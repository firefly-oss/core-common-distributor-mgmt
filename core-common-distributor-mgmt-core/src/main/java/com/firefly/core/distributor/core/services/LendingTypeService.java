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

import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing lending types.
 */
public interface LendingTypeService {

    /**
     * Get all lending types.
     *
     * @return A Flux of all lending types
     */
    Flux<LendingTypeDTO> getAllLendingTypes();

    /**
     * Get all active lending types.
     *
     * @return A Flux of active lending types
     */
    Flux<LendingTypeDTO> getActiveLendingTypes();

    /**
     * Get a lending type by its ID.
     *
     * @param id The ID of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingTypeDTO> getLendingTypeById(UUID id);

    /**
     * Get a lending type by its code.
     *
     * @param code The code of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingTypeDTO> getLendingTypeByCode(String code);

    /**
     * Create a new lending type.
     *
     * @param lendingTypeDTO The lending type to create
     * @return A Mono containing the created lending type
     */
    Mono<LendingTypeDTO> createLendingType(LendingTypeDTO lendingTypeDTO);

    /**
     * Update an existing lending type.
     *
     * @param id The ID of the lending type to update
     * @param lendingTypeDTO The updated lending type data
     * @return A Mono containing the updated lending type
     */
    Mono<LendingTypeDTO> updateLendingType(UUID id, LendingTypeDTO lendingTypeDTO);

    /**
     * Delete a lending type by its ID.
     *
     * @param id The ID of the lending type to delete
     * @return A Mono that completes when the lending type is deleted
     */
    Mono<Void> deleteLendingType(UUID id);
}