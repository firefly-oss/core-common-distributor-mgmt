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

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for distributor authorized territory information.
 * Represents geographic territories where a distributor is authorized to operate.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Distributor authorized territory information")
public class DistributorAuthorizedTerritoryDTO {

    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @NotNull(message = "Distributor ID is required")
    @Schema(description = "Distributor ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID distributorId;

    @NotNull(message = "Country ID is required")
    @Schema(description = "Country ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID countryId;

    @Schema(description = "Administrative division ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID administrativeDivisionId;

    @Size(max = 50, message = "Authorization level must not exceed 50 characters")
    @Schema(description = "Authorization level", example = "COUNTRY", allowableValues = {"COUNTRY", "REGION", "CITY"})
    private String authorizationLevel;

    @Schema(description = "Whether this authorization is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Authorization start date", example = "2025-01-01T00:00:00")
    private LocalDateTime authorizedFrom;

    @Schema(description = "Authorization end date", example = "2026-12-31T23:59:59")
    private LocalDateTime authorizedUntil;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    @Schema(description = "Additional notes", example = "Authorized for retail operations only")
    private String notes;

    @Schema(description = "Creation timestamp", example = "2025-01-01T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "User who created this record", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID createdBy;

    @Schema(description = "Last update timestamp", example = "2025-01-15T10:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who last updated this record", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID updatedBy;
}

