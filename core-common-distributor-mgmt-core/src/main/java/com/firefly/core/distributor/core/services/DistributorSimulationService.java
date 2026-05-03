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
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor simulations.
 */
public interface DistributorSimulationService {

    /**
     * Filters the distributor simulations based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for DistributorSimulationDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of distributor simulations
     */
    Mono<PaginationResponse<DistributorSimulationDTO>> filterDistributorSimulations(FilterRequest<DistributorSimulationDTO> filterRequest);

    /**
     * Creates a new distributor simulation.
     *
     * @param distributorSimulationDTO the distributor simulation to create
     * @return a reactive {@code Mono} emitting the created distributor simulation
     */
    Mono<DistributorSimulationDTO> createDistributorSimulation(DistributorSimulationDTO distributorSimulationDTO);

    /**
     * Updates an existing distributor simulation.
     *
     * @param id the ID of the distributor simulation to update
     * @param distributorSimulationDTO the updated distributor simulation data
     * @return a reactive {@code Mono} emitting the updated distributor simulation
     */
    Mono<DistributorSimulationDTO> updateDistributorSimulation(UUID id, DistributorSimulationDTO distributorSimulationDTO);

    /**
     * Deletes a distributor simulation by its ID.
     *
     * @param id the ID of the distributor simulation to delete
     * @return a reactive {@code Mono} that completes when the simulation is deleted
     */
    Mono<Void> deleteDistributorSimulation(UUID id);

    /**
     * Retrieves a distributor simulation by its ID.
     *
     * @param id the ID of the distributor simulation to retrieve
     * @return a reactive {@code Mono} emitting the distributor simulation if found
     */
    Mono<DistributorSimulationDTO> getDistributorSimulationById(UUID id);

    /**
     * Retrieves all simulations for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all simulations for the distributor
     */
    Flux<DistributorSimulationDTO> getSimulationsByDistributorId(UUID distributorId);

    /**
     * Retrieves all active simulations for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all active simulations for the distributor
     */
    Flux<DistributorSimulationDTO> getActiveSimulationsByDistributorId(UUID distributorId);

    /**
     * Retrieves a simulation by application ID.
     *
     * @param applicationId the application ID
     * @return a reactive {@code Mono} emitting the simulation if found
     */
    Mono<DistributorSimulationDTO> getSimulationByApplicationId(UUID applicationId);

    /**
     * Retrieves simulations by status.
     *
     * @param simulationStatus the simulation status
     * @return a reactive {@code Flux} emitting simulations with the specified status
     */
    Flux<DistributorSimulationDTO> getSimulationsByStatus(String simulationStatus);

    /**
     * Retrieves simulations by distributor and status.
     *
     * @param distributorId the distributor ID
     * @param simulationStatus the simulation status
     * @return a reactive {@code Flux} emitting simulations for the distributor with the specified status
     */
    Flux<DistributorSimulationDTO> getSimulationsByDistributorIdAndStatus(UUID distributorId, String simulationStatus);

    /**
     * Updates the status of a distributor simulation.
     *
     * @param id the ID of the distributor simulation
     * @param simulationStatus the new simulation status
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated distributor simulation
     */
    Mono<DistributorSimulationDTO> updateSimulationStatus(UUID id, String simulationStatus, UUID updatedBy);

    /**
     * Activates a distributor simulation.
     *
     * @param id the ID of the distributor simulation to activate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated distributor simulation
     */
    Mono<DistributorSimulationDTO> activateDistributorSimulation(UUID id, UUID updatedBy);

    /**
     * Deactivates a distributor simulation.
     *
     * @param id the ID of the distributor simulation to deactivate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated distributor simulation
     */
    Mono<DistributorSimulationDTO> deactivateDistributorSimulation(UUID id, UUID updatedBy);
}
