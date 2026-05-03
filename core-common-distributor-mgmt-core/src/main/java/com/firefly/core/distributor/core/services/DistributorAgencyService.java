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
import com.firefly.core.distributor.interfaces.dtos.DistributorAgencyDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor agencies.
 */
public interface DistributorAgencyService {
    /**
     * Filters the agencies based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the agencies
     * @param filterRequest the request object containing filtering criteria for DistributorAgencyDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of agencies
     */
    Mono<PaginationResponse<DistributorAgencyDTO>> filterAgencies(UUID distributorId, FilterRequest<DistributorAgencyDTO> filterRequest);
    
    /**
     * Creates a new agency based on the provided information for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor that will own the agency
     * @param dto the DTO object containing details of the agency to be created
     * @return a Mono that emits the created DistributorAgencyDTO object
     */
    Mono<DistributorAgencyDTO> createAgency(UUID distributorId, DistributorAgencyDTO dto);
    
    /**
     * Updates an existing agency with updated information.
     *
     * @param distributorId the unique identifier of the distributor that owns the agency
     * @param agencyId the unique identifier of the agency to be updated
     * @param dto the data transfer object containing the updated details of the agency
     * @return a reactive Mono containing the updated DistributorAgencyDTO
     */
    Mono<DistributorAgencyDTO> updateAgency(UUID distributorId, UUID agencyId, DistributorAgencyDTO dto);
    
    /**
     * Deletes an agency identified by its unique ID, validating distributor ownership.
     *
     * @param distributorId the unique identifier of the distributor that owns the agency
     * @param agencyId the unique identifier of the agency to be deleted
     * @return a Mono that completes when the agency is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteAgency(UUID distributorId, UUID agencyId);
    
    /**
     * Retrieves an agency by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor that owns the agency
     * @param agencyId the unique identifier of the agency to retrieve
     * @return a Mono emitting the {@link DistributorAgencyDTO} representing the agency if found,
     *         or an empty Mono if the agency does not exist
     */
    Mono<DistributorAgencyDTO> getAgencyById(UUID distributorId, UUID agencyId);
}
