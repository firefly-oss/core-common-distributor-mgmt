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


package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.core.mappers.TermsAndConditionsTemplateMapper;
import com.firefly.core.distributor.core.services.impl.TermsAndConditionsTemplateServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import com.firefly.core.distributor.models.entities.TermsAndConditionsTemplate;
import com.firefly.core.distributor.models.repositories.TermsAndConditionsTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.UUID;

public class TermsAndConditionsTemplateServiceImplTest {

    private TermsAndConditionsTemplateRepository repository;
    private TermsAndConditionsTemplateMapper mapper;
    private TermsAndConditionsTemplateServiceImpl service;

    private TermsAndConditionsTemplate template;
    private TermsAndConditionsTemplateDTO templateDTO;
    private UUID testId;
    private UUID templateId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(TermsAndConditionsTemplateRepository.class);
        mapper = mock(TermsAndConditionsTemplateMapper.class);
        service = new TermsAndConditionsTemplateServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = TermsAndConditionsTemplateServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = TermsAndConditionsTemplateServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test UUID
        testId = UUID.randomUUID();
        templateId = UUID.randomUUID();

        // Initialize test data
        template = TermsAndConditionsTemplate.builder()
                .id(testId)
                .name("Test Template")
                .description("Test template description")
                .category("GENERAL")
                .templateContent("Test template content with {{variable}}")
                .version("1.0")
                .isDefault(false)
                .isActive(true)
                .approvalRequired(true)
                .autoRenewal(false)
                .renewalPeriodMonths(12)
                .createdAt(LocalDateTime.now())
                .build();

