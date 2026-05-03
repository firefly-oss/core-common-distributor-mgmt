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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for DistributorProductCatalog entity representing products available to distributors.
 * Used for transferring product catalog data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorProductCatalogDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotNull(message = "Product ID is required")
    private UUID productId;

    private String catalogCode;
    private String displayName;
    private String customDescription;
    private Boolean isFeatured;
    private Boolean isAvailable;
    private LocalDateTime availabilityStartDate;
    private LocalDateTime availabilityEndDate;
    private Integer displayOrder;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Boolean shippingAvailable;
    private BigDecimal shippingCost;
    private Integer shippingTimeDays;
    private String specialConditions;
    private String metadata;
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
