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

import com.firefly.core.distributor.models.entities.LendingType;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing LendingType entities.
 */
@Repository
public interface LendingTypeRepository extends BaseRepository<LendingType, UUID> {

    /**
     * Find a lending type by its code.
     *
     * @param code The unique code of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingType> findByCode(String code);

    /**
     * Find all active lending types.
     *
     * @return A Flux of active lending types
     */
    Flux<LendingType> findByIsActiveTrue();

    /**
     * Find a lending type by its name.
     *
     * @param name The name of the lending type
     * @return A Mono containing the lending type if found
     */
    Mono<LendingType> findByName(String name);
}