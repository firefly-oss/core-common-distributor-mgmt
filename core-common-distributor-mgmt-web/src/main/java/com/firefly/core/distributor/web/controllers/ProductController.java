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


package com.firefly.core.distributor.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.ProductService;
import com.firefly.core.distributor.core.services.ProductCategoryService;
import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/products")
@Tag(name = "Product", description = "API for managing distributor products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * POST /api/v1/distributors/{distributorId}/products/filter : Filter products
     *
     * @param distributorId the ID of the distributor
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @Operation(summary = "Filter products", description = "Returns a paginated list of products based on filter criteria for a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ProductDTO>>> filterProducts(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody FilterRequest<ProductDTO> filterRequest) {
        
        // Ensure we're only filtering products for the specified distributor
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new ProductDTO());
        }
        filterRequest.getFilters().setDistributorId(distributorId);
        
        return productService.filterProducts(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/distributors/{distributorId}/products : Create a new product
     *
     * @param distributorId the ID of the distributor
     * @param productDTO the product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product
     */
    @Operation(summary = "Create a new product", description = "Creates a new product for a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> createProduct(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody ProductDTO productDTO) {
        
        productDTO.setDistributorId(distributorId);
        
        return productService.createProduct(productDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/distributors/{distributorId}/products/{productId} : Update an existing product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to update
     * @param productDTO the product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product
     */
    @Operation(summary = "Update an existing product", description = "Updates an existing product for a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> updateProduct(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable UUID productId,
            @Valid @RequestBody ProductDTO productDTO) {
        
        productDTO.setDistributorId(distributorId);
        
        return productService.updateProduct(productId, productDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/distributors/{distributorId}/products/{productId} : Delete a product
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete a product", description = "Deletes a product based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteProduct(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable UUID productId) {
        
        return productService.deleteProduct(productId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/{productId} : Get a product by ID
     *
     * @param distributorId the ID of the distributor
     * @param productId the ID of the product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product
     */
    @Operation(summary = "Get product by ID", description = "Returns a product based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> getProductById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable UUID productId) {
        
        return productService.getProductById(productId)
                .map(ResponseEntity::ok);
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products : Get all products for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of products
     */
    @Operation(summary = "Get all products for a distributor",
            description = "Returns all products associated with a specific distributor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Distributor not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ProductDTO>>> getProductsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        
        return Mono.just(ResponseEntity.ok(productService.getProductsByDistributorId(distributorId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/active : Get all active products for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of active products
     */
    @Operation(summary = "Get all active products for a distributor", description = "Returns all active products associated with a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active products",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ProductDTO>>> getActiveProductsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        
        return Mono.just(ResponseEntity.ok(productService.getActiveProductsByDistributorId(distributorId)));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/products/category/{categoryId} : Get all products for a distributor by category
     *
     * @param distributorId the ID of the distributor
     * @param categoryId the ID of the product category
     * @return the ResponseEntity with status 200 (OK) and with body the list of products
     */
    @Operation(summary = "Get products by category", description = "Returns all products for a specific distributor filtered by category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor or category not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ProductDTO>>> getProductsByDistributorIdAndCategory(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the product category", required = true)
            @PathVariable UUID categoryId) {
        
        return productCategoryService.getProductCategoryById(categoryId)
                .map(category -> ResponseEntity.ok(productService.getProductsByDistributorIdAndCategory(distributorId, category)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}