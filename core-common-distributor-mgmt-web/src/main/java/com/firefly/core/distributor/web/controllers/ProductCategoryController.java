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

import com.firefly.core.distributor.core.services.ProductCategoryService;
import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing product categories.
 */
@RestController
@RequestMapping("/api/v1/product-categories")
@Tag(name = "Product Category", description = "API for managing product categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    /**
     * GET /api/v1/product-categories : Get all product categories
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of product categories
     */
    @Operation(summary = "Get all product categories", description = "Returns all product categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product categories",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<ProductCategoryDTO>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAllProductCategories());
    }

    /**
     * GET /api/v1/product-categories/active : Get all active product categories
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of active product categories
     */
    @Operation(summary = "Get all active product categories", description = "Returns all active product categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active product categories",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<ProductCategoryDTO>> getActiveProductCategories() {
        return ResponseEntity.ok(productCategoryService.getActiveProductCategories());
    }

    /**
     * GET /api/v1/product-categories/{id} : Get a product category by ID
     *
     * @param id the ID of the product category to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product category
     */
    @Operation(summary = "Get product category by ID", description = "Returns a product category based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product category",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product category not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductCategoryDTO> getProductCategoryById(
            @Parameter(description = "ID of the product category to retrieve", required = true)
            @PathVariable UUID id) {
        return productCategoryService.getProductCategoryById(id);
    }

    /**
     * GET /api/v1/product-categories/code/{code} : Get a product category by code
     *
     * @param code the code of the product category to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the product category
     */
    @Operation(summary = "Get product category by code", description = "Returns a product category based on its code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product category",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product category not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductCategoryDTO> getProductCategoryByCode(
            @Parameter(description = "Code of the product category to retrieve", required = true)
            @PathVariable String code) {
        return productCategoryService.getProductCategoryByCode(code);
    }

    /**
     * POST /api/v1/product-categories : Create a new product category
     *
     * @param productCategoryDTO the product category to create
     * @return the ResponseEntity with status 201 (Created) and with body the new product category
     */
    @Operation(summary = "Create a new product category", description = "Creates a new product category with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product category successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product category data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductCategoryDTO> createProductCategory(
            @Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
        return productCategoryService.createProductCategory(productCategoryDTO);
    }

    /**
     * PUT /api/v1/product-categories/{id} : Update an existing product category
     *
     * @param id the ID of the product category to update
     * @param productCategoryDTO the product category to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated product category
     */
    @Operation(summary = "Update product category", description = "Updates an existing product category with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product category successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductCategoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product category data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product category not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductCategoryDTO> updateProductCategory(
            @Parameter(description = "ID of the product category to update", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProductCategoryDTO productCategoryDTO) {
        return productCategoryService.updateProductCategory(id, productCategoryDTO);
    }

    /**
     * DELETE /api/v1/product-categories/{id} : Delete a product category
     *
     * @param id the ID of the product category to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete product category", description = "Deletes a product category based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product category successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product category not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductCategory(
            @Parameter(description = "ID of the product category to delete", required = true)
            @PathVariable UUID id) {
        return productCategoryService.deleteProductCategory(id);
    }
}