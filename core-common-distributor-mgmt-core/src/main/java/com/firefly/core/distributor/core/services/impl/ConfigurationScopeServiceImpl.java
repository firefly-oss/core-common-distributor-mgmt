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
import com.firefly.core.distributor.core.mappers.ConfigurationScopeMapper;
import com.firefly.core.distributor.core.services.ConfigurationScopeService;
import com.firefly.core.distributor.interfaces.dtos.ConfigurationScopeDTO;
import com.firefly.core.distributor.models.entities.ConfigurationScope;
import com.firefly.core.distributor.models.repositories.ConfigurationScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfigurationScopeServiceImpl implements ConfigurationScopeService {

    private final ConfigurationScopeRepository repository;
    private final ConfigurationScopeMapper mapper;

    @Override
    public Mono<PaginationResponse<ConfigurationScopeDTO>> filterConfigurationScopes(FilterRequest<ConfigurationScopeDTO> filterRequest) {
        return FilterUtils.createFilter(ConfigurationScope.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<ConfigurationScopeDTO> createConfigurationScope(ConfigurationScopeDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ConfigurationScopeDTO> updateConfigurationScope(UUID id, ConfigurationScopeDTO dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("ConfigurationScope not found with ID: " + id)))
                .flatMap(existing -> {
                    ConfigurationScope updated = mapper.toEntity(dto);
                    updated.setId(id);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteConfigurationScope(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("ConfigurationScope not found with ID: " + id)))
                .flatMap(entity -> repository.deleteById(id));
    }

    @Override
    public Mono<ConfigurationScopeDTO> getConfigurationScopeById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("ConfigurationScope not found with ID: " + id)))
                .map(mapper::toDTO);
    }
}
