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
import com.firefly.core.distributor.core.mappers.TermsAndConditionsTemplateMapper;
import com.firefly.core.distributor.core.services.TermsAndConditionsTemplateService;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import com.firefly.core.distributor.models.entities.TermsAndConditionsTemplate;
import com.firefly.core.distributor.models.repositories.TermsAndConditionsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the TermsAndConditionsTemplateService interface.
 */
@Service
@Transactional
public class TermsAndConditionsTemplateServiceImpl implements TermsAndConditionsTemplateService {

    @Autowired
    private TermsAndConditionsTemplateRepository repository;

    @Autowired
    private TermsAndConditionsTemplateMapper mapper;

    @Override
    public Mono<PaginationResponse<TermsAndConditionsTemplateDTO>> filterTermsAndConditionsTemplates(FilterRequest<TermsAndConditionsTemplateDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        TermsAndConditionsTemplate.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> createTemplate(TermsAndConditionsTemplateDTO templateDTO) {
        return Mono.just(templateDTO)
                .map(mapper::toEntity)
                .doOnNext(template -> {
                    template.setCreatedAt(LocalDateTime.now());
                    if (template.getIsActive() == null) {
                        template.setIsActive(true);
                    }
                    if (template.getIsDefault() == null) {
                        template.setIsDefault(false);
                    }
                    if (template.getApprovalRequired() == null) {
                        template.setApprovalRequired(true);
                    }
                    if (template.getAutoRenewal() == null) {
                        template.setAutoRenewal(false);
                    }
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> updateTemplate(UUID id, TermsAndConditionsTemplateDTO templateDTO) {
        return repository.findById(id)
                .flatMap(existingTemplate -> {
                    TermsAndConditionsTemplate updatedTemplate = mapper.toEntity(templateDTO);
                    updatedTemplate.setId(id);
                    updatedTemplate.setCreatedAt(existingTemplate.getCreatedAt());
                    updatedTemplate.setCreatedBy(existingTemplate.getCreatedBy());
                    updatedTemplate.setUpdatedAt(LocalDateTime.now());
                    return repository.save(updatedTemplate);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteTemplate(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> getTemplateById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getActiveTemplates() {
        return repository.findByIsActiveTrue()
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getTemplatesByCategory(String category) {
        return repository.findByCategory(category)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getActiveTemplatesByCategory(String category) {
        return repository.findByCategoryAndIsActiveTrue(category)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> getTemplateByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getTemplatesByVersion(String version) {
        return repository.findByVersion(version)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getDefaultTemplates() {
        return repository.findByIsDefaultTrue()
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getActiveDefaultTemplates() {
        return repository.findByIsDefaultTrueAndIsActiveTrue()
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> getDefaultTemplateByCategory(String category) {
        return repository.findByCategoryAndIsDefaultTrueAndIsActiveTrue(category)
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getTemplatesRequiringApproval() {
        return repository.findByApprovalRequiredTrue()
                .map(mapper::toDTO);
    }

    @Override
    public Flux<TermsAndConditionsTemplateDTO> getAutoRenewalTemplates() {
        return repository.findByAutoRenewalTrue()
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> activateTemplate(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(template -> {
                    template.setIsActive(true);
                    template.setUpdatedAt(LocalDateTime.now());
                    template.setUpdatedBy(updatedBy);
                    return repository.save(template);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> deactivateTemplate(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(template -> {
                    template.setIsActive(false);
                    template.setUpdatedAt(LocalDateTime.now());
                    template.setUpdatedBy(updatedBy);
                    return repository.save(template);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> setAsDefault(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(template -> {
                    // First, remove default status from other templates in the same category
                    return repository.findByCategoryAndIsDefaultTrueAndIsActiveTrue(template.getCategory())
                            .flatMap(existingDefault -> {
                                existingDefault.setIsDefault(false);
                                existingDefault.setUpdatedAt(LocalDateTime.now());
                                existingDefault.setUpdatedBy(updatedBy);
                                return repository.save(existingDefault);
                            })
                            .then(Mono.just(template));
                })
                .flatMap(template -> {
                    template.setIsDefault(true);
                    template.setUpdatedAt(LocalDateTime.now());
                    template.setUpdatedBy(updatedBy);
                    return repository.save(template);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<TermsAndConditionsTemplateDTO> removeDefault(UUID id, UUID updatedBy) {
        return repository.findById(id)
                .flatMap(template -> {
                    template.setIsDefault(false);
                    template.setUpdatedAt(LocalDateTime.now());
                    template.setUpdatedBy(updatedBy);
                    return repository.save(template);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Boolean> templateNameExists(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Mono<Boolean> templateNameExistsForDifferentTemplate(String name, UUID excludeId) {
        return repository.existsByNameAndIdNot(name, excludeId);
    }
}
