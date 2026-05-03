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
import com.firefly.core.distributor.core.mappers.AgencyPaymentMethodMapper;
import com.firefly.core.distributor.core.services.AgencyPaymentMethodService;
import com.firefly.core.distributor.interfaces.dtos.AgencyPaymentMethodDTO;
import com.firefly.core.distributor.models.entities.AgencyPaymentMethod;
import com.firefly.core.distributor.models.repositories.AgencyPaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyPaymentMethodServiceImpl implements AgencyPaymentMethodService {

    private final AgencyPaymentMethodRepository repository;
    private final AgencyPaymentMethodMapper mapper;

    @Override
    public Mono<PaginationResponse<AgencyPaymentMethodDTO>> filterPaymentMethods(UUID agencyId, FilterRequest<AgencyPaymentMethodDTO> filterRequest) {
        return FilterUtils.createFilter(AgencyPaymentMethod.class, mapper::toDTO).filter(filterRequest);
    }

    @Override
    public Mono<AgencyPaymentMethodDTO> createPaymentMethod(UUID agencyId, AgencyPaymentMethodDTO dto) {
        return Mono.just(dto)
                .map(mapper::toEntity)
                .doOnNext(entity -> {
                    entity.setAgencyId(agencyId);
                    // If this is marked as primary, we need to unset other primary methods
                    if (Boolean.TRUE.equals(entity.getIsPrimary())) {
                        entity.setIsPrimary(false); // Will be set after unsetting others
                    }
                })
                .flatMap(entity -> {
                    if (Boolean.TRUE.equals(dto.getIsPrimary())) {
                        // Unset all other primary payment methods for this agency
                        return repository.findByAgencyIdAndIsPrimary(agencyId, true)
                                .flatMap(existing -> {
                                    existing.setIsPrimary(false);
                                    return repository.save(existing);
                                })
                                .then(Mono.just(entity))
                                .doOnNext(e -> e.setIsPrimary(true));
                    }
                    return Mono.just(entity);
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<AgencyPaymentMethodDTO> updatePaymentMethod(UUID agencyId, UUID paymentMethodId, AgencyPaymentMethodDTO dto) {
        return repository.findById(paymentMethodId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment method not found with ID: " + paymentMethodId)))
                .flatMap(existing -> {
                    if (!existing.getAgencyId().equals(agencyId)) {
                        return Mono.error(new RuntimeException("Payment method does not belong to agency: " + agencyId));
                    }
                    AgencyPaymentMethod updated = mapper.toEntity(dto);
                    updated.setId(paymentMethodId);
                    updated.setAgencyId(agencyId);
                    
                    // If setting as primary, unset other primary methods
                    if (Boolean.TRUE.equals(dto.getIsPrimary()) && !Boolean.TRUE.equals(existing.getIsPrimary())) {
                        return repository.findByAgencyIdAndIsPrimary(agencyId, true)
                                .flatMap(otherPrimary -> {
                                    if (!otherPrimary.getId().equals(paymentMethodId)) {
                                        otherPrimary.setIsPrimary(false);
                                        return repository.save(otherPrimary);
                                    }
                                    return Mono.just(otherPrimary);
                                })
                                .then(repository.save(updated));
                    }
                    
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentMethod(UUID agencyId, UUID paymentMethodId) {
        return repository.findById(paymentMethodId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment method not found with ID: " + paymentMethodId)))
                .flatMap(paymentMethod -> {
                    if (!paymentMethod.getAgencyId().equals(agencyId)) {
                        return Mono.error(new RuntimeException("Payment method does not belong to agency: " + agencyId));
                    }
                    return repository.deleteById(paymentMethodId);
                });
    }

    @Override
    public Mono<AgencyPaymentMethodDTO> getPaymentMethodById(UUID agencyId, UUID paymentMethodId) {
        return repository.findById(paymentMethodId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment method not found with ID: " + paymentMethodId)))
                .flatMap(paymentMethod -> {
                    if (!paymentMethod.getAgencyId().equals(agencyId)) {
                        return Mono.error(new RuntimeException("Payment method does not belong to agency: " + agencyId));
                    }
                    return Mono.just(mapper.toDTO(paymentMethod));
                });
    }

    @Override
    public Mono<AgencyPaymentMethodDTO> setPrimaryPaymentMethod(UUID agencyId, UUID paymentMethodId) {
        return repository.findById(paymentMethodId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment method not found with ID: " + paymentMethodId)))
                .flatMap(paymentMethod -> {
                    if (!paymentMethod.getAgencyId().equals(agencyId)) {
                        return Mono.error(new RuntimeException("Payment method does not belong to agency: " + agencyId));
                    }
                    
                    // Unset all other primary payment methods
                    return repository.findByAgencyIdAndIsPrimary(agencyId, true)
                            .flatMap(existing -> {
                                if (!existing.getId().equals(paymentMethodId)) {
                                    existing.setIsPrimary(false);
                                    return repository.save(existing);
                                }
                                return Mono.just(existing);
                            })
                            .then(Mono.just(paymentMethod))
                            .doOnNext(pm -> pm.setIsPrimary(true))
                            .flatMap(repository::save)
                            .map(mapper::toDTO);
                });
    }
}