        templateDTO = TermsAndConditionsTemplateDTO.builder()
                .id(testId)
                .name("Test Template")
                .description("Test template description")
                .category("GENERAL")
                .templateContent("Test template content with {{variable}}")
                .version("1.0")
                .isDefault(false)
                .isActive(true)
                .approvalRequired(true)
                .autoRenewal(false)
                .renewalPeriodMonths(12)
                .build();
    }

    @Test
    void createTemplate_ShouldCreateAndReturnTemplate() {
        // Arrange
        when(mapper.toEntity(any(TermsAndConditionsTemplateDTO.class))).thenReturn(template);
        when(repository.save(any(TermsAndConditionsTemplate.class))).thenReturn(Mono.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.createTemplate(templateDTO))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(templateDTO);
        verify(repository).save(any(TermsAndConditionsTemplate.class));
        verify(mapper).toDTO(template);
    }

    @Test
    void updateTemplate_WhenTemplateExists_ShouldUpdateAndReturnTemplate() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(template));
        when(mapper.toEntity(any(TermsAndConditionsTemplateDTO.class))).thenReturn(template);
        when(repository.save(any(TermsAndConditionsTemplate.class))).thenReturn(Mono.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.updateTemplate(testId, templateDTO))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(templateDTO);
        verify(repository).save(any(TermsAndConditionsTemplate.class));
        verify(mapper).toDTO(template);
    }

    @Test
    void getTemplateById_WhenTemplateExists_ShouldReturnTemplate() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getTemplateById(testId))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(template);
    }

    @Test
    void getTemplateById_WhenTemplateDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getTemplateById(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getActiveTemplates_ShouldReturnActiveTemplates() {
        // Arrange
        when(repository.findByIsActiveTrue()).thenReturn(Flux.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveTemplates())
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByIsActiveTrue();
        verify(mapper).toDTO(template);
    }

    @Test
    void getTemplatesByCategory_ShouldReturnTemplatesInCategory() {
        // Arrange
        when(repository.findByCategory(anyString())).thenReturn(Flux.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getTemplatesByCategory("GENERAL"))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByCategory("GENERAL");
        verify(mapper).toDTO(template);
    }

    @Test
    void getActiveTemplatesByCategory_ShouldReturnActiveTemplatesInCategory() {
        // Arrange
        when(repository.findByCategoryAndIsActiveTrue(anyString())).thenReturn(Flux.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveTemplatesByCategory("GENERAL"))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByCategoryAndIsActiveTrue("GENERAL");
        verify(mapper).toDTO(template);
    }

    @Test
    void getTemplateByName_WhenTemplateExists_ShouldReturnTemplate() {
        // Arrange
        when(repository.findByName(anyString())).thenReturn(Mono.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getTemplateByName("Test Template"))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByName("Test Template");
        verify(mapper).toDTO(template);
    }

    @Test
    void getDefaultTemplates_ShouldReturnDefaultTemplates() {
        // Arrange
        when(repository.findByIsDefaultTrue()).thenReturn(Flux.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getDefaultTemplates())
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByIsDefaultTrue();
        verify(mapper).toDTO(template);
    }

    @Test
    void getDefaultTemplateByCategory_WhenDefaultTemplateExists_ShouldReturnTemplate() {
        // Arrange
        when(repository.findByCategoryAndIsDefaultTrueAndIsActiveTrue(anyString())).thenReturn(Mono.just(template));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.getDefaultTemplateByCategory("GENERAL"))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByCategoryAndIsDefaultTrueAndIsActiveTrue("GENERAL");
        verify(mapper).toDTO(template);
    }

    @Test
    void activateTemplate_WhenTemplateExists_ShouldActivateAndReturnTemplate() {
        // Arrange
        UUID updatedBy = UUID.randomUUID();
        TermsAndConditionsTemplate inactiveTemplate = TermsAndConditionsTemplate.builder()
                .id(testId)
                .name("Test Template")
                .category("GENERAL")
                .templateContent("Test content")
                .version("1.0")
                .isActive(false)
                .build();

        TermsAndConditionsTemplate activeTemplate = TermsAndConditionsTemplate.builder()
                .id(testId)
                .name("Test Template")
                .category("GENERAL")
                .templateContent("Test content")
                .version("1.0")
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(inactiveTemplate));
        when(repository.save(any(TermsAndConditionsTemplate.class))).thenReturn(Mono.just(activeTemplate));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.activateTemplate(testId, updatedBy))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(TermsAndConditionsTemplate.class));
        verify(mapper).toDTO(activeTemplate);
    }

    @Test
    void deactivateTemplate_WhenTemplateExists_ShouldDeactivateAndReturnTemplate() {
        // Arrange
        UUID updatedBy = UUID.randomUUID();
        TermsAndConditionsTemplate inactiveTemplate = TermsAndConditionsTemplate.builder()
                .id(testId)
                .name("Test Template")
                .category("GENERAL")
                .templateContent("Test content")
                .version("1.0")
                .isActive(false)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(template));
        when(repository.save(any(TermsAndConditionsTemplate.class))).thenReturn(Mono.just(inactiveTemplate));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.deactivateTemplate(testId, updatedBy))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(TermsAndConditionsTemplate.class));
        verify(mapper).toDTO(inactiveTemplate);
    }

    @Test
    void setAsDefault_WhenTemplateExists_ShouldSetAsDefaultAndReturnTemplate() {
        // Arrange
        UUID updatedBy = UUID.randomUUID();
        TermsAndConditionsTemplate defaultTemplate = TermsAndConditionsTemplate.builder()
                .id(testId)
                .name("Test Template")
                .category("GENERAL")
                .templateContent("Test content")
                .version("1.0")
                .isDefault(true)
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(template));
        when(repository.findByCategoryAndIsDefaultTrueAndIsActiveTrue(anyString())).thenReturn(Mono.empty());
        when(repository.save(any(TermsAndConditionsTemplate.class))).thenReturn(Mono.just(defaultTemplate));
        when(mapper.toDTO(any(TermsAndConditionsTemplate.class))).thenReturn(templateDTO);

        // Act & Assert
        StepVerifier.create(service.setAsDefault(testId, updatedBy))
                .expectNext(templateDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).findByCategoryAndIsDefaultTrueAndIsActiveTrue("GENERAL");
        verify(repository).save(any(TermsAndConditionsTemplate.class));
        verify(mapper).toDTO(defaultTemplate);
    }

    @Test
    void templateNameExists_WhenNameExists_ShouldReturnTrue() {
        // Arrange
        when(repository.existsByName(anyString())).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(service.templateNameExists("Test Template"))
                .expectNext(true)
                .verifyComplete();

        // Verify
        verify(repository).existsByName("Test Template");
    }

    @Test
    void templateNameExists_WhenNameDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(repository.existsByName(anyString())).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(service.templateNameExists("Non-existent Template"))
                .expectNext(false)
                .verifyComplete();

        // Verify
        verify(repository).existsByName("Non-existent Template");
    }

    @Test
    void deleteTemplate_ShouldDeleteTemplate() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteTemplate(testId))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(testId);
    }
}
