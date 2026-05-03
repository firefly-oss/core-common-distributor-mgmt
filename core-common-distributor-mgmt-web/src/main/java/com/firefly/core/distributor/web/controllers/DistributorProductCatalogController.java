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
import com.firefly.core.distributor.core.services.DistributorProductCatalogService;
import com.firefly.core.distributor.interfaces.dtos.DistributorProductCatalogDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/product-catalog")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Product Catalog",
    description = "API for managing product catalog associated with distributors"
)
public class DistributorProductCatalogController {

    private final DistributorProductCatalogService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter product catalog for a distributor",
        description = "Retrieve a paginated list of product catalog items associated with a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered product catalog",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter request",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<DistributorProductCatalogDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for product catalog", required = true)
            @Valid @RequestBody FilterRequest<DistributorProductCatalogDTO> request) {
        return service.filterProductCatalogs(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create product catalog item for a distributor",
        description = "Create a new product catalog item associated with a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Product catalog item successfully created",
            content = @Content(schema = @Schema(implementation = DistributorProductCatalogDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid product catalog data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorProductCatalogDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Product catalog data to create", required = true)
            @Valid @RequestBody DistributorProductCatalogDTO dto) {
        return service.createProductCatalog(distributorId, dto)
                .map(catalog -> ResponseEntity.status(HttpStatus.CREATED).body(catalog));
    }

    @GetMapping("/{catalogId}")
    @Operation(
        summary = "Get product catalog item by ID",
        description = "Retrieve a specific product catalog item associated with a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Product catalog item found",
            content = @Content(schema = @Schema(implementation = DistributorProductCatalogDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product catalog item or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorProductCatalogDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the product catalog item", required = true)
            @PathVariable UUID catalogId) {
        return service.getProductCatalogById(distributorId, catalogId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{catalogId}")
    @Operation(
        summary = "Update product catalog item",
        description = "Update an existing product catalog item associated with a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Product catalog item successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorProductCatalogDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid product catalog data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product catalog item or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorProductCatalogDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the product catalog item", required = true)
            @PathVariable UUID catalogId,
            @Parameter(description = "Updated product catalog data", required = true)
            @Valid @RequestBody DistributorProductCatalogDTO dto) {
        return service.updateProductCatalog(distributorId, catalogId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{catalogId}")
    @Operation(
        summary = "Delete product catalog item",
        description = "Delete a product catalog item associated with a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Product catalog item successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Product catalog item or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the product catalog item", required = true)
            @PathVariable UUID catalogId) {
        return service.deleteProductCatalog(distributorId, catalogId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
