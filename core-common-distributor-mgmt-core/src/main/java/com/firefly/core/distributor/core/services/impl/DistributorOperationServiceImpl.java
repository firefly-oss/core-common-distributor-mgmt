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
import com.firefly.core.distributor.core.mappers.DistributorOperationMapper;
import com.firefly.core.distributor.core.services.DistributorOperationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import com.firefly.core.distributor.models.entities.DistributorOperation;
import com.firefly.core.distributor.models.repositories.DistributorOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the DistributorOperationService interface.
 */
@Service
@Transactional
public class DistributorOperationServiceImpl implements DistributorOperationService {

    @Autowired
    private DistributorOperationRepository repository;

    @Autowired
    private DistributorOperationMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorOperationDTO>> filterDistributorOperations(FilterRequest<DistributorOperationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorOperation.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorOperationDTO> createDistributorOperation(DistributorOperationDTO distributorOperationDTO) {
        return Mono.just(distributorOperationDTO)
                .map(mapper::toEntity)
                .doOnNext(operation -> {
                    operation.setCreatedAt(LocalDateTime.now());
                    if (operation.getIsActive() == null) {
                        operation.setIsActive(true);
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorOperationDTO> updateDistributorOperation(UUID id, DistributorOperationDTO distributorOperationDTO) {
        return repository.findById(id)
                .flatMap(existingOperation -> {
                    DistributorOperation updatedOperation = mapper.toEntity(distributorOperationDTO);
                    updatedOperation.setId(id);
                    updatedOperation.setCreatedAt(existingOperation.getCreatedAt());
                    updatedOperation.setCreatedBy(existingOperation.getCreatedBy());
                    updatedOperation.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedOperation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorOperation(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<DistributorOperationDTO> getDistributorOperationById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorOperationDTO> getOperationsByDistributorId(UUID distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorOperationDTO> getActiveOperationsByDistributorId(UUID distributorId) {
        return repository.findByDistributorIdAndIsActiveTrue(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorOperationDTO> getOperationsByCountryId(UUID countryId) {
        return repository.findByCountryId(countryId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorOperationDTO> getOperationsByAdministrativeDivisionId(UUID administrativeDivisionId) {
        return repository.findByAdministrativeDivisionId(administrativeDivisionId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Boolean> canDistributorOperateInLocation(UUID distributorId, UUID countryId, UUID administrativeDivisionId) {
        return repository.existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
                distributorId, countryId, administrativeDivisionId);
    }

    @Override
    public Mono<DistributorOperationDTO> activateDistributorOperation(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(operation -> {
                    operation.setIsActive(true);
                    operation.setUpdatedAt(LocalDateTime.now());
                    operation.setUpdatedBy(updatedBy);
                    return repository.save(operation);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorOperationDTO> deactivateDistributorOperation(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(operation -> {
                    operation.setIsActive(false);
                    operation.setUpdatedAt(LocalDateTime.now());
                    operation.setUpdatedBy(updatedBy);
                    return repository.save(operation);
                })
                .map(mapper::toDTO);
    }
}
