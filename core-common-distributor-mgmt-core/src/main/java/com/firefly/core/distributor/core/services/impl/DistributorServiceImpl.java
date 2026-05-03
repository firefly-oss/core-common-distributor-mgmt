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
import com.firefly.core.distributor.core.mappers.DistributorMapper;
import com.firefly.core.distributor.core.services.DistributorService;
import com.firefly.core.distributor.interfaces.dtos.DistributorDTO;
import com.firefly.core.distributor.models.entities.Distributor;
import com.firefly.core.distributor.models.repositories.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class DistributorServiceImpl implements DistributorService {

    @Autowired
    private DistributorRepository repository;

    @Autowired
    private DistributorMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorDTO>> filterDistributors(FilterRequest<DistributorDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Distributor.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorDTO> createDistributor(DistributorDTO distributorDTO) {
        return Mono.just(distributorDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorDTO> updateDistributor(UUID distributorId, DistributorDTO distributorDTO) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .flatMap(existingDistributor -> {
                    Distributor updatedDistributor = mapper.toEntity(distributorDTO);
                    updatedDistributor.setId(distributorId);
                    return repository.save(updatedDistributor);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributor(UUID distributorId) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .flatMap(distributor -> repository.deleteById(distributorId));
    }

    @Override
    public Mono<DistributorDTO> getDistributorById(UUID distributorId) {
        return repository.findById(distributorId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor not found with ID: " + distributorId)))
                .map(mapper::toDTO);
    }
}