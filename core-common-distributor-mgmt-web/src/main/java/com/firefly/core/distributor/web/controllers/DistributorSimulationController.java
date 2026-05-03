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
import com.firefly.core.distributor.core.services.DistributorSimulationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
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
 * REST controller for managing distributor simulations.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/simulations")
@Tag(name = "Distributor Simulations", description = "API for managing distributor simulation tracking")
@RequiredArgsConstructor
public class DistributorSimulationController {

    private final DistributorSimulationService distributorSimulationService;

    @Operation(summary = "Filter distributor simulations", description = "Returns a paginated list of distributor simulations based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor simulations",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DistributorSimulationDTO>>> filterDistributorSimulations(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody FilterRequest<DistributorSimulationDTO> filterRequest) {
        return distributorSimulationService.filterDistributorSimulations(filterRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create a new distributor simulation", description = "Creates a new simulation tracking for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Distributor simulation successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor simulation data provided", 
                content = @Content),
        @ApiResponse(responseCode = "409", description = "Distributor simulation already exists for this application", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> createDistributorSimulation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorSimulationDTO distributorSimulationDTO) {
        
        distributorSimulationDTO.setDistributorId(distributorId);
        
        return distributorSimulationService.createDistributorSimulation(distributorSimulationDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Operation(summary = "Get distributor simulation by ID", description = "Returns a distributor simulation based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor simulation",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{simulationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> getDistributorSimulationById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation", required = true)
            @PathVariable UUID simulationId) {
        return distributorSimulationService.getDistributorSimulationById(simulationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update distributor simulation", description = "Updates an existing distributor simulation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor simulation successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid distributor simulation data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{simulationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> updateDistributorSimulation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation to update", required = true)
            @PathVariable UUID simulationId,
            @Valid @RequestBody DistributorSimulationDTO distributorSimulationDTO) {
        
        distributorSimulationDTO.setDistributorId(distributorId);
        
        return distributorSimulationService.updateDistributorSimulation(simulationId, distributorSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete distributor simulation", description = "Deletes a distributor simulation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Distributor simulation successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{simulationId}")
    public Mono<ResponseEntity<Void>> deleteDistributorSimulation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation to delete", required = true)
            @PathVariable UUID simulationId) {
        return distributorSimulationService.deleteDistributorSimulation(simulationId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Get all simulations for distributor", description = "Returns all simulations tracked by a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor simulations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DistributorSimulationDTO>>> getSimulationsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        return Mono.just(ResponseEntity.ok(distributorSimulationService.getSimulationsByDistributorId(distributorId)));
    }

    @Operation(summary = "Get active simulations for distributor", description = "Returns all active simulations tracked by a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active distributor simulations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DistributorSimulationDTO>>> getActiveSimulationsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        return Mono.just(ResponseEntity.ok(distributorSimulationService.getActiveSimulationsByDistributorId(distributorId)));
    }

    @Operation(summary = "Get simulations by status", description = "Returns simulations with a specific status for a distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved distributor simulations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<DistributorSimulationDTO>>> getSimulationsByDistributorIdAndStatus(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Status of the simulations", required = true)
            @PathVariable String status) {
        return Mono.just(ResponseEntity.ok(distributorSimulationService.getSimulationsByDistributorIdAndStatus(distributorId, status)));
    }

    @Operation(summary = "Update simulation status", description = "Updates the status of a distributor simulation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Simulation status successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{simulationId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> updateSimulationStatus(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation", required = true)
            @PathVariable UUID simulationId,
            @Parameter(description = "New status for the simulation", required = true)
            @RequestParam String status,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return distributorSimulationService.updateSimulationStatus(simulationId, status, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Activate distributor simulation", description = "Activates a distributor simulation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor simulation successfully activated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{simulationId}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> activateDistributorSimulation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation to activate", required = true)
            @PathVariable UUID simulationId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return distributorSimulationService.activateDistributorSimulation(simulationId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deactivate distributor simulation", description = "Deactivates a distributor simulation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distributor simulation successfully deactivated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{simulationId}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> deactivateDistributorSimulation(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the distributor simulation to deactivate", required = true)
            @PathVariable UUID simulationId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return distributorSimulationService.deactivateDistributorSimulation(simulationId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
