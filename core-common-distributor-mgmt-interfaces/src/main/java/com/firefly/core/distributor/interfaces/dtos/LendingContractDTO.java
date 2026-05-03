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


package com.firefly.core.distributor.interfaces.dtos;

import org.fireflyframework.annotations.ValidAmount;
import org.fireflyframework.annotations.ValidDate;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for LendingContract entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LendingContractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Lending configuration ID is required")
    private UUID lendingConfigurationId;

    private UUID originatingAgentId;
    private UUID agencyId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @ValidAmount
    @NotNull(message = "Monthly payment is required")
    private BigDecimal monthlyPayment;

    @ValidAmount
    @NotNull(message = "Down payment is required")
    private BigDecimal downPayment;

    @ValidAmount
    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    @Pattern(regexp = "DRAFT|PENDING|APPROVED|ACTIVE|COMPLETED|CANCELLED|TERMINATED",
             message = "Status must be one of: DRAFT, PENDING, APPROVED, ACTIVE, COMPLETED, CANCELLED, TERMINATED")
    private String status;

    private LocalDateTime approvalDate;

    private UUID approvedBy;

    @Size(max = 5000, message = "Terms and conditions cannot exceed 5000 characters")
    private String termsConditions;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    private Boolean isActive;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID updatedBy;
}
