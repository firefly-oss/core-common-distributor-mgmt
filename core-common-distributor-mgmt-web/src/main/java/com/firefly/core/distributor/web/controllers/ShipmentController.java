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
import com.firefly.core.distributor.core.services.ShipmentService;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.interfaces.dtos.ShipmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
 * REST controller for managing shipments.
 */
@RestController
@RequestMapping("/api/v1/shipments")
@Tag(name = "Shipment", description = "API for managing shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    /**
     * POST /api/v1/shipments/filter : Filter shipments
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return the ResponseEntity with status 200 (OK) and the list of shipments in body
     */
    @Operation(summary = "Filter shipments", description = "Returns a paginated list of shipments based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipments",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ShipmentDTO>>> filterShipments(
            @Valid @RequestBody FilterRequest<ShipmentDTO> filterRequest) {
        
        return shipmentService.filterShipments(filterRequest)
                .map(ResponseEntity::ok);
    }

    /**
     * POST /api/v1/shipments : Create a new shipment
     *
     * @param shipmentDTO the shipment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shipment
     */
    @Operation(summary = "Create a new shipment", description = "Creates a new shipment with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Shipment successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid shipment data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShipmentDTO>> createShipment(
            @Valid @RequestBody ShipmentDTO shipmentDTO) {
        
        return shipmentService.createShipment(shipmentDTO)
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    /**
     * PUT /api/v1/shipments/{id} : Update an existing shipment
     *
     * @param id the ID of the shipment to update
     * @param shipmentDTO the shipment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment
     */
    @Operation(summary = "Update an existing shipment", description = "Updates an existing shipment with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shipment successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid shipment data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Shipment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShipmentDTO>> updateShipment(
            @Parameter(description = "ID of the shipment to update", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ShipmentDTO shipmentDTO) {
        
        return shipmentService.updateShipment(id, shipmentDTO)
                .map(ResponseEntity::ok);
    }

    /**
     * DELETE /api/v1/shipments/{id} : Delete a shipment
     *
     * @param id the ID of the shipment to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @Operation(summary = "Delete a shipment", description = "Deletes a shipment based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Shipment successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Shipment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteShipment(
            @Parameter(description = "ID of the shipment to delete", required = true)
            @PathVariable UUID id) {
        
        return shipmentService.deleteShipment(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * GET /api/v1/shipments/{id} : Get a shipment by ID
     *
     * @param id the ID of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment
     */
    @Operation(summary = "Get shipment by ID", description = "Returns a shipment based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipment",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Shipment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShipmentDTO>> getShipmentById(
            @Parameter(description = "ID of the shipment to retrieve", required = true)
            @PathVariable UUID id) {
        
        return shipmentService.getShipmentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/shipments/tracking/{trackingNumber} : Get a shipment by tracking number
     *
     * @param trackingNumber the tracking number of the shipment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shipment
     */
    @Operation(summary = "Get shipment by tracking number", description = "Returns a shipment based on its tracking number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipment",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Shipment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/tracking/{trackingNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShipmentDTO>> getShipmentByTrackingNumber(
            @Parameter(description = "Tracking number of the shipment to retrieve", required = true)
            @PathVariable String trackingNumber) {
        
        return shipmentService.getShipmentByTrackingNumber(trackingNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/shipments/leasing-contract/{leasingContractId} : Get all shipments for a leasing contract
     *
     * @param leasingContractId the ID of the leasing contract
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @Operation(summary = "Get shipments by leasing contract", description = "Returns all shipments associated with a specific leasing contract")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipments",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Leasing contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/leasing-contract/{leasingContractId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByLendingContractId(
            @Parameter(description = "ID of the leasing contract", required = true)
            @PathVariable UUID leasingContractId) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByLendingContractId(leasingContractId);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * GET /api/v1/shipments/product/{productId} : Get all shipments for a product
     *
     * @param productId the ID of the product
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @Operation(summary = "Get shipments by product", description = "Returns all shipments associated with a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipments",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ShipmentDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByProductId(
            @Parameter(description = "ID of the product", required = true)
            @PathVariable UUID productId) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByProductId(productId);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * GET /api/v1/shipments/status/{status} : Get all shipments with a specific status
     *
     * @param status the status
     * @return the ResponseEntity with status 200 (OK) and with body the list of shipments
     */
    @Operation(summary = "Get shipments by status", description = "Returns all shipments with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shipments",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status value", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ShipmentDTO>>> getShipmentsByStatus(
            @Parameter(description = "Status value to filter shipments", required = true)
            @PathVariable String status) {
        
        Flux<ShipmentDTO> shipments = shipmentService.getShipmentsByStatus(status);
        return Mono.just(ResponseEntity.ok(shipments));
    }

    /**
     * PUT /api/v1/shipments/{id}/status : Update the status of a shipment
     *
     * @param id the ID of the shipment to update
     * @param status the new status
     * @param updatedBy the ID of the user updating the status
     * @return the ResponseEntity with status 200 (OK) and with body the updated shipment
     */
    @Operation(summary = "Update shipment status", description = "Updates the status of an existing shipment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shipment status successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ShipmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status value", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Shipment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ShipmentDTO>> updateShipmentStatus(
            @Parameter(description = "ID of the shipment to update", required = true)
            @PathVariable UUID id,
            @Parameter(description = "New status value", required = true)
            @RequestParam String status,
            @Parameter(description = "ID of the user updating the status", required = true)
            @RequestParam UUID updatedBy) {
        
        return shipmentService.updateShipmentStatus(id, status, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}