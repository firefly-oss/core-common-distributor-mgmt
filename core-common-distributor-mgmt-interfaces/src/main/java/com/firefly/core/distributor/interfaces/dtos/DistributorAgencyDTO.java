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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for DistributorAgency entity representing physical agency locations.
 * Used for transferring distributor agency data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorAgencyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Distributor ID is required")
    private UUID distributorId;

    @NotBlank(message = "Agency name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Agency code is required")
    @Size(max = 100, message = "Code must not exceed 100 characters")
    private String code;

    private String description;

    @NotNull(message = "Country ID is required")
    private UUID countryId;

    private UUID administrativeDivisionId;

    @Size(max = 255, message = "Address line must not exceed 255 characters")
    private String addressLine;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Size(max = 50, message = "Phone number must not exceed 50 characters")
    private String phoneNumber;

    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    // Fiscal and Legal Information
    @Size(max = 255, message = "Legal entity name must not exceed 255 characters")
    private String legalEntityName;

    private UUID legalFormId; // References legal_form(legal_form_id) from master data

    @Size(max = 100, message = "Tax ID must not exceed 100 characters")
    private String taxId;

    @Size(max = 100, message = "Registration number must not exceed 100 characters")
    private String registrationNumber;

    @Size(max = 100, message = "VAT number must not exceed 100 characters")
    private String vatNumber;

    @Size(max = 255, message = "Fiscal address line must not exceed 255 characters")
    private String fiscalAddressLine;

    @Size(max = 20, message = "Fiscal postal code must not exceed 20 characters")
    private String fiscalPostalCode;

    @Size(max = 100, message = "Fiscal city must not exceed 100 characters")
    private String fiscalCity;

    @Size(max = 100, message = "Fiscal state must not exceed 100 characters")
    private String fiscalState;

    private UUID fiscalCountryId;

    // Operational Flags
    private Boolean isHeadquarters;
    private Boolean isActive;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID createdBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID updatedBy;
}
