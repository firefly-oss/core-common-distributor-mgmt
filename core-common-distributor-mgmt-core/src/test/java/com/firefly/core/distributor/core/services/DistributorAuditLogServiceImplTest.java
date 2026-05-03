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
import com.firefly.core.distributor.core.mappers.DistributorAuditLogMapper;
import com.firefly.core.distributor.core.services.impl.DistributorAuditLogServiceImpl;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import com.firefly.core.distributor.models.entities.DistributorAuditLog;
import com.firefly.core.distributor.models.repositories.DistributorAuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.UUID;
import java.util.UUID;

public class DistributorAuditLogServiceImplTest {

    private DistributorAuditLogRepository repository;
    private DistributorAuditLogMapper mapper;
    private DistributorAuditLogServiceImpl service;

    private DistributorAuditLog distributorAuditLog;
    private DistributorAuditLogDTO distributorAuditLogDTO;
    private FilterRequest<DistributorAuditLogDTO> filterRequest;
    private UUID testId;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        repository = mock(DistributorAuditLogRepository.class);
        mapper = mock(DistributorAuditLogMapper.class);
        service = new DistributorAuditLogServiceImpl();

        // Use reflection to set the mocked dependencies
        try {
            java.lang.reflect.Field repoField = DistributorAuditLogServiceImpl.class.getDeclaredField("repository");
            repoField.setAccessible(true);
            repoField.set(service, repository);

            java.lang.reflect.Field mapperField = DistributorAuditLogServiceImpl.class.getDeclaredField("mapper");
            mapperField.setAccessible(true);
            mapperField.set(service, mapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up test", e);
        }

        // Initialize test UUID
        testId = UUID.randomUUID();

        // Initialize test data
        distributorAuditLog = new DistributorAuditLog();
        distributorAuditLog.setId(testId);

        distributorAuditLogDTO = new DistributorAuditLogDTO();
        distributorAuditLogDTO.setId(testId);

        filterRequest = new FilterRequest<>();
    }

    @Test
    void createDistributorAuditLog_ShouldCreateAndReturnDistributorAuditLog() {
        // Arrange
        when(mapper.toEntity(any(DistributorAuditLogDTO.class))).thenReturn(distributorAuditLog);
        when(repository.save(any(DistributorAuditLog.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.createDistributorAuditLog(distributorAuditLogDTO))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(mapper).toEntity(distributorAuditLogDTO);
        verify(repository).save(distributorAuditLog);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void updateDistributorAuditLog_WhenDistributorAuditLogExists_ShouldUpdateAndReturnDistributorAuditLog() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toEntity(any(DistributorAuditLogDTO.class))).thenReturn(distributorAuditLog);
        when(repository.save(any(DistributorAuditLog.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.updateDistributorAuditLog(testId, distributorAuditLogDTO))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toEntity(distributorAuditLogDTO);
        verify(repository).save(distributorAuditLog);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void updateDistributorAuditLog_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updateDistributorAuditLog(testId, distributorAuditLogDTO))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDTO(any());
    }

    @Test
    void deleteDistributorAuditLog_WhenDistributorAuditLogExists_ShouldDeleteDistributorAuditLog() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorAuditLog));
        when(repository.deleteById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorAuditLog(testId))
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(repository).deleteById(testId);
    }

    @Test
    void deleteDistributorAuditLog_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deleteDistributorAuditLog(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(repository, never()).deleteById(any(UUID.class));
    }

    @Test
    void getDistributorAuditLogById_WhenDistributorAuditLogExists_ShouldReturnDistributorAuditLog() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(distributorAuditLog));
        when(mapper.toDTO(any(DistributorAuditLog.class))).thenReturn(distributorAuditLogDTO);

        // Act & Assert
        StepVerifier.create(service.getDistributorAuditLogById(testId))
                .expectNext(distributorAuditLogDTO)
                .verifyComplete();

        // Verify
        verify(repository).findById(testId);
        verify(mapper).toDTO(distributorAuditLog);
    }

    @Test
    void getDistributorAuditLogById_WhenDistributorAuditLogDoesNotExist_ShouldReturnError() {
        // Arrange
        when(repository.findById(any(UUID.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getDistributorAuditLogById(testId))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Distributor audit log not found with ID: " + testId))
                .verify();

        // Verify
        verify(repository).findById(testId);
        verify(mapper, never()).toDTO(any());
    }
}
