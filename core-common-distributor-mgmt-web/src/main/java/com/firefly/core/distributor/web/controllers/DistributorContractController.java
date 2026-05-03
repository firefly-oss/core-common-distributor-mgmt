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
import com.firefly.core.distributor.core.services.DistributorContractService;
import com.firefly.core.distributor.interfaces.dtos.DistributorContractDTO;
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
@RequestMapping("/api/v1/distributors/{distributorId}/contracts")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Distributor Contract",
    description = "API for managing contracts associated with distributors"
)
public class DistributorContractController {

    private final DistributorContractService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter contracts for a distributor",
        description = "Retrieve a paginated list of contracts associated with a specific distributor based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered contracts",
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
    public Mono<ResponseEntity<PaginationResponse<DistributorContractDTO>>> filter(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Filter criteria for contracts", required = true)
            @Valid @RequestBody FilterRequest<DistributorContractDTO> request) {
        return service.filterContracts(distributorId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create contract for a distributor",
        description = "Create a new contract associated with a specific distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Contract successfully created",
            content = @Content(schema = @Schema(implementation = DistributorContractDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid contract data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorContractDTO>> create(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Contract data to create", required = true)
            @Valid @RequestBody DistributorContractDTO dto) {
        return service.createContract(distributorId, dto)
                .map(contract -> ResponseEntity.status(HttpStatus.CREATED).body(contract));
    }

    @GetMapping("/{contractId}")
    @Operation(
        summary = "Get contract by ID",
        description = "Retrieve a specific contract associated with a distributor by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contract found",
            content = @Content(schema = @Schema(implementation = DistributorContractDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contract or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorContractDTO>> getById(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the contract", required = true)
            @PathVariable UUID contractId) {
        return service.getContractById(distributorId, contractId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{contractId}")
    @Operation(
        summary = "Update contract",
        description = "Update an existing contract associated with a distributor"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contract successfully updated",
            content = @Content(schema = @Schema(implementation = DistributorContractDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid contract data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contract or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<DistributorContractDTO>> update(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "Updated contract data", required = true)
            @Valid @RequestBody DistributorContractDTO dto) {
        return service.updateContract(distributorId, contractId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{contractId}")
    @Operation(
        summary = "Delete contract",
        description = "Delete a contract associated with a distributor from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Contract successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contract or distributor not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the distributor", required = true)
            @PathVariable UUID distributorId,
            @Parameter(description = "Unique identifier of the contract", required = true)
            @PathVariable UUID contractId) {
        return service.deleteContract(distributorId, contractId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
