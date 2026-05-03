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
import com.firefly.core.distributor.interfaces.dtos.AgencyPaymentMethodDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing agency payment methods.
 */
public interface AgencyPaymentMethodService {
    /**
     * Filters the payment methods based on the given criteria for a specific agency.
     *
     * @param agencyId the unique identifier of the agency
     * @param filterRequest the request object containing filtering criteria for AgencyPaymentMethodDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of payment methods
     */
    Mono<PaginationResponse<AgencyPaymentMethodDTO>> filterPaymentMethods(UUID agencyId, FilterRequest<AgencyPaymentMethodDTO> filterRequest);
    
    /**
     * Creates a new payment method for a specific agency.
     *
     * @param agencyId the unique identifier of the agency
     * @param dto the DTO object containing details of the payment method to be created
     * @return a Mono that emits the created AgencyPaymentMethodDTO object
     */
    Mono<AgencyPaymentMethodDTO> createPaymentMethod(UUID agencyId, AgencyPaymentMethodDTO dto);
    
    /**
     * Updates an existing payment method with updated information.
     *
     * @param agencyId the unique identifier of the agency
     * @param paymentMethodId the unique identifier of the payment method to be updated
     * @param dto the data transfer object containing the updated details of the payment method
     * @return a reactive Mono containing the updated AgencyPaymentMethodDTO
     */
    Mono<AgencyPaymentMethodDTO> updatePaymentMethod(UUID agencyId, UUID paymentMethodId, AgencyPaymentMethodDTO dto);
    
    /**
     * Deletes a payment method identified by its unique ID.
     *
     * @param agencyId the unique identifier of the agency
     * @param paymentMethodId the unique identifier of the payment method to be deleted
     * @return a Mono that completes when the payment method is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deletePaymentMethod(UUID agencyId, UUID paymentMethodId);
    
    /**
     * Retrieves a payment method by its unique identifier.
     *
     * @param agencyId the unique identifier of the agency
     * @param paymentMethodId the unique identifier of the payment method to retrieve
     * @return a Mono emitting the {@link AgencyPaymentMethodDTO} representing the payment method if found,
     *         or an empty Mono if the payment method does not exist
     */
    Mono<AgencyPaymentMethodDTO> getPaymentMethodById(UUID agencyId, UUID paymentMethodId);
    
    /**
     * Sets a payment method as the primary payment method for an agency.
     *
     * @param agencyId the unique identifier of the agency
     * @param paymentMethodId the unique identifier of the payment method to set as primary
     * @return a Mono that emits the updated AgencyPaymentMethodDTO
     */
    Mono<AgencyPaymentMethodDTO> setPrimaryPaymentMethod(UUID agencyId, UUID paymentMethodId);
}

