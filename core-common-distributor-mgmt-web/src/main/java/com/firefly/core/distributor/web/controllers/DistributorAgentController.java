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
import com.firefly.core.distributor.core.services.DistributorAgentService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentDTO;
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
@RequestMapping("/api/v1/distributors/{distributorId}/agents")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Agents",
    description = "API for managing agents associated with distributors"
)
public class DistributorAgentController {

    private final DistributorAgentService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter agents for a distributor",
        description = "Retrieve a paginated list of agents associated with a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered agents",
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
    public Mono<ResponseEntity<PaginationResponse<DistributorAgentDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for agents", required = true)
            @Valid @RequestBody FilterRequest<DistributorAgentDTO> request) {
        return service.filterAgents(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create agent for a distributor",
        description = "Create a new agent associated with a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Agent successfully created",
            content = @Content(schema = @Schema(implementation = DistributorAgentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agent data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgentDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Agent data to create", required = true)
            @Valid @RequestBody DistributorAgentDTO dto) {
        return service.createAgent(distributorId, dto)
                .map(agent -> ResponseEntity.status(HttpStatus.CREATED).body(agent));
    }

    @GetMapping("/{agentId}")
    @Operation(
        summary = "Get agent by ID",
        description = "Retrieve a specific agent associated with a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agent found",
            content = @Content(schema = @Schema(implementation = DistributorAgentDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgentDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agent", required = true)
            @PathVariable UUID agentId) {
        return service.getAgentById(distributorId, agentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{agentId}")
    @Operation(
        summary = "Update agent",
        description = "Update an existing agent associated with a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agent successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorAgentDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agent data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorAgentDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agent", required = true)
            @PathVariable UUID agentId,
            @Parameter(description = "Updated agent data", required = true)
            @Valid @RequestBody DistributorAgentDTO dto) {
        return service.updateAgent(distributorId, agentId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{agentId}")
    @Operation(
        summary = "Delete agent",
        description = "Delete an agent associated with a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Agent successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the agent", required = true)
            @PathVariable UUID agentId) {
        return service.deleteAgent(distributorId, agentId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
