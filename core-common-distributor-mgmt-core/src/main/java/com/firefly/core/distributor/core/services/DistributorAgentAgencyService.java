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
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentAgencyDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor agent-agency relationships.
 */
public interface DistributorAgentAgencyService {
    /**
     * Filters the agent-agency relationships based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the agent-agency relationships
     * @param filterRequest the request object containing filtering criteria for DistributorAgentAgencyDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of agent-agency relationships
     */
    Mono<PaginationResponse<DistributorAgentAgencyDTO>> filterAgentAgencies(UUID distributorId, FilterRequest<DistributorAgentAgencyDTO> filterRequest);
    
    /**
     * Creates a new agent-agency relationship based on the provided information for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor that will own the agent-agency relationship
     * @param dto the DTO object containing details of the agent-agency relationship to be created
     * @return a Mono that emits the created DistributorAgentAgencyDTO object
     */
    Mono<DistributorAgentAgencyDTO> createAgentAgency(UUID distributorId, DistributorAgentAgencyDTO dto);
    
    /**
     * Updates an existing agent-agency relationship with updated information.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent-agency relationship
     * @param agentAgencyId the unique identifier of the agent-agency relationship to be updated
     * @param dto the data transfer object containing the updated details of the agent-agency relationship
     * @return a reactive Mono containing the updated DistributorAgentAgencyDTO
     */
    Mono<DistributorAgentAgencyDTO> updateAgentAgency(UUID distributorId, UUID agentAgencyId, DistributorAgentAgencyDTO dto);
    
    /**
     * Deletes an agent-agency relationship identified by its unique ID, validating distributor ownership.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent-agency relationship
     * @param agentAgencyId the unique identifier of the agent-agency relationship to be deleted
     * @return a Mono that completes when the agent-agency relationship is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteAgentAgency(UUID distributorId, UUID agentAgencyId);
    
    /**
     * Retrieves an agent-agency relationship by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent-agency relationship
     * @param agentAgencyId the unique identifier of the agent-agency relationship to retrieve
     * @return a Mono emitting the {@link DistributorAgentAgencyDTO} representing the agent-agency relationship if found,
     *         or an empty Mono if the agent-agency relationship does not exist
     */
    Mono<DistributorAgentAgencyDTO> getAgentAgencyById(UUID distributorId, UUID agentAgencyId);
}
