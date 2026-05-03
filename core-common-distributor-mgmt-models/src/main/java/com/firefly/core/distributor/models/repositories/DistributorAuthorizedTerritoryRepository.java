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

import com.firefly.core.distributor.models.entities.DistributorAuthorizedTerritory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository interface for DistributorAuthorizedTerritory entity.
 * Provides data access operations for distributor authorized territories.
 */
@Repository
public interface DistributorAuthorizedTerritoryRepository extends BaseRepository<DistributorAuthorizedTerritory, UUID> {

    /**
     * Find all authorized territories for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @return Flux of authorized territories
     */
    Flux<DistributorAuthorizedTerritory> findByDistributorId(UUID distributorId);

    /**
     * Find all active authorized territories for a specific distributor.
     *
     * @param distributorId the distributor ID
     * @param isActive the active status
     * @return Flux of authorized territories
     */
    Flux<DistributorAuthorizedTerritory> findByDistributorIdAndIsActive(UUID distributorId, Boolean isActive);

    /**
     * Find authorized territories for a specific distributor and country.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @return Flux of authorized territories
     */
    Flux<DistributorAuthorizedTerritory> findByDistributorIdAndCountryId(UUID distributorId, UUID countryId);

    /**
     * Find active authorized territories for a specific distributor and country.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param isActive the active status
     * @return Flux of authorized territories
     */
    Flux<DistributorAuthorizedTerritory> findByDistributorIdAndCountryIdAndIsActive(
            UUID distributorId, UUID countryId, Boolean isActive);

    /**
     * Check if a distributor is authorized for a specific country.
     *
     * @param distributorId the distributor ID
     * @param countryId the country ID
     * @param isActive the active status
     * @return Mono of Boolean indicating if authorized
     */
    Mono<Boolean> existsByDistributorIdAndCountryIdAndIsActive(UUID distributorId, UUID countryId, Boolean isActive);

    /**
     * Find authorized territories by country.
     *
     * @param countryId the country ID
     * @return Flux of authorized territories
     */
    Flux<DistributorAuthorizedTerritory> findByCountryId(UUID countryId);
}

