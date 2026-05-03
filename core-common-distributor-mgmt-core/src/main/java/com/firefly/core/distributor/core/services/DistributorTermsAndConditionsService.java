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
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service interface for managing distributor terms and conditions.
 */
public interface DistributorTermsAndConditionsService {

    /**
     * Filters the distributor terms and conditions based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list
     */
    Mono<PaginationResponse<DistributorTermsAndConditionsDTO>> filterDistributorTermsAndConditions(FilterRequest<DistributorTermsAndConditionsDTO> filterRequest);

    /**
     * Creates new distributor terms and conditions.
     *
     * @param distributorTermsAndConditionsDTO the terms and conditions to create
     * @return a reactive {@code Mono} emitting the created terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> createDistributorTermsAndConditions(DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO);

    /**
     * Updates existing distributor terms and conditions.
     *
     * @param id the ID of the terms and conditions to update
     * @param distributorTermsAndConditionsDTO the updated terms and conditions data
     * @return a reactive {@code Mono} emitting the updated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> updateDistributorTermsAndConditions(UUID id, DistributorTermsAndConditionsDTO distributorTermsAndConditionsDTO);

    /**
     * Deletes distributor terms and conditions by its ID.
     *
     * @param id the ID of the terms and conditions to delete
     * @return a reactive {@code Mono} that completes when the terms and conditions are deleted
     */
    Mono<Void> deleteDistributorTermsAndConditions(UUID id);

    /**
     * Retrieves distributor terms and conditions by its ID.
     *
     * @param id the ID of the terms and conditions to retrieve
     * @return a reactive {@code Mono} emitting the terms and conditions if found
     */
    Mono<DistributorTermsAndConditionsDTO> getDistributorTermsAndConditionsById(UUID id);

    /**
     * Retrieves all terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all terms and conditions for the distributor
     */
    Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorId(UUID distributorId);

    /**
     * Retrieves all active terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Flux} emitting all active terms and conditions for the distributor
     */
    Flux<DistributorTermsAndConditionsDTO> getActiveTermsAndConditionsByDistributorId(UUID distributorId);

    /**
     * Retrieves terms and conditions by status.
     *
     * @param status the status
     * @return a reactive {@code Flux} emitting terms and conditions with the specified status
     */
    Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByStatus(String status);

    /**
     * Retrieves terms and conditions by distributor and status.
     *
     * @param distributorId the distributor ID
     * @param status the status
     * @return a reactive {@code Flux} emitting terms and conditions for the distributor with the specified status
     */
    Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByDistributorIdAndStatus(UUID distributorId, String status);

    /**
     * Retrieves terms and conditions by template ID.
     *
     * @param templateId the template ID
     * @return a reactive {@code Flux} emitting terms and conditions using the specified template
     */
    Flux<DistributorTermsAndConditionsDTO> getTermsAndConditionsByTemplateId(UUID templateId);

    /**
     * Updates the status of distributor terms and conditions.
     *
     * @param id the ID of the terms and conditions
     * @param status the new status
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> updateStatus(UUID id, String status, UUID updatedBy);

    /**
     * Signs the terms and conditions.
     *
     * @param id the ID of the terms and conditions
     * @param signedBy the ID of the user signing
     * @return a reactive {@code Mono} emitting the signed terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> signTermsAndConditions(UUID id, UUID signedBy);

    /**
     * Retrieves terms and conditions expiring before a certain date.
     *
     * @param expirationDate the expiration date threshold
     * @return a reactive {@code Flux} emitting expiring terms and conditions
     */
    Flux<DistributorTermsAndConditionsDTO> getExpiringTermsAndConditions(LocalDateTime expirationDate);

    /**
     * Checks if a distributor has active signed terms and conditions.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Mono} emitting true if active signed terms exist
     */
    Mono<Boolean> hasActiveSignedTerms(UUID distributorId);

    /**
     * Gets the latest version of terms and conditions for a distributor.
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Mono} emitting the latest terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> getLatestTermsAndConditions(UUID distributorId);

    /**
     * Activates distributor terms and conditions.
     *
     * @param id the ID of the terms and conditions to activate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> activateTermsAndConditions(UUID id, UUID updatedBy);

    /**
     * Deactivates distributor terms and conditions.
     *
     * @param id the ID of the terms and conditions to deactivate
     * @param updatedBy the ID of the user performing the update
     * @return a reactive {@code Mono} emitting the updated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> deactivateTermsAndConditions(UUID id, UUID updatedBy);
}
