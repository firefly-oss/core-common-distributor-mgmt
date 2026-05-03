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
import com.firefly.core.distributor.core.services.DistributorAgentAgencyService;
import com.firefly.core.distributor.interfaces.dtos.DistributorAgentAgencyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing distributor agent agency relationships.
 */
@RestController
@RequestMapping("/api/v1/distributors/{distributorId}/agent-agencies")
@Tag(name = "Distributor Agent Agency", description = "API for managing agent-agency relationships within a distributor")
public class DistributorAgentAgencyController {

    @Autowired
    private DistributorAgentAgencyService service;

    /**
     * POST /api/v1/distributors/{distributorId}/agent-agencies/filter : Filter agent-agency relationships
     *
     * @param distributorId the ID of the distributor
     * @param request the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of agent-agency relationships in body
     */
    @Operation(summary = "Filter agent-agency relationships", description = "Returns a paginated list of agent-agency relationships based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved agent-agency relationships",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<DistributorAgentAgencyDTO>>> filter(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody FilterRequest<DistributorAgentAgencyDTO> request) {
        return service.filterAgentAgencies(distributorId, request)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/distributors/{distributorId}/agent-agencies : Create a new agent-agency relationship
     *
     * @param distributorId the ID of the distributor
     * @param dto the agent-agency relationship to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agent-agency relationship
     */
    @Operation(summary = "Create a new agent-agency relationship", description = "Creates a new agent-agency relationship with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agent-agency relationship successfully created",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DistributorAgentAgencyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid agent-agency relationship data provided",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorAgentAgencyDTO>> create(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Valid @RequestBody DistributorAgentAgencyDTO dto) {
        return service.createAgentAgency(distributorId, dto)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * GET /api/v1/distributors/{distributorId}/agent-agencies/{id} : Get an agent-agency relationship by ID
     *
     * @param distributorId the ID of the distributor
     * @param id the ID of the agent-agency relationship to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agent-agency relationship, or with status 404 (Not Found)
     */
    @Operation(summary = "Get agent-agency relationship by ID", description = "Returns a single agent-agency relationship")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved agent-agency relationship",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DistributorAgentAgencyDTO.class))),
        @ApiResponse(responseCode = "404", description = "Agent-agency relationship not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorAgentAgencyDTO>> getById(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the agent-agency relationship to retrieve", required = true)
            @PathVariable UUID id) {
        return service.getAgentAgencyById(distributorId, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/v1/distributors/{distributorId}/agent-agencies/{id} : Update an existing agent-agency relationship
     *
     * @param distributorId the ID of the distributor
     * @param id the ID of the agent-agency relationship to update
     * @param dto the updated agent-agency relationship information
     * @return the ResponseEntity with status 200 (OK) and with body the updated agent-agency relationship
     */
    @Operation(summary = "Update an agent-agency relationship", description = "Updates an existing agent-agency relationship with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agent-agency relationship successfully updated",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DistributorAgentAgencyDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid agent-agency relationship data provided",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Agent-agency relationship not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<DistributorAgentAgencyDTO>> update(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the agent-agency relationship to update", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DistributorAgentAgencyDTO dto) {
        return service.updateAgentAgency(distributorId, id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/distributors/{distributorId}/agent-agencies/{id} : Delete an agent-agency relationship
     *
     * @param distributorId the ID of the distributor
     * @param id the ID of the agent-agency relationship to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @Operation(summary = "Delete an agent-agency relationship", description = "Deletes an agent-agency relationship by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Agent-agency relationship successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Agent-agency relationship not found",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "ID of the agent-agency relationship to delete", required = true)
            @PathVariable UUID id) {
        return service.deleteAgentAgency(distributorId, id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
