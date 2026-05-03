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

import com.firefly.core.distributor.core.mappers.DistributorSimulationMapper;
import com.firefly.core.distributor.core.services.impl.DistributorSimulationServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import com.firefly.core.distributor.models.entities.DistributorSimulation;
import com.firefly.core.distributor.models.repositories.DistributorSimulationRepository;
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

public class DistributorSimulationServiceImplTest {

    private DistributorSimulationRepository repository;
    private DistributorSimulationMapper mapper;
    private DistributorSimulationServiceImpl service;

    private DistributorSimulation distributorSimulation;
    private DistributorSimulationDTO distributorSimulationDTO;
    private UUID testId;
    private UUID simulationTypeId;
    private UUID applicationId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorSimulationRepository.class);
        mapper = mock(DistributorSimulationMapper.class);
        service = new DistributorSimulationServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorSimulationServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorSimulationServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);

        // Initialize test UUID
        testId = UUID.randomUUID();
        simulationTypeId = UUID.randomUUID();
        applicationId = UUID.randomUUID();
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorSimulation = DistributorSimulation.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("PENDING")
                .notes("Test simulation")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        distributorSimulationDTO = DistributorSimulationDTO.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("PENDING")
                .notes("Test simulation")
                .isActive(true)
                .build();
    }

    @Test
    void createDistributorSimulation_ShouldCreateAndReturnSimulation() {
        // Arrange
        when(mapper.toEntity(any(DistributorSimulationDTO.class))).thenReturn(distributorSimulation);
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorSimulation(distributorSimulationDTO))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorSimulationDTO);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void updateDistributorSimulation_WhenSimulationExists_ShouldUpdateAndReturnSimulation() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toEntity(any(DistributorSimulationDTO.class))).thenReturn(distributorSimulation);
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorSimulation(testId, distributorSimulationDTO))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(distributorSimulationDTO);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getDistributorSimulationById_WhenSimulationExists_ShouldReturnSimulation() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorSimulationById(testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getDistributorSimulationById_WhenSimulationDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorSimulationById(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void getSimulationsByDistributorId_ShouldReturnSimulations() {
        // Arrange
        when(repository.findByDistributorId(any(UUID.class))).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByDistributorId(testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorId(testId);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getActiveSimulationsByDistributorId_ShouldReturnActiveSimulations() {
        // Arrange
        when(repository.findByDistributorIdAndIsActiveTrue(any(UUID.class))).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getActiveSimulationsByDistributorId(testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndIsActiveTrue(testId);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationByApplicationId_WhenSimulationExists_ShouldReturnSimulation() {
        // Arrange
        when(repository.findByApplicationId(any(UUID.class))).thenReturn(Mono.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationByApplicationId(testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByApplicationId(testId);
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationsByStatus_ShouldReturnSimulationsWithStatus() {
        // Arrange
        when(repository.findBySimulationStatus(anyString())).thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByStatus("PENDING"))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findBySimulationStatus("PENDING");
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void getSimulationsByDistributorIdAndStatus_ShouldReturnSimulations() {
        // Arrange
        when(repository.findByDistributorIdAndSimulationStatus(any(UUID.class), anyString()))
                .thenReturn(Flux.just(distributorSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.getSimulationsByDistributorIdAndStatus(testId, "PENDING"))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findByDistributorIdAndSimulationStatus(testId, "PENDING");
        verify(mapper).toDTO(distributorSimulation);
    }

    @Test
    void updateSimulationStatus_WhenSimulationExists_ShouldUpdateStatusAndReturnSimulation() {
        // Arrange
        DistributorSimulation updatedSimulation = DistributorSimulation.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("COMPLETED")
                .notes("Test simulation")
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(updatedSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.updateSimulationStatus(testId, "COMPLETED", testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(updatedSimulation);
    }

    @Test
    void activateDistributorSimulation_WhenSimulationExists_ShouldActivateAndReturnSimulation() {
        // Arrange
        DistributorSimulation inactiveSimulation = DistributorSimulation.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("PENDING")
                .isActive(false)
                .build();

        DistributorSimulation activeSimulation = DistributorSimulation.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("PENDING")
                .isActive(true)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(inactiveSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(activeSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.activateDistributorSimulation(testId, testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(activeSimulation);
    }

    @Test
    void deactivateDistributorSimulation_WhenSimulationExists_ShouldDeactivateAndReturnSimulation() {
        // Arrange
        DistributorSimulation inactiveSimulation = DistributorSimulation.builder()
                .id(testId)
                .distributorId(testId)
                .applicationId(testId)
                .simulationStatus("PENDING")
                .isActive(false)
                .build();

        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorSimulation));
        when(repository.save(any(DistributorSimulation.class))).thenReturn(Mono.just(inactiveSimulation));
        when(mapper.toDTO(any(DistributorSimulation.class))).thenReturn(distributorSimulationDTO);

        // Act & Assert
        StepVerifier.create(service.deactivateDistributorSimulation(testId, testId))
                .expectNext(distributorSimulationDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).save(any(DistributorSimulation.class));
        verify(mapper).toDTO(inactiveSimulation);
    }

    @Test
    void deleteDistributorSimulation_ShouldDeleteSimulation() {
        // Arrange
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorSimulation(testId))
                .verifyComplete();

        // Verify
        verify(repository).deleteById(testId);
    }
}
