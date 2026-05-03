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
import com.firefly.core.distributor.core.services.AgencyPaymentMethodService;
import com.firefly.core.distributor.interfaces.dtos.AgencyPaymentMethodDTO;
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
@RequestMapping("/api/v1/agencies/{agencyId}/payment-methods")
@RequiredArgsConstructor
@Validated
@Tag(
    name = "Agency Payment Methods",
    description = "API for managing payment methods for agency disbursements"
)
public class AgencyPaymentMethodController {

    private final AgencyPaymentMethodService service;

    @PostMapping("/filter")
    @Operation(
        summary = "Filter payment methods for an agency",
        description = "Retrieve a paginated list of payment methods for a specific agency based on filtering criteria"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved filtered payment methods",
            content = @Content(schema = @Schema(implementation = PaginationResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter request",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<PaginationResponse<AgencyPaymentMethodDTO>>> filter(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Filter criteria for payment methods", required = true)
            @Valid @RequestBody FilterRequest<AgencyPaymentMethodDTO> request) {
        return service.filterPaymentMethods(agencyId, request)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(
        summary = "Create payment method for an agency",
        description = "Create a new payment method for a specific agency"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Payment method successfully created",
            content = @Content(schema = @Schema(implementation = AgencyPaymentMethodDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid payment method data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgencyPaymentMethodDTO>> create(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Payment method data to create", required = true)
            @Valid @RequestBody AgencyPaymentMethodDTO dto) {
        return service.createPaymentMethod(agencyId, dto)
                .map(paymentMethod -> ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod));
    }

    @GetMapping("/{paymentMethodId}")
    @Operation(
        summary = "Get payment method by ID",
        description = "Retrieve a specific payment method for an agency by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Payment method found",
            content = @Content(schema = @Schema(implementation = AgencyPaymentMethodDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Payment method or agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgencyPaymentMethodDTO>> getById(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Unique identifier of the payment method", required = true)
            @PathVariable UUID paymentMethodId) {
        return service.getPaymentMethodById(agencyId, paymentMethodId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{paymentMethodId}")
    @Operation(
        summary = "Update payment method",
        description = "Update an existing payment method for an agency"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Payment method successfully updated",
            content = @Content(schema = @Schema(implementation = AgencyPaymentMethodDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid payment method data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Payment method or agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgencyPaymentMethodDTO>> update(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Unique identifier of the payment method", required = true)
            @PathVariable UUID paymentMethodId,
            @Parameter(description = "Updated payment method data", required = true)
            @Valid @RequestBody AgencyPaymentMethodDTO dto) {
        return service.updatePaymentMethod(agencyId, paymentMethodId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{paymentMethodId}")
    @Operation(
        summary = "Delete payment method",
        description = "Delete a payment method for an agency from the system"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Payment method successfully deleted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Payment method or agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<Void>> delete(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Unique identifier of the payment method", required = true)
            @PathVariable UUID paymentMethodId) {
        return service.deletePaymentMethod(agencyId, paymentMethodId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PatchMapping("/{paymentMethodId}/set-primary")
    @Operation(
        summary = "Set payment method as primary",
        description = "Set a specific payment method as the primary payment method for an agency"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Payment method successfully set as primary",
            content = @Content(schema = @Schema(implementation = AgencyPaymentMethodDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Payment method or agency not found",
            content = @Content
        )
    })
    public Mono<ResponseEntity<AgencyPaymentMethodDTO>> setPrimary(
            @Parameter(description = "Unique identifier of the agency", required = true)
            @PathVariable UUID agencyId,
            @Parameter(description = "Unique identifier of the payment method", required = true)
            @PathVariable UUID paymentMethodId) {
        return service.setPrimaryPaymentMethod(agencyId, paymentMethodId)
                .map(ResponseEntity::ok);
    }
}

