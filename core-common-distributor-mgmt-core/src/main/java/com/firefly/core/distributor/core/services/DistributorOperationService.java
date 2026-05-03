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
import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor operations.
 */
public interface DistributorOperationService {

    /**
     * Filters the distributor operations based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for DistributorOperationDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of distributor operations
     */
    Mono<PaginationResponse<DistributorOperationDTO>> filterDistributorOperations(FilterRequest<DistributorOperationDTO> filterRequest);

    /**
     * Creates a new distributor operation.
     *
     * @param distributorOperationDTO the distributor operation to create
     * @return a reactive {@code Mono} emitting the created distributor operation
     */
    Mono<DistributorOperationDTO> createDistributorOperation(DistributorOperationDTO distributorOperationDTO);

    /**
     * Updates an existing distributor operation.
     *
     * @param id the ID of the distributor operation to update
     * @param distributorOperationDTO the updated distributor operation data
     * @return a reactive {@code Mono} emitting the updated distributor operation
     */
    Mono<DistributorOperationDTO> updateDistributorOperation(UUID id, DistributorOperationDTO distributorOperationDTO);

    /**
     * Deletes a distributor operation by its ID.
     *
     * @param id the ID of the distributor operation to delete
     * @return a reactive {@code Mono} that completes when the operation is deleted
     */
    Mono<Void> deleteDistributorOperation(UUID id);

    /**
     * Retrieves a distributor operation by its ID.
     *
     * @param id the ID of the distributor operation to retrieve
     * @return a reactive {@code Mono} emitting the distributor operation if found
     */
    Mono<DistributorOperationDTO> getDistributorOperationById(UUID id);

    /**
     * Retrieves all operations for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all operations for the distributor
     */
    Flux<DistributorOperationDTO> getOperationsByDistributorId(UUID distributorId);

    /**
     * Retrieves all active operations for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all active operations for the distributor
     */
    Flux<DistributorOperationDTO> getActiveOperationsByDistributorId(UUID distributorId);

    /**
     * Retrieves operations by country ID.
     *
     * @param countryId the country ID
     * @return a reactive {@code Flux} emitting operations for the country
     */
    Flux<DistributorOperationDTO> getOperationsByCountryId(UUID countryId);

    /**
     * Retrieves operations by administrative division ID.
     *
     * @param administrativeDivisionId the administrative division ID
     * @return a reactive {@code Flux} emitting operations for the administrative division
     */
    Flux<DistributorOperationDTO> getOperationsByAdministrativeDivisionId(UUID administrativeDivisionId);

    /**
     * Checks if a distributor can operate in a specific country and administrative division.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param administrativeDivisionId the administrative division ID
     * @return a reactive {@code Mono} emitting true if the distributor can operate in the specified location
     */
    Mono<Boolean> canDistributorOperateInLocation(UUID distributorId, UUID countryId, UUID administrativeDivisionId);

    /**
     * Activates a distributor operation.
     *
     * @param id the ID of the distributor operation to activate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated distributor operation
     */
    Mono<DistributorOperationDTO> activateDistributorOperation(UUID id, UUID updatedBy);

    /**
     * Deactivates a distributor operation.
     *
     * @param id the ID of the distributor operation to deactivate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated distributor operation
     */
    Mono<DistributorOperationDTO> deactivateDistributorOperation(UUID id, UUID updatedBy);
}
