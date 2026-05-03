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
import com.firefly.core.distributor.core.services.ConfigurationScopeService;
import com.firefly.core.distributor.interfaces.dtos.ConfigurationScopeDTO;
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
@RequestMapping("/api/v1/configuration-scopes")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Configuration Scopes",
    description = "API for managing configuration scopes"
)
public class ConfigurationScopeController {

    private final ConfigurationScopeService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter configuration scopes",
        description = "Retrieve a paginated list of configuration scopes based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered configuration scopes",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter request",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<ConfigurationScopeDTO>>> filter(
            @Parameter(description = "Filter criteria for configuration scopes", required = true)
            @Valid @RequestBody FilterRequest<ConfigurationScopeDTO> request) {
        return service.filterConfigurationScopes(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create configuration scope",
        description = "Create a new configuration scope"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Configuration scope successfully created",
            content = @Content(schema = @Schema(implementation = ConfigurationScopeDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration scope data",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationScopeDTO>> create(
            @Parameter(description = "Configuration scope data to create", required = true)
            @Valid @RequestBody ConfigurationScopeDTO dto) {
        return service.createConfigurationScope(dto)
                .map(scope -> ResponseEntity.status(HttpStatus.CREATED).body(scope));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get configuration scope by ID",
        description = "Retrieve a specific configuration scope by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration scope found",
            content = @Content(schema = @Schema(implementation = ConfigurationScopeDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration scope not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationScopeDTO>> getById(
            @Parameter(description = "Unique identifier of the configuration scope", required = true)
            @PathVariable UUID id) {
        return service.getConfigurationScopeById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update configuration scope",
        description = "Update an existing configuration scope"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration scope successfully updated",
            content = @Content(schema = @Schema(implementation = ConfigurationScopeDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration scope data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration scope not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationScopeDTO>> update(
            @Parameter(description = "Unique identifier of the configuration scope", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated configuration scope data", required = true)
            @Valid @RequestBody ConfigurationScopeDTO dto) {
        return service.updateConfigurationScope(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete configuration scope",
        description = "Delete a configuration scope from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Configuration scope successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration scope not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the configuration scope", required = true)
            @PathVariable UUID id) {
        return service.deleteConfigurationScope(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
