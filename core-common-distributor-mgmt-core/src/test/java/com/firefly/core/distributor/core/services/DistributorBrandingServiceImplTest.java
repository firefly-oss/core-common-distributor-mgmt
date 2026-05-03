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
import com.firefly.core.distributor.core.mappers.DistributorBrandingMapper;
import com.firefly.core.distributor.core.services.impl.DistributorBrandingServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import com.firefly.core.distributor.models.repositories.DistributorBrandingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;

public class DistributorBrandingServiceImplTest {

    private DistributorBrandingRepository repository;
    private DistributorBrandingMapper mapper;
    private DistributorBrandingServiceImpl service;

    private DistributorBranding distributorBranding;
    private DistributorBrandingDTO distributorBrandingDTO;
    private FilterRequest<DistributorBrandingDTO> filterRequest;
    private UUID testId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorBrandingRepository.class);
        mapper = mock(DistributorBrandingMapper.class);
        service = new DistributorBrandingServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorBrandingServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorBrandingServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);

        // Initialize test UUID
        testId = UUID.randomUUID();
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test data
        distributorBranding = new DistributorBranding();
        distributorBranding.setId(testId);

        distributorBrandingDTO = new DistributorBrandingDTO();
        distributorBrandingDTO.setId(testId);

        filterRequest = new FilterRequest<>();
    }

    @Test
    void createDistributorBranding_ShouldCreateAndReturnDistributorBranding() {
        // Arrange
        when(mapper.toEntity(any(DistributorBrandingDTO.class))).thenReturn(distributorBranding);
        when(repository.save(any(DistributorBranding.class))).thenReturn(Mono.just(distributorBranding));
        when(mapper.toDTO(any(DistributorBranding.class))).thenReturn(distributorBrandingDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorBranding(distributorBrandingDTO))
                .expectNext(distributorBrandingDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorBrandingDTO);
        verify(repository).save(distributorBranding);
        verify(mapper).toDTO(distributorBranding);
    }

    @Test
    void updateDistributorBranding_WhenDistributorBrandingExists_ShouldUpdateAndReturnDistributorBranding() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorBranding));
        doNothing().when(mapper).updateEntityFromDto(any(DistributorBrandingDTO.class), any(DistributorBranding.class));
        when(repository.save(any(DistributorBranding.class))).thenReturn(Mono.just(distributorBranding));
        when(mapper.toDTO(any(DistributorBranding.class))).thenReturn(distributorBrandingDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorBranding(testId, distributorBrandingDTO))
                .expectNext(distributorBrandingDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).updateEntityFromDto(distributorBrandingDTO, distributorBranding);
        verify(repository).save(distributorBranding);
        verify(mapper).toDTO(distributorBranding);
    }

    @Test
    void updateDistributorBranding_WhenDistributorBrandingDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDistributorBranding(testId, distributorBrandingDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor branding not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deleteDistributorBranding_WhenDistributorBrandingExists_ShouldDeleteDistributorBranding() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorBranding));
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorBranding(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).deleteById(testId);
    }

    @Test
    void deleteDistributorBranding_WhenDistributorBrandingDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorBranding(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor branding not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void getDistributorBrandingById_WhenDistributorBrandingExists_ShouldReturnDistributorBranding() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorBranding));
        when(mapper.toDTO(any(DistributorBranding.class))).thenReturn(distributorBrandingDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorBrandingById(testId))
                .expectNext(distributorBrandingDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributorBranding);
    }

    @Test
    void getDistributorBrandingById_WhenDistributorBrandingDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorBrandingById(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor branding not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }
}
