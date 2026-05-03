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
import com.firefly.core.distributor.core.services.LendingContractService;
import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * REST controller for managing lending contracts.
 */
@RestController
@RequestMapping("/api/v1/lending-contracts")
@Tag(name = "Lending Contract", description = "API for managing lending contracts")
public class LendingContractController {

    @Autowired
    private LendingContractService lendingContractService;

    /**
     * POST /api/v1/lending-contracts/filter : Filter lending contracts
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of lending contracts in body
     */
    @Operation(summary = "Filter lending contracts", description = "Returns a paginated list of lending contracts based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contracts",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<LendingContractDTO>>> filterLendingContracts(
            @Valid @RequestBody FilterRequest<LendingContractDTO> filterRequest) {
        
        return lendingContractService.filterLendingContracts(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/lending-contracts : Create a new lending contract
     *
     * @param lendingContractDTO the lending contract to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lending contract
     */
    @Operation(summary = "Create a new lending contract", description = "Creates a new lending contract with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Leasing contract successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending contract data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingContractDTO>> createLendingContract(
            @Valid @RequestBody LendingContractDTO lendingContractDTO) {
        
        return lendingContractService.createLendingContract(lendingContractDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/lending-contracts/{id} : Update an existing lending contract
     *
     * @param id the ID of the lending contract to update
     * @param lendingContractDTO the lending contract to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lending contract
     */
    @Operation(summary = "Update an existing lending contract", description = "Updates an existing lending contract with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leasing contract successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid lending contract data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingContractDTO>> updateLendingContract(
            @Parameter(description = "ID of the lending contract to update", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LendingContractDTO lendingContractDTO) {
        
        return lendingContractService.updateLendingContract(id, lendingContractDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/lending-contracts/{id} : Delete a lending contract
     *
     * @param id the ID of the lending contract to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete a lending contract", description = "Deletes a lending contract based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Leasing contract successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteLendingContract(
            @Parameter(description = "ID of the lending contract to delete", required = true)
            @PathVariable UUID id) {
        
        return lendingContractService.deleteLendingContract(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/lending-contracts/{id} : Get a lending contract by ID
     *
     * @param id the ID of the lending contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lending contract
     */
    @Operation(summary = "Get lending contract by ID", description = "Returns a lending contract based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contract",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingContractDTO>> getLendingContractById(
            @Parameter(description = "ID of the lending contract to retrieve", required = true)
            @PathVariable UUID id) {
        
        return lendingContractService.getLendingContractById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/lending-contracts/contract/{contractId} : Get a lending contract by contract ID
     *
     * @param contractId the contract ID of the lending contract to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lending contract
     */
    @Operation(summary = "Get lending contract by contract ID", description = "Returns a lending contract based on its contract ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contract",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/contract/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingContractDTO>> getLendingContractByContractId(
            @Parameter(description = "Contract ID of the lending contract to retrieve", required = true)
            @PathVariable UUID contractId) {
        
        return lendingContractService.getLendingContractByContractId(contractId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/lending-contracts/distributor/{distributorId} : Get all lending contracts for a distributor
     *
     * @param distributorId the ID of the distributor
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending contracts
     */
    @Operation(summary = "Get lending contracts by distributor", description = "Returns all lending contracts associated with a specific distributor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Distributor not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/distributor/{distributorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingContractDTO>>> getLendingContractsByDistributorId(
            @Parameter(description = "ID of the distributor", required = true)
            @PathVariable UUID distributorId) {
        
        Flux<LendingContractDTO> contracts = lendingContractService.getLendingContractsByDistributorId(distributorId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/lending-contracts/product/{productId} : Get all lending contracts for a product
     *
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending contracts
     */
    @Operation(summary = "Get lending contracts by product", description = "Returns all lending contracts associated with a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingContractDTO>>> getLendingContractsByProductId(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable UUID productId) {
        
        Flux<LendingContractDTO> contracts = lendingContractService.getLendingContractsByProductId(productId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/lending-contracts/party/{partyId} : Get all lending contracts for a party
     *
     * @param partyId the ID of the party
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending contracts
     */
    @Operation(summary = "Get lending contracts by party", description = "Returns all lending contracts associated with a specific party")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "404", description = "Party not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/party/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingContractDTO>>> getLendingContractsByPartyId(
            @Parameter(description = "ID of the party", required = true)
            @PathVariable UUID partyId) {
        
        Flux<LendingContractDTO> contracts = lendingContractService.getLendingContractsByPartyId(partyId);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * GET /api/v1/lending-contracts/status/{status} : Get all lending contracts with a specific status
     *
     * @param status the status
     * @return the ResponseEntity with status 200 (OK) and with body the list of lending contracts
     */
    @Operation(summary = "Get lending contracts by status", description = "Returns all lending contracts with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved lending contracts",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status value", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<LendingContractDTO>>> getLendingContractsByStatus(
            @Parameter(description = "Status value to filter lending contracts", required = true)
            @PathVariable String status) {
        
        Flux<LendingContractDTO> contracts = lendingContractService.getLendingContractsByStatus(status);
        return Mono.just(ResponseEntity.ok(contracts));
    }

    /**
     * POST /api/v1/lending-contracts/{id}/approve : Approve a lending contract
     *
     * @param id the ID of the lending contract to approve
     * @param approvedBy the ID of the user approving the contract
     * @return the ResponseEntity with status 200 (OK) and with body the approved lending contract
     */
    @Operation(summary = "Approve a lending contract", description = "Approves a lending contract and updates its status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leasing contract successfully approved",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LendingContractDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid approval request", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<LendingContractDTO>> approveLendingContract(
            @Parameter(description = "ID of the lending contract to approve", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID of the user approving the contract", required = true)
            @RequestParam UUID approvedBy) {
        
        return lendingContractService.approveLendingContract(id, approvedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}