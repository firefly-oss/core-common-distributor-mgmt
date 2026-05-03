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

import com.firefly.core.distributor.core.services.DistributorSimulationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorSimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for global simulation access.
 */
@RestController
@RequestMapping("/api/v1/simulations")
@Tag(name = "Simulations", description = "API for global simulation access and management")
@RequiredArgsConstructor
public class SimulationController {

    private final DistributorSimulationService distributorSimulationService;

    @Operation(summary = "Get simulation by application ID", description = "Returns a simulation based on its application ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved simulation",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Simulation not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/application/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorSimulationDTO>> getSimulationByApplicationId(
            @Parameter(description = "ID of the application", required = true)
            @PathVariable UUID applicationId) {
        return distributorSimulationService.getSimulationByApplicationId(applicationId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get simulations by status", description = "Returns all simulations with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved simulations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorSimulationDTO>> getSimulationsByStatus(
            @Parameter(description = "Status of the simulations", required = true)
            @PathVariable String status) {
        return ResponseEntity.ok(distributorSimulationService.getSimulationsByStatus(status));
    }

    @Operation(summary = "Get simulations by country", description = "Returns all simulations for distributors operating in a specific country")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved simulations",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = DistributorSimulationDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/country/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<DistributorSimulationDTO>> getSimulationsByCountry(
            @Parameter(description = "ID of the country", required = true)
            @PathVariable UUID countryId) {
        // This would require a more complex query joining with distributor operations
        // For now, we'll return an empty flux as this would need additional service methods
        return ResponseEntity.ok(Flux.empty());
    }
}
