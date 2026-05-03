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

import com.firefly.core.distributor.models.entities.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing Product entities.
 */
@Repository
public interface ProductRepository extends BaseRepository<Product, UUID> {

    /**
     * Find all products for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of products
     */
    Flux<Product> findByDistributorId(UUID distributorId);

    /**
     * Find all active products for a specific distributor.
     *
     * @param distributorId the ID of the distributor
     * @param isActive the active status
     * @return a Flux of products
     */
    Flux<Product> findByDistributorIdAndIsActive(UUID distributorId, Boolean isActive);

    /**
     * Find all products for a specific distributor and category.
     *
     * @param distributorId the ID of the distributor
     * @param categoryId the ID of the product category
     * @return a Flux of products
     */
    Flux<Product> findByDistributorIdAndCategoryId(UUID distributorId, UUID categoryId);

    /**
     * Find a product by its SKU.
     *
     * @param distributorId the ID of the distributor
     * @param sku the product SKU
     * @return a Mono of the product
     */
    Mono<Product> findByDistributorIdAndSku(UUID distributorId, String sku);
}