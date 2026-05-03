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
import com.firefly.core.distributor.core.mappers.DistributorContractMapper;
import com.firefly.core.distributor.core.services.DistributorContractService;
import com.firefly.core.distributor.interfaces.dtos.DistributorContractDTO;
import com.firefly.core.distributor.models.entities.DistributorContract;
import com.firefly.core.distributor.models.repositories.DistributorContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DistributorContractServiceImpl implements DistributorContractService {

    private final DistributorContractRepository repository;
    private final DistributorContractMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorContractDTO>> filterContracts(UUID distributorId, FilterRequest<DistributorContractDTO> filterRequest) {
        return FilterUtils.createFilter(DistributorContract.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<DistributorContractDTO> createContract(UUID distributorId, DistributorContractDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorContractDTO> updateContract(UUID distributorId, UUID contractId, DistributorContractDTO dto) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .flatMap(existing -> {
                    DistributorContract updated = mapper.toEntity(dto);
                    updated.setId(contractId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContract(UUID distributorId, UUID contractId) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .flatMap(entity -> repository.deleteById(contractId));
    }

    @Override
    public Mono<DistributorContractDTO> getContractById(UUID distributorId, UUID contractId) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .map(mapper::toDTO);
    }
}
