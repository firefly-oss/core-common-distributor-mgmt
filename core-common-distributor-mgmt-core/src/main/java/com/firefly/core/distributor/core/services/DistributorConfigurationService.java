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
import com.firefly.core.distributor.interfaces.dtos.DistributorConfigurationDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor configurations.
 */
public interface DistributorConfigurationService {
    /**
     * Filters the configurations based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the configurations
     * @param filterRequest the request object containing filtering criteria for DistributorConfigurationDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of configurations
     */
    Mono<PaginationResponse<DistributorConfigurationDTO>> filterConfigurations(UUID distributorId, FilterRequest<DistributorConfigurationDTO> filterRequest);
    
    /**
     * Creates a new configuration based on the provided information for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor that will own the configuration
     * @param dto the DTO object containing details of the configuration to be created
     * @return a Mono that emits the created DistributorConfigurationDTO object
     */
    Mono<DistributorConfigurationDTO> createConfiguration(UUID distributorId, DistributorConfigurationDTO dto);
    
    /**
     * Updates an existing configuration with updated information.
     *
     * @param distributorId the unique identifier of the distributor that owns the configuration
     * @param configurationId the unique identifier of the configuration to be updated
     * @param dto the data transfer object containing the updated details of the configuration
     * @return a reactive Mono containing the updated DistributorConfigurationDTO
     */
    Mono<DistributorConfigurationDTO> updateConfiguration(UUID distributorId, UUID configurationId, DistributorConfigurationDTO dto);
    
    /**
     * Deletes a configuration identified by its unique ID, validating distributor ownership.
     *
     * @param distributorId the unique identifier of the distributor that owns the configuration
     * @param configurationId the unique identifier of the configuration to be deleted
     * @return a Mono that completes when the configuration is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteConfiguration(UUID distributorId, UUID configurationId);
    
    /**
     * Retrieves a configuration by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor that owns the configuration
     * @param configurationId the unique identifier of the configuration to retrieve
     * @return a Mono emitting the {@link DistributorConfigurationDTO} representing the configuration if found,
     *         or an empty Mono if the configuration does not exist
     */
    Mono<DistributorConfigurationDTO> getConfigurationById(UUID distributorId, UUID configurationId);
}
