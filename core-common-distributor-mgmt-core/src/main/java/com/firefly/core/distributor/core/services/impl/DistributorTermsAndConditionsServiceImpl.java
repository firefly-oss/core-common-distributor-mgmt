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
import com.firefly.core.distributor.core.mappers.DistributorTermsAndConditionsMapper;
import com.firefly.core.distributor.core.services.DistributorTermsAndConditionsService;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import com.firefly.core.distributor.models.repositories.DistributorTermsAndConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the DistributorTermsAndConditionsService interface.
 */
@Service
@Transactional
public class DistributorTermsAndConditionsServiceImpl implements DistributorTermsAndConditionsService {

    @Autowired
    private DistributorTermsAndConditionsRepository repository;

    @Autowired
    private DistributorTermsAndConditionsMapper mapper;

    @Override
    public Mono<PaginationResponse<DistributorTermsAndConditionsDTO>> filterDistributorTermsAndConditions(FilterRequest<DistributorTermsAndConditionsDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        DistributorTermsAndConditions.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> createDistributorTermsAndConditions(DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        return Mono.just(distributorTermsAndConditionsDTO)
                .map(mapper::toEntity)
                .doOnNext(termsAndConditions -> {
                    termsAndConditions.setCreatedAt(LocalDateTime.now());
                    if (termsAndConditions.getIsActive() == null) {
                        termsAndConditions.setIsActive(true);
                    }
                    if (termsAndConditions.getStatus() == null) {
                        termsAndConditions.setStatus("DRAFT");
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> updateDistributorTermsAndConditions(UUID id, DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO) {
        return repository.findById(id)
                .flatMap(existingTermsAndConditions -> {
                    DistributorTermsAndConditions updatedTermsAndConditions = mapper.toEntity(distributorTermsAndConditionsDTO);
                    updatedTermsAndConditions.setId(id);
                    updatedTermsAndConditions.setCreatedAt(existingTermsAndConditions.getCreatedAt());
                    updatedTermsAndConditions.setCreatedBy(existingTermsAndConditions.getCreatedBy());
                    updatedTermsAndConditions.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedTermsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteDistributorTermsAndConditions(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getDistributorTermsAndConditionsById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorId(UUID distributorId) {
        return repository.findByDistributorId(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getActiveTermsAndConditionsByDistributorId(UUID distributorId) {
        return repository.findByDistributorIdAndIsActiveTrue(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByStatus(String status) {
        return repository.findByStatus(status)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorIdAndStatus(UUID distributorId, String status) {
        return repository.findByDistributorIdAndStatus(distributorId, status)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByTemplateId(UUID templateId) {
        return repository.findByTemplateId(templateId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> updateStatus(UUID id, String status, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setStatus(status);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> signTermsAndConditions(UUID id, UUID signedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setStatus("SIGNED");
                    termsAndConditions.setSignedDate(LocalDateTime.now());
                    termsAndConditions.setSignedBy(signedBy);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(signedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Flux<DistributorTermsAndConditionsDTO> getExpiringTermsAndConditions(LocalDateTime expirationDate) {
        return repository.findByExpirationDateBeforeAndIsActiveTrue(expirationDate)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Boolean> hasActiveSignedTerms(UUID distributorId) {
        return repository.existsByDistributorIdAndStatusAndIsActiveTrue(distributorId, "SIGNED");
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> getLatestTermsAndConditions(UUID distributorId) {
        return repository.findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(distributorId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> activateTermsAndConditions(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setIsActive(true);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> deactivateTermsAndConditions(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(termsAndConditions -> {
                    termsAndConditions.setIsActive(false);
                    termsAndConditions.setUpdatedAt(LocalDateTime.now());
                    termsAndConditions.setUpdatedBy(updatedBy);
                    return repository.save(termsAndConditions);
                })
                .map(mapper::toDTO);
    }
}
