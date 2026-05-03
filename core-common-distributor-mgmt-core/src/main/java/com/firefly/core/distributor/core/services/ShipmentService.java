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


package com.firefly.core.distributor.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
import com.firefly.core.distributor.interfaces.dtos.ShipmentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service for managing shipments.
 */
public interface ShipmentService {

    /**
     * Create a new shipment.
     *
     * @param shipmentDTO the shipment to create
     * @return the created shipment
     */
    Mono<ShipmentDTO> createShipment(ShipmentDTO shipmentDTO);

    /**
     * Create a shipment for an approved lending contract.
     *
     * @param lendingContractDTO the approved lending contract
     * @return a Mono completing when the operation is done
     */
    Mono<ShipmentDTO> createShipmentForApprovedContract(LendingContractDTO lendingContractDTO);

    /**
     * Update an existing shipment.
     *
     * @param id the ID of the shipment to update
     * @param shipmentDTO the shipment data to update
     * @return the updated shipment
     */
    Mono<ShipmentDTO> updateShipment(UUID id, ShipmentDTO shipmentDTO);

    /**
     * Delete a shipment.
     *
     * @param id the ID of the shipment to delete
     * @return a Mono completing when the operation is done
     */
    Mono<Void> deleteShipment(UUID id);

    /**
     * Get a shipment by ID.
     *
     * @param id the ID of the shipment to retrieve
     * @return the shipment
     */
    Mono<ShipmentDTO> getShipmentById(UUID id);

    /**
     * Get a shipment by tracking number.
     *
     * @param trackingNumber the tracking number of the shipment to retrieve
     * @return the shipment
     */
    Mono<ShipmentDTO> getShipmentByTrackingNumber(String trackingNumber);

    /**
     * Get all shipments for a lending contract.
     *
     * @param lendingContractId the ID of the lending contract
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByLendingContractId(UUID lendingContractId);

    /**
     * Get all shipments for a product.
     *
     * @param productId the ID of the product
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByProductId(UUID productId);

    /**
     * Get all shipments with a specific status.
     *
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<ShipmentDTO> getShipmentsByStatus(String status);

    /**
     * Update the status of a shipment.
     *
     * @param id the ID of the shipment to update
     * @param status the new status
     * @param updatedBy the ID of the user updating the status
     * @return the updated shipment
     */
    Mono<ShipmentDTO> updateShipmentStatus(UUID id, String status, UUID updatedBy);

    /**
     * Filter shipments based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a PaginationResponse with the filtered shipments
     */
    Mono<PaginationResponse<ShipmentDTO>> filterShipments(FilterRequest<ShipmentDTO> filterRequest);
}