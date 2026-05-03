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

import com.firefly.core.distributor.core.services.LendingTypeService;
import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
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
 * REST controller for managing lending types.
 */
@RestController
@RequestMapping("/api/v1/lending-types")
@Tag(name = "Lending Type", description = "API for managing lending types")
@RequiredArgsConstructor
public class LendingTypeController {

    private final LendingTypeService lendingTypeService;

    /**
     * GET /api/v1/lending-types : Get all lending types
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending types
     */
    @Operation(summary = "Get all lending types", description = "Returns all lending types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending types",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<LendingTypeDTO>> getAllLendingTypes() {
        return ResponseEntity.ok(lendingTypeService.getAllLendingTypes());
    }

    /**
     * GET /api/v1/lending-types/active : Get all active lending types
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of active lending types
     */
    @Operation(summary = "Get all active lending types", description = "Returns all active lending types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active lending types",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<LendingTypeDTO>> getActiveLendingTypes() {
        return ResponseEntity.ok(lendingTypeService.getActiveLendingTypes());
    }

    /**
     * GET /api/v1/lending-types/{id} : Get a lending type by ID
     *
     * @param id the ID of the lending type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lending type
     */
    @Operation(summary = "Get lending type by ID", description = "Returns a lending type based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending type",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Lending type not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingTypeDTO>> getLendingTypeById(
            @Parameter(description = "ID of the lending type to retrieve", required = true)
            @PathVariable UUID id) {
        return lendingTypeService.getLendingTypeById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/lending-types/code/{code} : Get a lending type by code
     *
     * @param code the code of the lending type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lending type
     */
    @Operation(summary = "Get lending type by code", description = "Returns a lending type based on its code")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending type",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Lending type not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingTypeDTO>> getLendingTypeByCode(
            @Parameter(description = "Code of the lending type to retrieve", required = true)
            @PathVariable String code) {
        return lendingTypeService.getLendingTypeByCode(code)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/lending-types : Create a new lending type
     *
     * @param lendingTypeDTO the lending type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lending type
     */
    @Operation(summary = "Create a new lending type", description = "Creates a new lending type with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lending type successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending type data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingTypeDTO>> createLendingType(
            @Valid @RequestBody LendingTypeDTO lendingTypeDTO) {
        return lendingTypeService.createLendingType(lendingTypeDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/lending-types/{id} : Update an existing lending type
     *
     * @param id the ID of the lending type to update
     * @param lendingTypeDTO the lending type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lending type
     */
    @Operation(summary = "Update lending type", description = "Updates an existing lending type with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lending type successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingTypeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending type data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Lending type not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingTypeDTO>> updateLendingType(
            @Parameter(description = "ID of the lending type to update", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LendingTypeDTO lendingTypeDTO) {
        return lendingTypeService.updateLendingType(id, lendingTypeDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/lending-types/{id} : Delete a lending type
     *
     * @param id the ID of the lending type to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete lending type", description = "Deletes a lending type based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lending type successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Lending type not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLendingType(
            @Parameter(description = "ID of the lending type to delete", required = true)
            @PathVariable UUID id) {
        return lendingTypeService.deleteLendingType(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}