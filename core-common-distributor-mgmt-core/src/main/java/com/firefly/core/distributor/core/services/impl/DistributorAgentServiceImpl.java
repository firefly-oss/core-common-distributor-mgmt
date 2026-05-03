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
import com.firefly.core.distributor.core.mappers.DistributorAgentMapper;
import com.firefly.core.distributor.core.services.DistributorAgentService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentDTO;
import com.firefly.core.distributor.models.entities.DistributorAgent;
import com.firefly.core.distributor.models.repositories.DistributorAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorAgentServiceImpl implements DistributorAgentService {

    private final DistributorAgentRepository repository;
    private final DistributorAgentMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorAgentDTO>> filterAgents(UUID distributorId, FilterRequest<DistributorAgentDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorAgent.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorAgentDTO> createAgent(UUID distributorId, DistributorAgentDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorAgentDTO> updateAgent(UUID distributorId, UUID agentId, DistributorAgentDTO dto) {
        return repository.findById(agentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agent not found with ID: " + agentId)))
                .flatMap(existing -> {
                    DistributorAgent updated = mapper.toEntity(dto);
                    updated.setId(agentId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAgent(UUID distributorId, UUID agentId) {
        return repository.findById(agentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agent not found with ID: " + agentId)))
                .flatMap(agent -> repository.deleteById(agentId));
    }

    @Override
    public Mono<DistributorAgentDTO> getAgentById(UUID distributorId, UUID agentId) {
        return repository.findById(agentId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agent not found with ID: " + agentId)))
                .map(mapper::toDTO);
    }
}
