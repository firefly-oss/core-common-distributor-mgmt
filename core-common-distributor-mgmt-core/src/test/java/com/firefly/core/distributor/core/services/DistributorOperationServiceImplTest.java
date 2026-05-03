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

import com.firefly.core.distributor.core.mappers.DistributorOperationMapper;
import com.firefly.core.distributor.core.services.impl.DistributorOperationServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import com.firefly.core.distributor.models.entities.DistributorOperation;
import com.firefly.core.distributor.models.repositories.DistributorOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import java.util.UUID;

public class DistributorOperationServiceImplTest {

    private DistributorOperationRepository repository;
    private DistributorOperationMapper mapper;
    private DistributorOperationServiceImpl service;

    private DistributorOperation distributorOperation;
    private DistributorOperationDTO distributorOperationDTO;
    private UUID testId;
    private UUID countryId;
    private UUID administrativeDivisionId;
    private UUID updatedBy;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorOperationRepository.class);
        mapper = mock(DistributorOperationMapper.class);
        service = new DistributorOperationServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorOperationServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorOperationServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);

        // Initialize test UUIDs
        testId = UUID.randomUUID();
        countryId = UUID.randomUUID();
        administrativeDivisionId = UUID.randomUUID();
        updatedBy = UUID.randomUUID();
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorOperation = DistributorOperation.builder()
                .id(testId)
                .distributorId(testId)
                .countryId(testId)
                .administrativeDivisionId(testId)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorOperationDTO = DistributorOperationDTO.builder()
                .id(testId)
                .distributorId(testId)
                .countryId(testId)
                .administrativeDivisionId(testId)
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorOperation_ShouldCreateAndReturnOperation() {
        // Arrange
        when(mapper.toEntity(any(DistributorOperationDTO.class))).thenReturn(distributorOperation);
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorOperation(distributorOperationDTO))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorOperationDTO);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void updateDistributorOperation_WhenOperationExists_ShouldUpdateAndReturnOperation() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toEntity(any(DistributorOperationDTO.class))).thenReturn(distributorOperation);
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorOperation(testId, distributorOperationDTO))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(distributorOperationDTO);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getDistributorOperationById_WhenOperationExists_ShouldReturnOperation() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorOperationById(testId))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getDistributorOperationById_WhenOperationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorOperationById(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getOperationsByDistributorId_ShouldReturnOperations() {
        // Arrange
        when(repository.findByDistributorId(any(UUID.class))).thenReturn(Flux.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getOperationsByDistributorId(testId))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(testId);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void getActiveOperationsByDistributorId_ShouldReturnActiveOperations() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(any(UUID.class))).thenReturn(Flux.just(distributorOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveOperationsByDistributorId(testId))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(testId);
        verify(mapper).toDTO(distributorOperation);
    }

    @Test
    void canDistributorOperateInLocation_WhenOperationExists_ShouldReturnTrue() {
        // Arrange
        when(repository.existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
                any(UUID.class), any(UUID.class), any(UUID.class))).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(service.canDistributorOperateInLocation(testId, countryId, administrativeDivisionId))
                .expectNext(true)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(testId, countryId, administrativeDivisionId);
    }

    @Test
    void canDistributorOperateInLocation_WhenOperationDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(repository.existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(
                any(UUID.class), any(UUID.class), any(UUID.class))).thenReturn(Mono.just(false));

        // Act & Assert
        StepVerifier.create(service.canDistributorOperateInLocation(testId, countryId, administrativeDivisionId))
                .expectNext(false)
                .verifyComplete();

        // Verify
        verify(repository).existsByDistributorIdAndCountryIdAndAdministrativeDivisionIdAndIsActiveTrue(testId, countryId, administrativeDivisionId);
    }

    @Test
    void activateDistributorOperation_WhenOperationExists_ShouldActivateAndReturnOperation() {
        // Arrange
        DistributorOperation inactiveOperation = DistributorOperation.builder()
                .id(testId)
                .distributorId(testId)
                .countryId(testId)
                .administrativeDivisionId(testId)
                .isActive(false)
                .build();

        DistributorOperation activeOperation = DistributorOperation.builder()
                .id(testId)
                .distributorId(testId)
                .countryId(testId)
                .administrativeDivisionId(testId)
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(inactiveOperation));
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(activeOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.activateDistributorOperation(testId, updatedBy))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(activeOperation);
    }

    @Test
    void deactivateDistributorOperation_WhenOperationExists_ShouldDeactivateAndReturnOperation() {
        // Arrange
        DistributorOperation inactiveOperation = DistributorOperation.builder()
                .id(testId)
                .distributorId(testId)
                .countryId(testId)
                .administrativeDivisionId(testId)
                .isActive(false)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorOperation));
        when(repository.save(any(DistributorOperation.class))).thenReturn(Mono.just(inactiveOperation));
        when(mapper.toDTO(any(DistributorOperation.class))).thenReturn(distributorOperationDTO);

        // Act & Assert
        StepVerifier.create(service.deactivateDistributorOperation(testId, updatedBy))
                .expectNext(distributorOperationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorOperation.class));
        verify(mapper).toDTO(inactiveOperation);
    }

    @Test
    void deleteDistributorOperation_ShouldDeleteOperation() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorOperation(testId))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(testId);
    }
}
