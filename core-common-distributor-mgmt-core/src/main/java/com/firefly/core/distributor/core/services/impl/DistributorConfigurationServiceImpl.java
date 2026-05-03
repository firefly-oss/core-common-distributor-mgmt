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
import com.firefly.core.distributor.core.mappers.DistributorConfigurationMapper;
import com.firefly.core.distributor.core.services.DistributorConfigurationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorConfigurationDTO;
import com.firefly.core.distributor.models.entities.DistributorConfiguration;
import com.firefly.core.distributor.models.repositories.DistributorConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorConfigurationServiceImpl implements DistributorConfigurationService {

    private final DistributorConfigurationRepository repository;
    private final DistributorConfigurationMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorConfigurationDTO>> filterConfigurations(UUID distributorId, FilterRequest<DistributorConfigurationDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorConfiguration.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorConfigurationDTO> createConfiguration(UUID distributorId, DistributorConfigurationDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorConfigurationDTO> updateConfiguration(UUID distributorId, UUID configurationId, DistributorConfigurationDTO dto) {
        return repository.findById(configurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Configuration not found with ID: " + configurationId)))
                .flatMap(existing -> {
                    DistributorConfiguration updated = mapper.toEntity(dto);
                    updated.setId(configurationId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteConfiguration(UUID distributorId, UUID configurationId) {
        return repository.findById(configurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Configuration not found with ID: " + configurationId)))
                .flatMap(entity -> repository.deleteById(configurationId));
    }

    @Override
    public Mono<DistributorConfigurationDTO> getConfigurationById(UUID distributorId, UUID configurationId) {
        return repository.findById(configurationId)
                .switchIfEmpty(Mono.error(new RuntimeException("Configuration not found with ID: " + configurationId)))
                .map(mapper::toDTO);
    }
}
