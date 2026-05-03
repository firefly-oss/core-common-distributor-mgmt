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
import org.fireflyframework.utils.annotations.FilterableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for DistributorTermsAndConditions entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorTermsAndConditionsDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @FilterableId
    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    private UUID templateId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Version is required")
    @Size(max = 50, message = "Version cannot exceed 50 characters")
    private String version;

    @NotNull(message = "Effective date is required")
    private LocalDateTime effectiveDate;

    private LocalDateTime expirationDate;

    private LocalDateTime signedDate;

    private UUID signedBy;

    @Pattern(regexp = "DRAFT|PENDING_SIGNATURE|SIGNED|EXPIRED|TERMINATED", 
             message = "Status must be one of: DRAFT, PENDING_SIGNATURE, SIGNED, EXPIRED, TERMINATED")
    private String status;

    private Boolean isActive;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID updatedBy;

    // Related entities
    private DistributorDTO distributor;
    private TermsAndConditionsTemplateDTO template;
}
