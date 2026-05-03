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
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.mappers.DistributorSimulationMapper;
import com.firefly.core.distributor.core.services.DistributorSimulationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import com.firefly.core.distributor.models.entities.DistributorSimulation;
import com.firefly.core.distributor.models.repositories.DistributorSimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the DistributorSimulationService interface.
 */
@Service
@Transactional
public class DistributorSimulationServiceImpl implements DistributorSimulationService {

    @Autowired
    private DistributorSimulationRepository repository;

    @Autowired
    private DistributorSimulationMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorSimulationDTO>> filterDistributorSimulations(FilterRequest<DistributorSimulationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorSimulation.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorSimulationDTO> createDistributorSimulation(DistributorSimulationDTO distributorSimulationDTO) {
        return Mono.just(distributorSimulationDTO)
                .map(mapper::toEntity)
                .doOnNext(simulation -> {
                    simulation.setCreatedAt(LocalDateTime.now());
                    if (simulation.getIsActive() == null) {
                        simulation.setIsActive(true);
                    }
                    if (simulation.getSimulationStatus() == null) {
                        simulation.setSimulationStatus("PENDING");
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> updateDistributorSimulation(UUID id, DistributorSimulationDTO distributorSimulationDTO) {
        return repository.findById(id)
                .flatMap(existingSimulation -> {
                    DistributorSimulation updatedSimulation = mapper.toEntity(distributorSimulationDTO);
                    updatedSimulation.setId(id);
                    updatedSimulation.setCreatedAt(existingSimulation.getCreatedAt());
                    updatedSimulation.setCreatedBy(existingSimulation.getCreatedBy());
                    updatedSimulation.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedSimulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorSimulation(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<DistributorSimulationDTO> getDistributorSimulationById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByDistributorId(UUID distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getActiveSimulationsByDistributorId(UUID distributorId) {
        return repository.findByDistributorIdAndIsActiveTrue(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> getSimulationByApplicationId(UUID applicationId) {
        return repository.findByApplicationId(applicationId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByStatus(String simulationStatus) {
        return repository.findBySimulationStatus(simulationStatus)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorSimulationDTO> getSimulationsByDistributorIdAndStatus(UUID distributorId, String simulationStatus) {
        return repository.findByDistributorIdAndSimulationStatus(distributorId, simulationStatus)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> updateSimulationStatus(UUID id, String simulationStatus, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setSimulationStatus(simulationStatus);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> activateDistributorSimulation(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setIsActive(true);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorSimulationDTO> deactivateDistributorSimulation(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(simulation -> {
                    simulation.setIsActive(false);
                    simulation.setUpdatedAt(LocalDateTime.now());
                    simulation.setUpdatedBy(updatedBy);
                    return repository.save(simulation);
                })
                .map(mapper::toDTO);
    }
}
