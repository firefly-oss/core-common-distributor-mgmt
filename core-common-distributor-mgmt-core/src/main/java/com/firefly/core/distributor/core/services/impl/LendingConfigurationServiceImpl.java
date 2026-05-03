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
import com.firefly.core.distributor.core.mappers.LendingConfigurationMapper;
import com.firefly.core.distributor.core.services.LendingConfigurationService;
import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
import com.firefly.core.distributor.models.entities.LendingConfiguration;
import com.firefly.core.distributor.models.repositories.LendingConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Implementation of the LendingConfigurationService interface.
 */
@Service
@Transactional
public class LendingConfigurationServiceImpl implements LendingConfigurationService {

    @Autowired
    private LendingConfigurationRepository repository;

    @Autowired
    private LendingConfigurationMapper mapper;

    @Override
    public Mono<PaginationResponse<LendingConfigurationDTO>> filterLendingConfigurations(FilterRequest<LendingConfigurationDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        LendingConfiguration.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<LendingConfigurationDTO> createLendingConfiguration(LendingConfigurationDTO lendingConfigurationDTO) {
        return Mono.just(lendingConfigurationDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LendingConfigurationDTO> updateLendingConfiguration(UUID lendingConfigurationId, LendingConfigurationDTO lendingConfigurationDTO) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .flatMap(existingConfig -> {
                    mapper.updateEntityFromDto(lendingConfigurationDTO, existingConfig);
                    return repository.save(existingConfig);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteLendingConfiguration(UUID lendingConfigurationId) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .flatMap(config -> repository.deleteById(lendingConfigurationId));
    }

    @Override
    public Mono<LendingConfigurationDTO> getLendingConfigurationById(UUID lendingConfigurationId) {
        return repository.findById(lendingConfigurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Lending configuration not found with ID: " + lendingConfigurationId)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByProductId(UUID productId) {
        return repository.findByProductId(productId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getActiveLendingConfigurationsByProductId(UUID productId) {
        return repository.findByProductIdAndIsActive(productId, true)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByProductIdAndLendingType(UUID productId, LendingTypeDTO lendingType) {
        return repository.findByProductIdAndLendingTypeId(productId, lendingType.getId())
                .map(mapper::toDTO);
    }

    @Override
    public Mono<LendingConfigurationDTO> getDefaultLendingConfigurationByProductId(UUID productId) {
        return repository.findByProductIdAndIsDefault(productId, true)
                .switchIfEmpty(Mono.error(new RuntimeException("Default lending configuration not found for product ID: " + productId)))
                .map(mapper::toDTO);
    }

    @Override
    public Flux<LendingConfigurationDTO> getLendingConfigurationsByDistributorId(UUID distributorId) {
        return repository.findByProductDistributorId(distributorId)
                .map(mapper::toDTO);
    }
}