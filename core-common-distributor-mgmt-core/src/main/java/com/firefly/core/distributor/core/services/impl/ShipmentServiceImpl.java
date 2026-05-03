/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.distributor.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.ShipmentMapper;
import com.firefly.core.distributor.core.services.ProductService;
import com.firefly.core.distributor.core.services.ShipmentService;
import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
import com.firefly.core.distributor.interfaces.dtos.ShipmentDTO;
import com.firefly.core.distributor.models.entities.Shipment;
import com.firefly.core.distributor.models.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the ShipmentService interface.
 */
@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public Mono<ShipmentDTO> createShipment(ShipmentDTO shipmentDTO) {
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment.setCreatedAt(LocalDateTime.now());
        
        // Generate tracking number if not provided
        if (shipment.getTrackingNumber() == null || shipment.getTrackingNumber().isEmpty()) {
            shipment.setTrackingNumber(generateTrackingNumber());
        }
        
        return shipmentRepository.save(shipment)
                .map(shipmentMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<ShipmentDTO> createShipmentForApprovedContract(LendingContractDTO lendingContractDTO) {
        // Create a new shipment for the approved contract
        ShipmentDTO shipmentDTO = ShipmentDTO.builder()
                .lendingContractId(lendingContractDTO.getId())
                .productId(lendingContractDTO.getProductId())
                .status("PENDING")
                .createdBy(lendingContractDTO.getApprovedBy())
                .build();

        return createShipment(shipmentDTO);
    }

    @Override
    @Transactional
    public Mono<ShipmentDTO> updateShipment(UUID id, ShipmentDTO shipmentDTO) {
        return shipmentRepository.findById(id)
                .flatMap(existingShipment -> {
                    Shipment updatedShipment = shipmentMapper.toEntity(shipmentDTO);
                    updatedShipment.setId(id);
                    updatedShipment.setCreatedAt(existingShipment.getCreatedAt());
                    updatedShipment.setCreatedBy(existingShipment.getCreatedBy());
                    updatedShipment.setUpdatedAt(LocalDateTime.now());
                    
                    return shipmentRepository.save(updatedShipment);
                })
                .map(shipmentMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<Void> deleteShipment(UUID id) {
        return shipmentRepository.deleteById(id);
    }

    @Override
    public Mono<ShipmentDTO> getShipmentById(UUID id) {
        return shipmentRepository.findById(id)
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    public Mono<ShipmentDTO> getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    public Flux<ShipmentDTO> getShipmentsByLendingContractId(UUID lendingContractId) {
        return shipmentRepository.findByLendingContractId(lendingContractId)
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    public Flux<ShipmentDTO> getShipmentsByProductId(UUID productId) {
        return shipmentRepository.findByProductId(productId)
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    public Flux<ShipmentDTO> getShipmentsByStatus(String status) {
        return shipmentRepository.findByStatus(status)
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    @Transactional
    public Mono<ShipmentDTO> updateShipmentStatus(UUID id, String status, UUID updatedBy) {
        return shipmentRepository.findById(id)
                .flatMap(shipment -> {
                    shipment.setStatus(status);
                    shipment.setUpdatedAt(LocalDateTime.now());
                    shipment.setUpdatedBy(updatedBy);
                    
                    // If status is SHIPPED, set shipping date
                    if ("SHIPPED".equals(status) && shipment.getShippingDate() == null) {
                        shipment.setShippingDate(LocalDateTime.now());
                    }
                    
                    // If status is DELIVERED, set actual delivery date
                    if ("DELIVERED".equals(status) && shipment.getActualDeliveryDate() == null) {
                        shipment.setActualDeliveryDate(LocalDateTime.now());
                    }
                    
                    return shipmentRepository.save(shipment);
                })
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO);
    }

    @Override
    public Mono<PaginationResponse<ShipmentDTO>> filterShipments(FilterRequest<ShipmentDTO> filterRequest) {
        // Simplified implementation - just return all shipments with basic pagination
        // In a real implementation, this would use proper filtering utilities
        
        return shipmentRepository.findAll()
                .map(shipmentMapper::toDto)
                .flatMap(this::enrichShipmentDTO)
                .collectList()
                .map(list -> {
                    // Create a simple pagination response with all items
                    // In a real implementation, proper pagination would be applied
                    return new PaginationResponse<>(list, list.size(), 0, list.size());
                });
    }
    
    /**
     * Enrich a ShipmentDTO with related data.
     *
     * @param shipmentDTO the ShipmentDTO to enrich
     * @return the enriched ShipmentDTO
     */
    private Mono<ShipmentDTO> enrichShipmentDTO(ShipmentDTO shipmentDTO) {
        // Enrich with product data if productId is available
        if (shipmentDTO.getProductId() != null) {
            return productService.getProductById(shipmentDTO.getProductId())
                    .map(productDTO -> {
                        shipmentDTO.setProductId(productDTO.getId());
                        return shipmentDTO;
                    })
                    .defaultIfEmpty(shipmentDTO);
        }
        
        return Mono.just(shipmentDTO);
    }
    
    /**
     * Generate a unique tracking number.
     *
     * @return a unique tracking number
     */
    private String generateTrackingNumber() {
        return "SHIP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}