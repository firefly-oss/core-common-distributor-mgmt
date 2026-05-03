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
import com.firefly.core.distributor.core.services.DistributorOperationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
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
 * REST controller for managing distributor operations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/operations")
@Tag(name = "Distributor Operations", description = "API for managing distributor operational coverage")
@RequiredArgsConstructor
public class DistributorOperationController {

    private final DistributorOperationService distributorOperationService;

    @Operation(summary = "Filter distributor operations", description = "Returns a paginated list of distributor operations based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor operations",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DistributorOperationDTO>>> filterDistributorOperations(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody FilterRequest<DistributorOperationDTO> filterRequest) {
        return distributorOperationService.filterDistributorOperations(filterRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create a new distributor operation", description = "Creates a new operational coverage for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Distributor operation successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor operation data provided", 
                content = @Content),
        @ApiResponse(responseCode = "409", description = "Distributor operation already exists for this location", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorOperationDTO>> createDistributorOperation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorOperationDTO distributorOperationDTO) {
        
        distributorOperationDTO.setDistributorId(distributorId);
        
        return distributorOperationService.createDistributorOperation(distributorOperationDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Operation(summary = "Get distributor operation by ID", description = "Returns a distributor operation based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor operation",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor operation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{operationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorOperationDTO>> getDistributorOperationById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor operation", required = true)
            @PathVariable UUID operationId) {
        return distributorOperationService.getDistributorOperationById(operationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update distributor operation", description = "Updates an existing distributor operation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor operation successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor operation data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor operation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{operationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorOperationDTO>> updateDistributorOperation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor operation to update", required = true)
            @PathVariable UUID operationId,
            @Valid @RequestBody DistributorOperationDTO distributorOperationDTO) {
        
        distributorOperationDTO.setDistributorId(distributorId);
        
        return distributorOperationService.updateDistributorOperation(operationId, distributorOperationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete distributor operation", description = "Deletes a distributor operation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Distributor operation successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Distributor operation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{operationId}")
    public Mono<ResponseEntity<Void>> deleteDistributorOperation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor operation to delete", required = true)
            @PathVariable UUID operationId) {
        return distributorOperationService.deleteDistributorOperation(operationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Get all operations for distributor", description = "Returns all operational coverage areas for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor operations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DistributorOperationDTO>>> getOperationsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        return Mono.just(ResponseEntity.ok(distributorOperationService.getOperationsByDistributorId(distributorId)));
    }

    @Operation(summary = "Get active operations for distributor", description = "Returns all active operational coverage areas for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active distributor operations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DistributorOperationDTO>>> getActiveOperationsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        return Mono.just(ResponseEntity.ok(distributorOperationService.getActiveOperationsByDistributorId(distributorId)));
    }

    @Operation(summary = "Check if distributor can operate in location", description = "Checks if a distributor can operate in a specific country and administrative division")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully checked operation capability",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/can-operate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Boolean>> canDistributorOperateInLocation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the country", required = true)
            @RequestParam UUID countryId,
            @Parameter(description = "ID of the administrative division", required = true)
            @RequestParam UUID administrativeDivisionId) {
        return distributorOperationService.canDistributorOperateInLocation(
                distributorId, countryId, administrativeDivisionId)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Activate distributor operation", description = "Activates a distributor operation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor operation successfully activated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor operation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{operationId}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorOperationDTO>> activateDistributorOperation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor operation to activate", required = true)
            @PathVariable UUID operationId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return distributorOperationService.activateDistributorOperation(operationId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deactivate distributor operation", description = "Deactivates a distributor operation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor operation successfully deactivated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorOperationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor operation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{operationId}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorOperationDTO>> deactivateDistributorOperation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor operation to deactivate", required = true)
            @PathVariable UUID operationId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return distributorOperationService.deactivateDistributorOperation(operationId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
