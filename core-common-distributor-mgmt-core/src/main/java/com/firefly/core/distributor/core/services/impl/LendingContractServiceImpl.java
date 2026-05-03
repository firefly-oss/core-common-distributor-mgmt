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
import com.firefly.core.distributor.core.mappers.LendingContractMapper;
import com.firefly.core.distributor.core.services.LendingContractService;
import com.firefly.core.distributor.core.services.ProductService;
import com.firefly.core.distributor.core.services.ShipmentService;
import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
import com.firefly.core.distributor.models.entities.LendingContract;
import com.firefly.core.distributor.models.repositories.LendingContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the LendingContractService interface.
 */
@Service
public class LendingContractServiceImpl implements LendingContractService {

    @Autowired
    private LendingContractRepository lendingContractRepository;

    @Autowired
    private LendingContractMapper lendingContractMapper;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public Mono<LendingContractDTO> createLendingContract(LendingContractDTO lendingContractDTO) {
        LendingContract lendingContract = lendingContractMapper.toEntity(lendingContractDTO);
        lendingContract.setCreatedAt(LocalDateTime.now());
        lendingContract.setIsActive(true);

        return lendingContractRepository.save(lendingContract)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    @Transactional
    public Mono<LendingContractDTO> updateLendingContract(UUID id, LendingContractDTO lendingContractDTO) {
        return lendingContractRepository.findById(id)
                .flatMap(existingContract -> {
                    lendingContractMapper.updateEntityFromDto(lendingContractDTO, existingContract);
                    return lendingContractRepository.save(existingContract);
                })
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    @Transactional
    public Mono<Void> deleteLendingContract(UUID id) {
        return lendingContractRepository.deleteById(id);
    }

    @Override
    public Mono<LendingContractDTO> getLendingContractById(UUID id) {
        return lendingContractRepository.findById(id)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    public Mono<LendingContractDTO> getLendingContractByContractId(UUID contractId) {
        return lendingContractRepository.findByContractId(contractId)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    public Flux<LendingContractDTO> getLendingContractsByDistributorId(UUID distributorId) {
        return lendingContractRepository.findByDistributorId(distributorId)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    public Flux<LendingContractDTO> getLendingContractsByProductId(UUID productId) {
        return lendingContractRepository.findByProductId(productId)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    public Flux<LendingContractDTO> getLendingContractsByPartyId(UUID partyId) {
        return lendingContractRepository.findByPartyId(partyId)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    public Flux<LendingContractDTO> getLendingContractsByStatus(String status) {
        return lendingContractRepository.findByStatus(status)
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO);
    }

    @Override
    @Transactional
    public Mono<LendingContractDTO> approveLendingContract(UUID id, UUID approvedBy) {
        return lendingContractRepository.findById(id)
                .flatMap(contract -> {
                    contract.setStatus("APPROVED");
                    contract.setApprovalDate(LocalDateTime.now());
                    contract.setApprovedBy(approvedBy);
                    contract.setUpdatedAt(LocalDateTime.now());
                    contract.setUpdatedBy(approvedBy);

                    return lendingContractRepository.save(contract);
                })
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO)
                .flatMap(approvedContract -> {
                    // Create a shipment for the approved contract
                    return shipmentService.createShipmentForApprovedContract(approvedContract)
                            .thenReturn(approvedContract);
                });
    }

    @Override
    public Mono<PaginationResponse<LendingContractDTO>> filterLendingContracts(FilterRequest<LendingContractDTO> filterRequest) {
        // Simplified implementation - just return all contracts with basic pagination
        // In a real implementation, this would use proper filtering utilities

        return lendingContractRepository.findAll()
                .map(lendingContractMapper::toDto)
                .flatMap(this::enrichLendingContractDTO)
                .collectList()
                .map(list -> {
                    // Create a simple pagination response with all items
                    // In a real implementation, proper pagination would be applied
                    return new PaginationResponse<>(list, list.size(), 0, list.size());
                });
    }

    /**
     * Enrich a LendingContractDTO with related data.
     *
     * @param lendingContractDTO the LendingContractDTO to enrich
     * @return the enriched LendingContractDTO
     */
    private Mono<LendingContractDTO> enrichLendingContractDTO(LendingContractDTO lendingContractDTO) {
        // Enrich with product data if productId is available
        if (lendingContractDTO.getProductId() != null) {
            return productService.getProductById(lendingContractDTO.getProductId())
                    .map(productDTO -> {
                        lendingContractDTO.setProductId(productDTO.getId());
                        return lendingContractDTO;
                    })
                    .defaultIfEmpty(lendingContractDTO);
        }

        return Mono.just(lendingContractDTO);
    }
}