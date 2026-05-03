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
import com.firefly.core.distributor.interfaces.dtos.DistributorDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributors.
 */
public interface DistributorService {
    /**
     * Filters the distributors based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for DistributorDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of distributors
     */
    Mono<PaginationResponse<DistributorDTO>> filterDistributors(FilterRequest<DistributorDTO> filterRequest);
    
    /**
     * Creates a new distributor based on the provided information.
     *
     * @param distributorDTO the DTO object containing details of the distributor to be created
     * @return a Mono that emits the created DistributorDTO object
     */
    Mono<DistributorDTO> createDistributor(DistributorDTO distributorDTO);
    
    /**
     * Updates an existing distributor with updated information.
     *
     * @param distributorId the unique identifier of the distributor to be updated
     * @param distributorDTO the data transfer object containing the updated details of the distributor
     * @return a reactive Mono containing the updated DistributorDTO
     */
    Mono<DistributorDTO> updateDistributor(UUID distributorId, DistributorDTO distributorDTO);
    
    /**
     * Deletes a distributor identified by its unique ID.
     *
     * @param distributorId the unique identifier of the distributor to be deleted
     * @return a Mono that completes when the distributor is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteDistributor(UUID distributorId);
    
    /**
     * Retrieves a distributor by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor to retrieve
     * @return a Mono emitting the {@link DistributorDTO} representing the distributor if found,
     *         or an empty Mono if the distributor does not exist
     */
    Mono<DistributorDTO> getDistributorById(UUID distributorId);
}