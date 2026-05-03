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

import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing product categories.
 */
public interface ProductCategoryService {

    /**
     * Get all product categories.
     *
     * @return A Flux of all product categories
     */
    Flux<ProductCategoryDTO> getAllProductCategories();

    /**
     * Get all active product categories.
     *
     * @return A Flux of active product categories
     */
    Flux<ProductCategoryDTO> getActiveProductCategories();

    /**
     * Get a product category by its ID.
     *
     * @param id The ID of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategoryDTO> getProductCategoryById(UUID id);

    /**
     * Get a product category by its code.
     *
     * @param code The code of the product category
     * @return A Mono containing the product category if found
     */
    Mono<ProductCategoryDTO> getProductCategoryByCode(String code);

    /**
     * Create a new product category.
     *
     * @param productCategoryDTO The product category to create
     * @return A Mono containing the created product category
     */
    Mono<ProductCategoryDTO> createProductCategory(ProductCategoryDTO productCategoryDTO);

    /**
     * Update an existing product category.
     *
     * @param id The ID of the product category to update
     * @param productCategoryDTO The updated product category data
     * @return A Mono containing the updated product category
     */
    Mono<ProductCategoryDTO> updateProductCategory(UUID id, ProductCategoryDTO productCategoryDTO);

    /**
     * Delete a product category by its ID.
     *
     * @param id The ID of the product category to delete
     * @return A Mono that completes when the product category is deleted
     */
    Mono<Void> deleteProductCategory(UUID id);
}