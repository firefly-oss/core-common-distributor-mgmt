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
import org.fireflyframework.annotations.ValidInterestRate;
import org.fireflyframework.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for LendingConfiguration entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LendingConfigurationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotBlank(message = "Lending configuration name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Lending type is required")
    private UUID lendingTypeId;

    @NotNull(message = "Minimum term months is required")
    @Positive(message = "Minimum term months must be positive")
    private Integer minTermMonths;

    @NotNull(message = "Maximum term months is required")
    @Positive(message = "Maximum term months must be positive")
    private Integer maxTermMonths;

    @NotNull(message = "Default term months is required")
    @Positive(message = "Default term months must be positive")
    private Integer defaultTermMonths;
    @ValidInterestRate
    private BigDecimal minDownPaymentPercentage;
    @ValidInterestRate
    private BigDecimal defaultDownPaymentPercentage;
    @ValidInterestRate
    private BigDecimal interestRate;
    @ValidInterestRate
    private BigDecimal processingFeePercentage;
    @ValidInterestRate
    private BigDecimal earlyTerminationFeePercentage;
    @ValidInterestRate
    private BigDecimal latePaymentFeeAmount;
    @Min(value = 0, message = "Grace period days cannot be negative")
    private Integer gracePeriodDays;

    private Boolean isDefault;
    private Boolean isActive;

    @Size(max = 5000, message = "Terms and conditions cannot exceed 5000 characters")
    private String termsConditions;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}
