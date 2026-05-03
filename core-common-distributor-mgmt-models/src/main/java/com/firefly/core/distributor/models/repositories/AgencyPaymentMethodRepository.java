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

import com.firefly.core.distributor.models.entities.AgencyPaymentMethod;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository interface for AgencyPaymentMethod entity.
 * Provides data access operations for agency payment methods.
 */
@Repository
public interface AgencyPaymentMethodRepository extends BaseRepository<AgencyPaymentMethod, UUID> {

    /**
     * Find all payment methods for a specific agency.
     *
     * @param agencyId the agency ID
     * @return Flux of payment methods
     */
    Flux<AgencyPaymentMethod> findByAgencyId(UUID agencyId);

    /**
     * Find all active payment methods for a specific agency.
     *
     * @param agencyId the agency ID
     * @param isActive the active status
     * @return Flux of payment methods
     */
    Flux<AgencyPaymentMethod> findByAgencyIdAndIsActive(UUID agencyId, Boolean isActive);

    /**
     * Find the primary payment method for a specific agency.
     *
     * @param agencyId the agency ID
     * @param isPrimary the primary status
     * @return Mono of payment method
     */
    Mono<AgencyPaymentMethod> findByAgencyIdAndIsPrimary(UUID agencyId, Boolean isPrimary);

    /**
     * Find active and primary payment method for a specific agency.
     *
     * @param agencyId the agency ID
     * @param isPrimary the primary status
     * @param isActive the active status
     * @return Mono of payment method
     */
    Mono<AgencyPaymentMethod> findByAgencyIdAndIsPrimaryAndIsActive(
            UUID agencyId, Boolean isPrimary, Boolean isActive);

    /**
     * Find payment methods by type for a specific agency.
     *
     * @param agencyId the agency ID
     * @param paymentMethodType the payment method type
     * @return Flux of payment methods
     */
    Flux<AgencyPaymentMethod> findByAgencyIdAndPaymentMethodType(UUID agencyId, String paymentMethodType);

    /**
     * Find verified payment methods for a specific agency.
     *
     * @param agencyId the agency ID
     * @param isVerified the verified status
     * @return Flux of payment methods
     */
    Flux<AgencyPaymentMethod> findByAgencyIdAndIsVerified(UUID agencyId, Boolean isVerified);

    /**
     * Find active and verified payment methods for a specific agency.
     *
     * @param agencyId the agency ID
     * @param isVerified the verified status
     * @param isActive the active status
     * @return Flux of payment methods
     */
    Flux<AgencyPaymentMethod> findByAgencyIdAndIsVerifiedAndIsActive(
            UUID agencyId, Boolean isVerified, Boolean isActive);
}

