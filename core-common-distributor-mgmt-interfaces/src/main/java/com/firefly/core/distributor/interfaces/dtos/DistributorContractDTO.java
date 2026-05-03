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

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for DistributorContract entity representing contracts with distributors.
 * Used for transferring distributor contract data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorContractDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotBlank(message = "Contract number is required")
    private String contractNumber;

    @NotBlank(message = "Contract type is required")
    private String contractType;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String status;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private LocalDate renewalDate;
    private Integer noticePeriodDays;
    private Boolean autoRenewal;
    private BigDecimal contractValue;
    private String currencyCode;
    private String paymentTerms;
    private String specialTerms;
    private String financialConditions;
    private String productConditions;
    private String serviceLevelAgreements;
    private String metadata;
    private LocalDate signedDate;
    private UUID signedBy;
    private LocalDate approvedDate;
    private UUID approvedBy;
    private LocalDate terminatedDate;
    private UUID terminatedBy;
    private String terminationReason;
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
