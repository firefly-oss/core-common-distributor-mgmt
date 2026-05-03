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
import com.firefly.core.distributor.core.services.DistributorAgencyService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgencyDTO;
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
@RequestMapping("/api/v1/distributors/{distributorId}/agencies")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Agencies",
    description = "API for managing physical agencies associated with distributors"
)
public class DistributorAgencyController {

    private final DistributorAgencyService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter agencies for a distributor",
        description = "Retrieve a paginated list of agencies associated with a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered agencies",
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
    public Mono<ResponseEntity<PaginationResponse<DistributorAgencyDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for agencies", required = true)
            @Valid @RequestBody FilterRequest<DistributorAgencyDTO> request) {
        return service.filterAgencies(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create agency for a distributor",
        description = "Create a new physical agency associated with a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Agency successfully created",
            content = @Content(schema = @Schema(implementation = DistributorAgencyDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agency data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgencyDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Agency data to create", required = true)
            @Valid @RequestBody DistributorAgencyDTO dto) {
        return service.createAgency(distributorId, dto)
                .map(agency -> ResponseEntity.status(HttpStatus.CREATED).body(agency));
    }

    @GetMapping("/{agencyId}")
    @Operation(
        summary = "Get agency by ID",
        description = "Retrieve a specific agency associated with a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agency found",
            content = @Content(schema = @Schema(implementation = DistributorAgencyDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agency or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgencyDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId) {
        return service.getAgencyById(distributorId, agencyId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{agencyId}")
    @Operation(
        summary = "Update agency",
        description = "Update an existing agency associated with a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agency successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorAgencyDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agency data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agency or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgencyDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Updated agency data", required = true)
            @Valid @RequestBody DistributorAgencyDTO dto) {
        return service.updateAgency(distributorId, agencyId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{agencyId}")
    @Operation(
        summary = "Delete agency",
        description = "Delete an agency associated with a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Agency successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agency or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId) {
        return service.deleteAgency(distributorId, agencyId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
