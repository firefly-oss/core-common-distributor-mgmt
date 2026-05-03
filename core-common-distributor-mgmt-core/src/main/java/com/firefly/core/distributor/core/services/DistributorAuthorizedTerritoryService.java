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
import com.firefly.core.distributor.interfaces.dtos.DistributorAuthorizedTerritoryDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor authorized territories.
 */
public interface DistributorAuthorizedTerritoryService {
    /**
     * Filters the authorized territories based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor
     * @param filterRequest the request object containing filtering criteria for DistributorAuthorizedTerritoryDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of territories
     */
    Mono<PaginationResponse<DistributorAuthorizedTerritoryDTO>> filterTerritories(UUID distributorId, FilterRequest<DistributorAuthorizedTerritoryDTO> filterRequest);
    
    /**
     * Creates a new authorized territory for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor
     * @param dto the DTO object containing details of the territory to be created
     * @return a Mono that emits the created DistributorAuthorizedTerritoryDTO object
     */
    Mono<DistributorAuthorizedTerritoryDTO> createTerritory(UUID distributorId, DistributorAuthorizedTerritoryDTO dto);
    
    /**
     * Updates an existing authorized territory with updated information.
     *
     * @param distributorId the unique identifier of the distributor
     * @param territoryId the unique identifier of the territory to be updated
     * @param dto the data transfer object containing the updated details of the territory
     * @return a reactive Mono containing the updated DistributorAuthorizedTerritoryDTO
     */
    Mono<DistributorAuthorizedTerritoryDTO> updateTerritory(UUID distributorId, UUID territoryId, DistributorAuthorizedTerritoryDTO dto);
    
    /**
     * Deletes an authorized territory identified by its unique ID.
     *
     * @param distributorId the unique identifier of the distributor
     * @param territoryId the unique identifier of the territory to be deleted
     * @return a Mono that completes when the territory is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteTerritory(UUID distributorId, UUID territoryId);
    
    /**
     * Retrieves an authorized territory by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor
     * @param territoryId the unique identifier of the territory to retrieve
     * @return a Mono emitting the {@link DistributorAuthorizedTerritoryDTO} representing the territory if found,
     *         or an empty Mono if the territory does not exist
     */
    Mono<DistributorAuthorizedTerritoryDTO> getTerritoryById(UUID distributorId, UUID territoryId);
}

