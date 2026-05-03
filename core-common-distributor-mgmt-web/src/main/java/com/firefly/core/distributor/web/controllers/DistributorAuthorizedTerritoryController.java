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
import com.firefly.core.distributor.core.services.DistributorAuthorizedTerritoryService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAuthorizedTerritoryDTO;
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
@RequestMapping("/api/v1/distributors/{distributorId}/authorized-territories")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Authorized Territories",
    description = "API for managing geographic territories where distributors are authorized to operate"
)
public class DistributorAuthorizedTerritoryController {

    private final DistributorAuthorizedTerritoryService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter authorized territories for a distributor",
        description = "Retrieve a paginated list of authorized territories for a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered territories",
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
    public Mono<ResponseEntity<PaginationResponse<DistributorAuthorizedTerritoryDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for authorized territories", required = true)
            @Valid @RequestBody FilterRequest<DistributorAuthorizedTerritoryDTO> request) {
        return service.filterTerritories(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create authorized territory for a distributor",
        description = "Create a new authorized territory for a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Authorized territory successfully created",
            content = @Content(schema = @Schema(implementation = DistributorAuthorizedTerritoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid territory data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAuthorizedTerritoryDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Territory data to create", required = true)
            @Valid @RequestBody DistributorAuthorizedTerritoryDTO dto) {
        return service.createTerritory(distributorId, dto)
                .map(territory -> ResponseEntity.status(HttpStatus.CREATED).body(territory));
    }

    @GetMapping("/{territoryId}")
    @Operation(
        summary = "Get authorized territory by ID",
        description = "Retrieve a specific authorized territory for a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Territory found",
            content = @Content(schema = @Schema(implementation = DistributorAuthorizedTerritoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Territory or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAuthorizedTerritoryDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the territory", required = true)
            @PathVariable UUID territoryId) {
        return service.getTerritoryById(distributorId, territoryId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{territoryId}")
    @Operation(
        summary = "Update authorized territory",
        description = "Update an existing authorized territory for a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Territory successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorAuthorizedTerritoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid territory data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Territory or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAuthorizedTerritoryDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the territory", required = true)
            @PathVariable UUID territoryId,
            @Parameter(description = "Updated territory data", required = true)
            @Valid @RequestBody DistributorAuthorizedTerritoryDTO dto) {
        return service.updateTerritory(distributorId, territoryId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{territoryId}")
    @Operation(
        summary = "Delete authorized territory",
        description = "Delete an authorized territory for a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Territory successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Territory or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the territory", required = true)
            @PathVariable UUID territoryId) {
        return service.deleteTerritory(distributorId, territoryId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

