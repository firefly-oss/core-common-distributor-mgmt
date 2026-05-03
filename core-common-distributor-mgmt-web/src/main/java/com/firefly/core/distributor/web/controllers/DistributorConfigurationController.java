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
import com.firefly.core.distributor.core.services.DistributorConfigurationService;
import com.firefly.core.distributor.interfaces.dtos.DistributorConfigurationDTO;
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
@RequestMapping("/api/v1/distributors/{distributorId}/configurations")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Configurations",
    description = "API for managing configurations associated with distributors"
)
public class DistributorConfigurationController {

    private final DistributorConfigurationService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter configurations for a distributor",
        description = "Retrieve a paginated list of configurations associated with a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered configurations",
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
    public Mono<ResponseEntity<PaginationResponse<DistributorConfigurationDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for configurations", required = true)
            @Valid @RequestBody FilterRequest<DistributorConfigurationDTO> request) {
        return service.filterConfigurations(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create configuration for a distributor",
        description = "Create a new configuration associated with a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Configuration successfully created",
            content = @Content(schema = @Schema(implementation = DistributorConfigurationDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorConfigurationDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Configuration data to create", required = true)
            @Valid @RequestBody DistributorConfigurationDTO dto) {
        return service.createConfiguration(distributorId, dto)
                .map(config -> ResponseEntity.status(HttpStatus.CREATED).body(config));
    }

    @GetMapping("/{configurationId}")
    @Operation(
        summary = "Get configuration by ID",
        description = "Retrieve a specific configuration associated with a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration found",
            content = @Content(schema = @Schema(implementation = DistributorConfigurationDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorConfigurationDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the configuration", required = true)
            @PathVariable UUID configurationId) {
        return service.getConfigurationById(distributorId, configurationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{configurationId}")
    @Operation(
        summary = "Update configuration",
        description = "Update an existing configuration associated with a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorConfigurationDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorConfigurationDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the configuration", required = true)
            @PathVariable UUID configurationId,
            @Parameter(description = "Updated configuration data", required = true)
            @Valid @RequestBody DistributorConfigurationDTO dto) {
        return service.updateConfiguration(distributorId, configurationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{configurationId}")
    @Operation(
        summary = "Delete configuration",
        description = "Delete a configuration associated with a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Configuration successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the configuration", required = true)
            @PathVariable UUID configurationId) {
        return service.deleteConfiguration(distributorId, configurationId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
