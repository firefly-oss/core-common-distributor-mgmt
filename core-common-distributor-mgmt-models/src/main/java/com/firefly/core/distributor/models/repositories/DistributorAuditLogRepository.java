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


package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.interfaces.enums.DistributorActionEnum;
import com.firefly.core.distributor.models.entities.DistributorAuditLog;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for managing {@link DistributorAuditLog} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorAuditLogRepository extends BaseRepository<DistributorAuditLog, UUID> {
    
    /**
     * Find all audit logs for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of audit logs for the distributor
     */
    Flux<DistributorAuditLog> findByDistributorId(UUID distributorId);
    
    /**
     * Find all audit logs for a specific distributor with pagination.
     *
     * @param distributorId the ID of the distributor
     * @param pageable pagination information
     * @return a Flux of audit logs for the distributor with pagination
     */
    Flux<DistributorAuditLog> findByDistributorId(UUID distributorId, Pageable pageable);
    
    /**
     * Find all audit logs for a specific action type.
     *
     * @param action the action type to search for
     * @return a Flux of audit logs for the specified action
     */
    Flux<DistributorAuditLog> findByAction(DistributorActionEnum action);
    
    /**
     * Find all audit logs for a specific entity type.
     *
     * @param entity the entity type to search for
     * @return a Flux of audit logs for the specified entity type
     */
    Flux<DistributorAuditLog> findByEntity(String entity);
    
    /**
     * Find all audit logs for a specific entity ID.
     *
     * @param entityId the entity ID to search for
     * @return a Flux of audit logs for the specified entity ID
     */
    Flux<DistributorAuditLog> findByEntityId(String entityId);
    
    /**
     * Find all audit logs within a time range.
     *
     * @param startTime the start time of the range
     * @param endTime the end time of the range
     * @return a Flux of audit logs within the specified time range
     */
    Flux<DistributorAuditLog> findByAuditTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find all audit logs for a specific distributor within a time range.
     *
     * @param distributorId the ID of the distributor
     * @param startTime the start time of the range
     * @param endTime the end time of the range
     * @return a Flux of audit logs for the distributor within the specified time range
     */
    Flux<DistributorAuditLog> findByDistributorIdAndAuditTimestampBetween(
            UUID distributorId, LocalDateTime startTime, LocalDateTime endTime);
}