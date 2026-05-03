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
import com.firefly.core.distributor.core.services.ConfigurationDataTypeService;
import com.firefly.core.distributor.interfaces.dtos.ConfigurationDataTypeDTO;
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
@RequestMapping("/api/v1/configuration-data-types")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Configuration Data Types",
    description = "API for managing configuration data types"
)
public class ConfigurationDataTypeController {

    private final ConfigurationDataTypeService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter configuration data types",
        description = "Retrieve a paginated list of configuration data types based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered configuration data types",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter request",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<ConfigurationDataTypeDTO>>> filter(
            @Parameter(description = "Filter criteria for configuration data types", required = true)
            @Valid @RequestBody FilterRequest<ConfigurationDataTypeDTO> request) {
        return service.filterConfigurationDataTypes(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create configuration data type",
        description = "Create a new configuration data type"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Configuration data type successfully created",
            content = @Content(schema = @Schema(implementation = ConfigurationDataTypeDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration data type data",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationDataTypeDTO>> create(
            @Parameter(description = "Configuration data type data to create", required = true)
            @Valid @RequestBody ConfigurationDataTypeDTO dto) {
        return service.createConfigurationDataType(dto)
                .map(type -> ResponseEntity.status(HttpStatus.CREATED).body(type));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get configuration data type by ID",
        description = "Retrieve a specific configuration data type by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration data type found",
            content = @Content(schema = @Schema(implementation = ConfigurationDataTypeDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration data type not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationDataTypeDTO>> getById(
            @Parameter(description = "Unique identifier of the configuration data type", required = true)
            @PathVariable UUID id) {
        return service.getConfigurationDataTypeById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update configuration data type",
        description = "Update an existing configuration data type"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Configuration data type successfully updated",
            content = @Content(schema = @Schema(implementation = ConfigurationDataTypeDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid configuration data type data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration data type not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<ConfigurationDataTypeDTO>> update(
            @Parameter(description = "Unique identifier of the configuration data type", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated configuration data type data", required = true)
            @Valid @RequestBody ConfigurationDataTypeDTO dto) {
        return service.updateConfigurationDataType(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete configuration data type",
        description = "Delete a configuration data type from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Configuration data type successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Configuration data type not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the configuration data type", required = true)
            @PathVariable UUID id) {
        return service.deleteConfigurationDataType(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
