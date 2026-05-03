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
import com.firefly.core.distributor.core.mappers.DistributorAgentAgencyMapper;
import com.firefly.core.distributor.core.services.DistributorAgentAgencyService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentAgencyDTO;
import com.firefly.core.distributor.models.entities.DistributorAgentAgency;
import com.firefly.core.distributor.models.repositories.DistributorAgentAgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorAgentAgencyServiceImpl implements DistributorAgentAgencyService {

    private final DistributorAgentAgencyRepository repository;
    private final DistributorAgentAgencyMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorAgentAgencyDTO>> filterAgentAgencies(UUID distributorId, FilterRequest<DistributorAgentAgencyDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorAgentAgency.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorAgentAgencyDTO> createAgentAgency(UUID distributorId, DistributorAgentAgencyDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorAgentAgencyDTO> updateAgentAgency(UUID distributorId, UUID agentAgencyId, DistributorAgentAgencyDTO dto) {
        return repository.findById(agentAgencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("AgentAgency not found with ID: " + agentAgencyId)))
                .flatMap(existing -> {
                    DistributorAgentAgency updated = mapper.toEntity(dto);
                    updated.setId(agentAgencyId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAgentAgency(UUID distributorId, UUID agentAgencyId) {
        return repository.findById(agentAgencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("AgentAgency not found with ID: " + agentAgencyId)))
                .flatMap(agentAgency -> repository.deleteById(agentAgencyId));
    }

    @Override
    public Mono<DistributorAgentAgencyDTO> getAgentAgencyById(UUID distributorId, UUID agentAgencyId) {
        return repository.findById(agentAgencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("AgentAgency not found with ID: " + agentAgencyId)))
                .map(mapper::toDTO);
    }
}
