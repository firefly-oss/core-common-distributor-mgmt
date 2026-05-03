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

import com.firefly.core.distributor.core.mappers.DistributorTermsAndConditionsMapper;
import com.firefly.core.distributor.core.services.impl.DistributorTermsAndConditionsServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import com.firefly.core.distributor.models.repositories.DistributorTermsAndConditionsRepository;
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

public class DistributorTermsAndConditionsServiceImplTest {

    private DistributorTermsAndConditionsRepository repository;
    private DistributorTermsAndConditionsMapper mapper;
    private DistributorTermsAndConditionsServiceImpl service;

    private DistributorTermsAndConditions distributorTermsAndConditions;
    private DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO;
    private UUID testId;
    private UUID templateId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorTermsAndConditionsRepository.class);
        mapper = mock(DistributorTermsAndConditionsMapper.class);
        service = new DistributorTermsAndConditionsServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorTermsAndConditionsServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorTermsAndConditionsServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);

        // Initialize test UUID
        testId = UUID.randomUUID();
        templateId = UUID.randomUUID();
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorTermsAndConditionsDTO = DistributorTermsAndConditionsDTO.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorTermsAndConditions_ShouldCreateAndReturnTermsAndConditions() {
        // Arrange
        when(mapper.toEntity(any(DistributorTermsAndConditionsDTO.class))).thenReturn(distributorTermsAndConditions);
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorTermsAndConditions(distributorTermsAndConditionsDTO))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorTermsAndConditionsDTO);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void updateDistributorTermsAndConditions_WhenTermsAndConditionsExists_ShouldUpdateAndReturnTermsAndConditions() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toEntity(any(DistributorTermsAndConditionsDTO.class))).thenReturn(distributorTermsAndConditions);
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorTermsAndConditions(testId, distributorTermsAndConditionsDTO))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(distributorTermsAndConditionsDTO);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getDistributorTermsAndConditionsById_WhenTermsAndConditionsExists_ShouldReturnTermsAndConditions() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorTermsAndConditionsById(testId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getDistributorTermsAndConditionsById_WhenTermsAndConditionsDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorTermsAndConditionsById(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getTermsAndConditionsByDistributorId_ShouldReturnTermsAndConditions() {
        // Arrange
        when(repository.findByDistributorId(any(UUID.class))).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getTermsAndConditionsByDistributorId(testId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(testId);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getActiveTermsAndConditionsByDistributorId_ShouldReturnActiveTermsAndConditions() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(any(UUID.class))).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveTermsAndConditionsByDistributorId(testId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(testId);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void getTermsAndConditionsByStatus_ShouldReturnTermsAndConditionsWithStatus() {
        // Arrange
        when(repository.findByStatus(anyString())).thenReturn(Flux.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getTermsAndConditionsByStatus("DRAFT"))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByStatus("DRAFT");
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void updateStatus_WhenTermsAndConditionsExists_ShouldUpdateStatusAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions updatedTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("SIGNED")
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(updatedTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.updateStatus(testId, "SIGNED", testId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(updatedTermsAndConditions);
    }

    @Test
    void signTermsAndConditions_WhenTermsAndConditionsExists_ShouldSignAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions signedTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("SIGNED")
                .signedDate(LocalDateTime.now())
                .signedBy(testId)
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(signedTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.signTermsAndConditions(testId, templateId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(signedTermsAndConditions);
    }

    @Test
    void hasActiveSignedTerms_WhenActiveSignedTermsExist_ShouldReturnTrue() {
        // Arrange
        when(repository.existsByDistributorIdAndStatusAndIsActiveTrue(any(UUID.class), anyString())).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(service.hasActiveSignedTerms(testId))
                .expectNext(true)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndStatusAndIsActiveTrue(testId, "SIGNED");
    }

    @Test
    void hasActiveSignedTerms_WhenNoActiveSignedTermsExist_ShouldReturnFalse() {
        // Arrange
        when(repository.existsByDistributorIdAndStatusAndIsActiveTrue(any(UUID.class), anyString())).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(service.hasActiveSignedTerms(testId))
                .expectNext(false)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndStatusAndIsActiveTrue(testId, "SIGNED");
    }

    @Test
    void getLatestTermsAndConditions_WhenTermsAndConditionsExists_ShouldReturnLatestTermsAndConditions() {
        // Arrange
        when(repository.findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(any(UUID.class))).thenReturn(Mono.just(distributorTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.getLatestTermsAndConditions(testId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(testId);
        verify(mapper).toDTO(distributorTermsAndConditions);
    }

    @Test
    void activateTermsAndConditions_WhenTermsAndConditionsExists_ShouldActivateAndReturnTermsAndConditions() {
        // Arrange
        DistributorTermsAndConditions inactiveTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(false)
                .build();

        DistributorTermsAndConditions activeTermsAndConditions = DistributorTermsAndConditions.builder()
                .id(testId)
                .distributorId(testId)
                .templateId(testId)
                .title("Test Terms and Conditions")
                .content("Test content")
                .version("1.0")
                .effectiveDate(LocalDateTime.now())
                .status("DRAFT")
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(inactiveTermsAndConditions));
        when(repository.save(any(DistributorTermsAndConditions.class))).thenReturn(Mono.just(activeTermsAndConditions));
        when(mapper.toDTO(any(DistributorTermsAndConditions.class))).thenReturn(distributorTermsAndConditionsDTO);

        // Act & Assert
        StepVerifier.create(service.activateTermsAndConditions(testId, templateId))
                .expectNext(distributorTermsAndConditionsDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorTermsAndConditions.class));
        verify(mapper).toDTO(activeTermsAndConditions);
    }

    @Test
    void deleteDistributorTermsAndConditions_ShouldDeleteTermsAndConditions() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorTermsAndConditions(testId))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(testId);
    }
}
