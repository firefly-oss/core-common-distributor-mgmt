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
import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Filter products based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a paginated response of products
     */
    Mono<PaginationResponse<ProductDTO>> filterProducts(FilterRequest<ProductDTO> filterRequest);

    /**
     * Create a new product.
     *
     * @param productDTO the product data
     * @return the created product
     */
    Mono<ProductDTO> createProduct(ProductDTO productDTO);

    /**
     * Update an existing product.
     *
     * @param productId the ID of the product to update
     * @param productDTO the updated product data
     * @return the updated product
     */
    Mono<ProductDTO> updateProduct(UUID productId, ProductDTO productDTO);

    /**
     * Delete a product.
     *
     * @param productId the ID of the product to delete
     * @return void
     */
    Mono<Void> deleteProduct(UUID productId);

    /**
     * Get a product by ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the product
     */
    Mono<ProductDTO> getProductById(UUID productId);

    /**
     * Get all products for a distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a flux of products
     */
    Flux<ProductDTO> getProductsByDistributorId(UUID distributorId);

    /**
     * Get all active products for a distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a flux of active products
     */
    Flux<ProductDTO> getActiveProductsByDistributorId(UUID distributorId);

    /**
     * Get all products for a distributor by category.
     *
     * @param distributorId the ID of the distributor
     * @param category the product category
     * @return a flux of products
     */
    Flux<ProductDTO> getProductsByDistributorIdAndCategory(UUID distributorId, ProductCategoryDTO category);
}