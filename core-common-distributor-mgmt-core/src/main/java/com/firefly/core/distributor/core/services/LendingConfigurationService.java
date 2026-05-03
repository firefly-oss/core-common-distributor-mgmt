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
import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing lending configurations.
 */
public interface LendingConfigurationService {

    /**
     * Filter lending configurations based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a paginated response of lending configurations
     */
    Mono<PaginationResponse<LendingConfigurationDTO>> filterLendingConfigurations(FilterRequest<LendingConfigurationDTO> filterRequest);

    /**
     * Create a new lending configuration.
     *
     * @param lendingConfigurationDTO the lending configuration data
     * @return the created lending configuration
     */
    Mono<LendingConfigurationDTO> createLendingConfiguration(LendingConfigurationDTO lendingConfigurationDTO);

    /**
     * Update an existing lending configuration.
     *
     * @param lendingConfigurationId the ID of the lending configuration to update
     * @param lendingConfigurationDTO the updated lending configuration data
     * @return the updated lending configuration
     */
    Mono<LendingConfigurationDTO> updateLendingConfiguration(UUID lendingConfigurationId, LendingConfigurationDTO lendingConfigurationDTO);

    /**
     * Delete a lending configuration.
     *
     * @param lendingConfigurationId the ID of the lending configuration to delete
     * @return void
     */
    Mono<Void> deleteLendingConfiguration(UUID lendingConfigurationId);

    /**
     * Get a lending configuration by ID.
     *
     * @param lendingConfigurationId the ID of the lending configuration to retrieve
     * @return the lending configuration
     */
    Mono<LendingConfigurationDTO> getLendingConfigurationById(UUID lendingConfigurationId);

    /**
     * Get all lending configurations for a product.
     *
     * @param productId the ID of the product
     * @return a flux of lending configurations
     */
    Flux<LendingConfigurationDTO> getLendingConfigurationsByProductId(UUID productId);

    /**
     * Get all active lending configurations for a product.
     *
     * @param productId the ID of the product
     * @return a flux of active lending configurations
     */
    Flux<LendingConfigurationDTO> getActiveLendingConfigurationsByProductId(UUID productId);

    /**
     * Get all lending configurations for a product by lending type.
     *
     * @param productId the ID of the product
     * @param lendingType the lending type
     * @return a flux of lending configurations
     */
    Flux<LendingConfigurationDTO> getLendingConfigurationsByProductIdAndLendingType(UUID productId, LendingTypeDTO lendingType);

    /**
     * Get the default lending configuration for a product.
     *
     * @param productId the ID of the product
     * @return the default lending configuration
     */
    Mono<LendingConfigurationDTO> getDefaultLendingConfigurationByProductId(UUID productId);

    /**
     * Get all lending configurations for products belonging to a distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a flux of lending configurations
     */
    Flux<LendingConfigurationDTO> getLendingConfigurationsByDistributorId(UUID distributorId);
}