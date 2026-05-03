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
import com.firefly.core.distributor.core.services.AgentRoleService;
import com.firefly.core.distributor.interfaces.dtos.AgentRoleDTO;
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
@RequestMapping("/api/v1/agent-roles")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Agent Roles",
    description = "API for managing agent roles - roles that distributor agents can have"
)
public class AgentRoleController {

    private final AgentRoleService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter agent roles",
        description = "Retrieve a paginated list of agent roles based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered agent roles",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter request",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<AgentRoleDTO>>> filter(
            @Parameter(description = "Filter criteria for agent roles", required = true)
            @Valid @RequestBody FilterRequest<AgentRoleDTO> request) {
        return service.filterAgentRoles(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create agent role",
        description = "Create a new agent role"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Agent role successfully created",
            content = @Content(schema = @Schema(implementation = AgentRoleDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agent role data",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgentRoleDTO>> create(
            @Parameter(description = "Agent role data to create", required = true)
            @Valid @RequestBody AgentRoleDTO dto) {
        return service.createAgentRole(dto)
                .map(role -> ResponseEntity.status(HttpStatus.CREATED).body(role));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get agent role by ID",
        description = "Retrieve a specific agent role by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agent role found",
            content = @Content(schema = @Schema(implementation = AgentRoleDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent role not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgentRoleDTO>> getById(
            @Parameter(description = "Unique identifier of the agent role", required = true)
            @PathVariable UUID id) {
        return service.getAgentRoleById(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update agent role",
        description = "Update an existing agent role"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Agent role successfully updated",
            content = @Content(schema = @Schema(implementation = AgentRoleDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid agent role data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent role not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgentRoleDTO>> update(
            @Parameter(description = "Unique identifier of the agent role", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated agent role data", required = true)
            @Valid @RequestBody AgentRoleDTO dto) {
        return service.updateAgentRole(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete agent role",
        description = "Delete an agent role from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Agent role successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agent role not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the agent role", required = true)
            @PathVariable UUID id) {
        return service.deleteAgentRole(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
