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
import com.firefly.core.distributor.core.mappers.DistributorAuthorizedTerritoryMapper;
import com.firefly.core.distributor.core.services.DistributorAuthorizedTerritoryService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuthorizedTerritoryDTO;
import com.firefly.core.distributor.models.entities.DistributorAuthorizedTerritory;
import com.firefly.core.distributor.models.repositories.DistributorAuthorizedTerritoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorAuthorizedTerritoryServiceImpl implements DistributorAuthorizedTerritoryService {

    private final DistributorAuthorizedTerritoryRepository repository;
    private final DistributorAuthorizedTerritoryMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorAuthorizedTerritoryDTO>> filterTerritories(UUID distributorId, FilterRequest<DistributorAuthorizedTerritoryDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorAuthorizedTerritory.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorAuthorizedTerritoryDTO> createTerritory(UUID distributorId, DistributorAuthorizedTerritoryDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .doOnNext(entity -> entity.setDistributorId(distributorId))
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorAuthorizedTerritoryDTO> updateTerritory(UUID distributorId, UUID territoryId, DistributorAuthorizedTerritoryDTO dto) {
        return repository.findById(territoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Authorized territory not found with ID: " + territoryId)))
                .flatMap(existing -> {
                    if (!existing.getDistributorId().equals(distributorId)) {
                        return Mono.error(new RuntimeException("Territory does not belong to distributor: " + distributorId));
                    }
                    DistributorAuthorizedTerritory updated = mapper.toEntity(dto);
                    updated.setId(territoryId);
                    updated.setDistributorId(distributorId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTerritory(UUID distributorId, UUID territoryId) {
        return repository.findById(territoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Authorized territory not found with ID: " + territoryId)))
                .flatMap(territory -> {
                    if (!territory.getDistributorId().equals(distributorId)) {
                        return Mono.error(new RuntimeException("Territory does not belong to distributor: " + distributorId));
                    }
                    return repository.deleteById(territoryId);
                });
    }

    @Override
    public Mono<DistributorAuthorizedTerritoryDTO> getTerritoryById(UUID distributorId, UUID territoryId) {
        return repository.findById(territoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Authorized territory not found with ID: " + territoryId)))
                .flatMap(territory -> {
                    if (!territory.getDistributorId().equals(distributorId)) {
                        return Mono.error(new RuntimeException("Territory does not belong to distributor: " + distributorId));
                    }
                    return Mono.just(mapper.toDTO(territory));
                });
    }
}

