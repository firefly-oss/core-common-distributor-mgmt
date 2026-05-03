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

import com.firefly.core.distributor.models.entities.DistributorTermsAndConditions;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for managing {@link DistributorTermsAndConditions} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorTermsAndConditionsRepository extends BaseRepository<DistributorTermsAndConditions, UUID> {
    
    /**
     * Find all terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorId(UUID distributorId);
    
    /**
     * Find all active terms and conditions for a specific distributor.
     *
     * @param distributorId the distributor ID to search for
     * @return a Flux of active distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndIsActiveTrue(UUID distributorId);
    
    /**
     * Find terms and conditions by status.
     *
     * @param status the status to search for
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByStatus(String status);
    
    /**
     * Find terms and conditions by distributor and status.
     *
     * @param distributorId the distributor ID
     * @param status the status
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndStatus(UUID distributorId, String status);
    
    /**
     * Find terms and conditions by template ID.
     *
     * @param templateId the template ID
     * @return a Flux of distributor terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByTemplateId(UUID templateId);
    
    /**
     * Find active terms and conditions for a distributor and template.
     *
     * @param distributorId the distributor ID
     * @param templateId the template ID
     * @return a Mono containing the active terms and conditions if found
     */
    Mono<DistributorTermsAndConditions> findByDistributorIdAndTemplateIdAndIsActiveTrue(UUID distributorId, UUID templateId);
    
    /**
     * Find terms and conditions expiring before a certain date.
     *
     * @param expirationDate the expiration date threshold
     * @return a Flux of expiring terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByExpirationDateBeforeAndIsActiveTrue(LocalDateTime expirationDate);
    
    /**
     * Find terms and conditions effective after a certain date.
     *
     * @param effectiveDate the effective date threshold
     * @return a Flux of terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByEffectiveDateAfter(LocalDateTime effectiveDate);
    
    /**
     * Find signed terms and conditions for a distributor.
     *
     * @param distributorId the distributor ID
     * @return a Flux of signed terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByDistributorIdAndStatusAndIsActiveTrue(UUID distributorId, String status);
    
    /**
     * Check if a distributor has active terms and conditions.
     *
     * @param distributorId the distributor ID
     * @return a Mono containing true if active terms exist
     */
    Mono<Boolean> existsByDistributorIdAndStatusAndIsActiveTrue(UUID distributorId, String status);
    
    /**
     * Find terms and conditions by version.
     *
     * @param version the version to search for
     * @return a Flux of terms and conditions
     */
    Flux<DistributorTermsAndConditions> findByVersion(String version);
    
    /**
     * Find the latest version of terms and conditions for a distributor.
     *
     * @param distributorId the distributor ID
     * @return a Mono containing the latest terms and conditions
     */
    Mono<DistributorTermsAndConditions> findTopByDistributorIdAndIsActiveTrueOrderByCreatedAtDesc(UUID distributorId);
}
