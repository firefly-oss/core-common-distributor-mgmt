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
import com.firefly.core.distributor.interfaces.dtos.AgentRoleDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing agent roles.
 */
public interface AgentRoleService {
    /**
     * Filters the agent roles based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for AgentRoleDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of agent roles
     */
    Mono<PaginationResponse<AgentRoleDTO>> filterAgentRoles(FilterRequest<AgentRoleDTO> filterRequest);
    
    /**
     * Creates a new agent role based on the provided information.
     *
     * @param dto the DTO object containing details of the agent role to be created
     * @return a Mono that emits the created AgentRoleDTO object
     */
    Mono<AgentRoleDTO> createAgentRole(AgentRoleDTO dto);
    
    /**
     * Updates an existing agent role with updated information.
     *
     * @param id the unique identifier of the agent role to be updated
     * @param dto the data transfer object containing the updated details of the agent role
     * @return a reactive Mono containing the updated AgentRoleDTO
     */
    Mono<AgentRoleDTO> updateAgentRole(UUID id, AgentRoleDTO dto);
    
    /**
     * Deletes an agent role identified by its unique ID.
     *
     * @param id the unique identifier of the agent role to be deleted
     * @return a Mono that completes when the agent role is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteAgentRole(UUID id);
    
    /**
     * Retrieves an agent role by its unique identifier.
     *
     * @param id the unique identifier of the agent role to retrieve
     * @return a Mono emitting the {@link AgentRoleDTO} representing the agent role if found,
     *         or an empty Mono if the agent role does not exist
     */
    Mono<AgentRoleDTO> getAgentRoleById(UUID id);
}
