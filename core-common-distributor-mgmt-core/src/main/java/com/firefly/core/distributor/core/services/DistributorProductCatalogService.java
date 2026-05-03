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
import com.firefly.core.distributor.interfaces.dtos.DistributorProductCatalogDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor product catalogs.
 */
public interface DistributorProductCatalogService {
    /**
     * Filters product catalogs based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the product catalogs
     * @param filterRequest the request object containing filtering criteria
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list
     */
    Mono<PaginationResponse<DistributorProductCatalogDTO>> filterProductCatalogs(UUID distributorId, FilterRequest<DistributorProductCatalogDTO> filterRequest);
    
    /**
     * Creates a new product catalog for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor
     * @param dto the DTO object containing details
     * @return a Mono that emits the created DistributorProductCatalogDTO object
     */
    Mono<DistributorProductCatalogDTO> createProductCatalog(UUID distributorId, DistributorProductCatalogDTO dto);
    
    /**
     * Updates an existing product catalog.
     *
     * @param distributorId the unique identifier of the distributor
     * @param catalogId the unique identifier of the product catalog
     * @param dto the data transfer object containing updated details
     * @return a reactive Mono containing the updated DistributorProductCatalogDTO
     */
    Mono<DistributorProductCatalogDTO> updateProductCatalog(UUID distributorId, UUID catalogId, DistributorProductCatalogDTO dto);
    
    /**
     * Deletes a product catalog by its unique ID.
     *
     * @param distributorId the unique identifier of the distributor
     * @param catalogId the unique identifier of the product catalog
     * @return a Mono that completes when successfully deleted
     */
    Mono<Void> deleteProductCatalog(UUID distributorId, UUID catalogId);
    
    /**
     * Retrieves a product catalog by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor
     * @param catalogId the unique identifier of the product catalog
     * @return a Mono emitting the DistributorProductCatalogDTO if found
     */
    Mono<DistributorProductCatalogDTO> getProductCatalogById(UUID distributorId, UUID catalogId);
}
