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
import com.firefly.core.distributor.core.mappers.DistributorBrandingMapper;
import com.firefly.core.distributor.core.services.DistributorBrandingService;
import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import com.firefly.core.distributor.models.repositories.DistributorBrandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class DistributorBrandingServiceImpl implements DistributorBrandingService {

    @Autowired
    private DistributorBrandingRepository repository;

    @Autowired
    private DistributorBrandingMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorBrandingDTO>> filterDistributorBranding(FilterRequest<DistributorBrandingDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorBranding.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorBrandingDTO> createDistributorBranding(DistributorBrandingDTO distributorBrandingDTO) {
        return Mono.just(distributorBrandingDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorBrandingDTO> updateDistributorBranding(UUID distributorBrandingId, DistributorBrandingDTO distributorBrandingDTO) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .flatMap(existingDistributorBranding -> {
                    mapper.updateEntityFromDto(distributorBrandingDTO, existingDistributorBranding);
                    return repository.save(existingDistributorBranding);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorBranding(UUID distributorBrandingId) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .flatMap(distributorBranding -> repository.deleteById(distributorBrandingId));
    }

    @Override
    public Mono<DistributorBrandingDTO> getDistributorBrandingById(UUID distributorBrandingId) {
        return repository.findById(distributorBrandingId)
                .switchIfEmpty(Mono.error(new RuntimeException("Distributor branding not found with ID: " + distributorBrandingId)))
                .map(mapper::toDTO);
    }
}