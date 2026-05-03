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
import com.firefly.core.distributor.core.mappers.DistributorProductCatalogMapper;
import com.firefly.core.distributor.core.services.DistributorProductCatalogService;
import com.firefly.core.distributor.interfaces.dtos.DistributorProductCatalogDTO;
import com.firefly.core.distributor.models.entities.DistributorProductCatalog;
import com.firefly.core.distributor.models.repositories.DistributorProductCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorProductCatalogServiceImpl implements DistributorProductCatalogService {

    private final DistributorProductCatalogRepository repository;
    private final DistributorProductCatalogMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorProductCatalogDTO>> filterProductCatalogs(UUID distributorId, FilterRequest<DistributorProductCatalogDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorProductCatalog.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorProductCatalogDTO> createProductCatalog(UUID distributorId, DistributorProductCatalogDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorProductCatalogDTO> updateProductCatalog(UUID distributorId, UUID catalogId, DistributorProductCatalogDTO dto) {
        return repository.findById(catalogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product catalog not found with ID: " + catalogId)))
                .flatMap(existing -> {
                    DistributorProductCatalog updated = mapper.toEntity(dto);
                    updated.setId(catalogId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteProductCatalog(UUID distributorId, UUID catalogId) {
        return repository.findById(catalogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product catalog not found with ID: " + catalogId)))
                .flatMap(entity -> repository.deleteById(catalogId));
    }

    @Override
    public Mono<DistributorProductCatalogDTO> getProductCatalogById(UUID distributorId, UUID catalogId) {
        return repository.findById(catalogId)
                .switchIfEmpty(Mono.error(new RuntimeException("Product catalog not found with ID: " + catalogId)))
                .map(mapper::toDTO);
    }
}
