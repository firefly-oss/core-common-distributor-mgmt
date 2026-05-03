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

import com.firefly.core.distributor.models.entities.Distributor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for managing {@link Distributor} entities.
 * Extends {@link BaseRepository} to inherit common CRUD operations.
 */
@Repository
public interface DistributorRepository extends BaseRepository<Distributor, UUID> {
    
    /**
     * Find a distributor by its external code.
     *
     * @param externalCode the external code to search for
     * @return a Mono containing the distributor if found, or an empty Mono if not found
     */
    Mono<Distributor> findByExternalCode(String externalCode);
    
    /**
     * Find all active distributors.
     *
     * @return a Flux of active distributors
     */
    Flux<Distributor> findByIsActiveTrue();
    
    /**
     * Find all test distributors.
     *
     * @return a Flux of test distributors
     */
    Flux<Distributor> findByIsTestDistributorTrue();
    
    /**
     * Find distributors by country ID.
     *
     * @param countryId the UUID of the country from master data
     * @return a Flux of distributors in the specified country
     */
    Flux<Distributor> findByCountryId(UUID countryId);
}