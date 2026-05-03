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
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor audit logs.
 */
public interface DistributorAuditLogService {
    /**
     * Filters the distributor audit logs based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for DistributorAuditLogDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of distributor audit logs
     */
    Mono<PaginationResponse<DistributorAuditLogDTO>> filterDistributorAuditLogs(FilterRequest<DistributorAuditLogDTO> filterRequest);
    
    /**
     * Creates a new distributor audit log based on the provided information.
     *
     * @param distributorAuditLogDTO the DTO object containing details of the distributor audit log to be created
     * @return a Mono that emits the created DistributorAuditLogDTO object
     */
    Mono<DistributorAuditLogDTO> createDistributorAuditLog(DistributorAuditLogDTO distributorAuditLogDTO);
    
    /**
     * Updates an existing distributor audit log with updated information.
     *
     * @param distributorAuditLogId the unique identifier of the distributor audit log to be updated
     * @param distributorAuditLogDTO the data transfer object containing the updated details of the distributor audit log
     * @return a reactive Mono containing the updated DistributorAuditLogDTO
     */
    Mono<DistributorAuditLogDTO> updateDistributorAuditLog(UUID distributorAuditLogId, DistributorAuditLogDTO distributorAuditLogDTO);
    
    /**
     * Deletes a distributor audit log identified by its unique ID.
     *
     * @param distributorAuditLogId the unique identifier of the distributor audit log to be deleted
     * @return a Mono that completes when the distributor audit log is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteDistributorAuditLog(UUID distributorAuditLogId);
    
    /**
     * Retrieves a distributor audit log by its unique identifier.
     *
     * @param distributorAuditLogId the unique identifier of the distributor audit log to retrieve
     * @return a Mono emitting the {@link DistributorAuditLogDTO} representing the distributor audit log if found,
     *         or an empty Mono if the distributor audit log does not exist
     */
    Mono<DistributorAuditLogDTO> getDistributorAuditLogById(UUID distributorAuditLogId);
}