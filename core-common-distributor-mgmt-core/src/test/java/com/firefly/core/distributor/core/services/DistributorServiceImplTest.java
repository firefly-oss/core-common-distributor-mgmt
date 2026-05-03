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

import org.fireflyframework.core.filters.FilterRequest;
import com.firefly.core.distributor.core.mappers.DistributorMapper;
import com.firefly.core.distributor.core.services.impl.DistributorServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorDTO;
import com.firefly.core.distributor.models.entities.Distributor;
import com.firefly.core.distributor.models.repositories.DistributorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

public class DistributorServiceImplTest {

    private DistributorRepository repository;
    private DistributorMapper mapper;
    private DistributorServiceImpl service;

    private Distributor distributor;
    private DistributorDTO distributorDTO;
    private FilterRequest<DistributorDTO> filterRequest;
    private UUID testId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorRepository.class);
        mapper = mock(DistributorMapper.class);
        service = new DistributorServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);

        // Initialize test UUID
        testId = UUID.randomUUID();
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributor = new Distributor();
        distributor.setId(testId);
        distributor.setName("Test Distributor");

        distributorDTO = new DistributorDTO();
        distributorDTO.setId(testId);
        distributorDTO.setName("Test Distributor");

        filterRequest = new FilterRequest<>();
    }

    @Test
    void createDistributor_ShouldCreateAndReturnDistributor() {
        // Arrange
        when(mapper.toEntity(any(DistributorDTO.class))).thenReturn(distributor);
        when(repository.save(any(Distributor.class))).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributor(distributorDTO))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorDTO);
        verify(repository).save(distributor);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void updateDistributor_WhenDistributorExists_ShouldUpdateAndReturnDistributor() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributor));
        when(mapper.toEntity(any(DistributorDTO.class))).thenReturn(distributor);
        when(repository.save(any(Distributor.class))).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributor(testId, distributorDTO))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(distributorDTO);
        verify(repository).save(distributor);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void updateDistributor_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDistributor(testId, distributorDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deleteDistributor_WhenDistributorExists_ShouldDeleteDistributor() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributor));
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributor(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).deleteById(testId);
    }

    @Test
    void deleteDistributor_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributor(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void getDistributorById_WhenDistributorExists_ShouldReturnDistributor() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributor));
        when(mapper.toDTO(any(Distributor.class))).thenReturn(distributorDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorById(testId))
                .expectNext(distributorDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributor);
    }

    @Test
    void getDistributorById_WhenDistributorDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorById(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }
}
