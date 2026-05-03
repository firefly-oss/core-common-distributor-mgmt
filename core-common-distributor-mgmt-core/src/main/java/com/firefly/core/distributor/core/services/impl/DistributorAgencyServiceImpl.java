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
import com.firefly.core.distributor.core.mappers.DistributorAgencyMapper;
import com.firefly.core.distributor.core.services.DistributorAgencyService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgencyDTO;
import com.firefly.core.distributor.models.entities.DistributorAgency;
import com.firefly.core.distributor.models.repositories.DistributorAgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorAgencyServiceImpl implements DistributorAgencyService {

    private final DistributorAgencyRepository repository;
    private final DistributorAgencyMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorAgencyDTO>> filterAgencies(UUID distributorId, FilterRequest<DistributorAgencyDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorAgency.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorAgencyDTO> createAgency(UUID distributorId, DistributorAgencyDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorAgencyDTO> updateAgency(UUID distributorId, UUID agencyId, DistributorAgencyDTO dto) {
        return repository.findById(agencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agency not found with ID: " + agencyId)))
                .flatMap(existing -> {
                    DistributorAgency updated = mapper.toEntity(dto);
                    updated.setId(agencyId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteAgency(UUID distributorId, UUID agencyId) {
        return repository.findById(agencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agency not found with ID: " + agencyId)))
                .flatMap(agency -> repository.deleteById(agencyId));
    }

    @Override
    public Mono<DistributorAgencyDTO> getAgencyById(UUID distributorId, UUID agencyId) {
        return repository.findById(agencyId)
                .switchIfEmpty(Mono.error(new RuntimeException("Agency not found with ID: " + agencyId)))
                .map(mapper::toDTO);
    }
}
