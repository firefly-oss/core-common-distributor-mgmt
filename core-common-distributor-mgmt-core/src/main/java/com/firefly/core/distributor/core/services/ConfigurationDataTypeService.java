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
import com.firefly.core.distributor.interfaces.dtos.ConfigurationDataTypeDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing configuration data types.
 */
public interface ConfigurationDataTypeService {
    /**
     * Filters configuration data types based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list
     */
    Mono<PaginationResponse<ConfigurationDataTypeDTO>> filterConfigurationDataTypes(FilterRequest<ConfigurationDataTypeDTO> filterRequest);
    
    /**
     * Creates a new configuration data type.
     *
     * @param dto the DTO object containing details
     * @return a Mono that emits the created ConfigurationDataTypeDTO object
     */
    Mono<ConfigurationDataTypeDTO> createConfigurationDataType(ConfigurationDataTypeDTO dto);
    
    /**
     * Updates an existing configuration data type.
     *
     * @param id the unique identifier
     * @param dto the data transfer object containing updated details
     * @return a reactive Mono containing the updated ConfigurationDataTypeDTO
     */
    Mono<ConfigurationDataTypeDTO> updateConfigurationDataType(UUID id, ConfigurationDataTypeDTO dto);
    
    /**
     * Deletes a configuration data type by its unique ID.
     *
     * @param id the unique identifier
     * @return a Mono that completes when successfully deleted
     */
    Mono<Void> deleteConfigurationDataType(UUID id);
    
    /**
     * Retrieves a configuration data type by its unique identifier.
     *
     * @param id the unique identifier
     * @return a Mono emitting the ConfigurationDataTypeDTO if found
     */
    Mono<ConfigurationDataTypeDTO> getConfigurationDataTypeById(UUID id);
}
