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
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor agents.
 */
public interface DistributorAgentService {
    /**
     * Filters the agents based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the agents
     * @param filterRequest the request object containing filtering criteria for DistributorAgentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of agents
     */
    Mono<PaginationResponse<DistributorAgentDTO>> filterAgents(UUID distributorId, FilterRequest<DistributorAgentDTO> filterRequest);
    
    /**
     * Creates a new agent based on the provided information for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor that will own the agent
     * @param dto the DTO object containing details of the agent to be created
     * @return a Mono that emits the created DistributorAgentDTO object
     */
    Mono<DistributorAgentDTO> createAgent(UUID distributorId, DistributorAgentDTO dto);
    
    /**
     * Updates an existing agent with updated information.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent
     * @param agentId the unique identifier of the agent to be updated
     * @param dto the data transfer object containing the updated details of the agent
     * @return a reactive Mono containing the updated DistributorAgentDTO
     */
    Mono<DistributorAgentDTO> updateAgent(UUID distributorId, UUID agentId, DistributorAgentDTO dto);
    
    /**
     * Deletes an agent identified by its unique ID, validating distributor ownership.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent
     * @param agentId the unique identifier of the agent to be deleted
     * @return a Mono that completes when the agent is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteAgent(UUID distributorId, UUID agentId);
    
    /**
     * Retrieves an agent by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor that owns the agent
     * @param agentId the unique identifier of the agent to retrieve
     * @return a Mono emitting the {@link DistributorAgentDTO} representing the agent if found,
     *         or an empty Mono if the agent does not exist
     */
    Mono<DistributorAgentDTO> getAgentById(UUID distributorId, UUID agentId);
}
