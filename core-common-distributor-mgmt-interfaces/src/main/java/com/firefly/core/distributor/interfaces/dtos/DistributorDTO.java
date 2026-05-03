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

import org.fireflyframework.annotations.ValidPhoneNumber;
import org.fireflyframework.annotations.ValidTaxId;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Distributor entity representing distributor organizations.
 * Used for transferring distributor data between application layers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Size(max = 255, message = "External code cannot exceed 255 characters")
    private String externalCode;

    @NotBlank(message = "Distributor name is required")
    @Size(max = 255, message = "Distributor name cannot exceed 255 characters")
    private String name;

    @Size(max = 255, message = "Display name cannot exceed 255 characters")
    private String displayName;

    @ValidTaxId
    private String taxId;

    @Size(max = 100, message = "Registration number cannot exceed 100 characters")
    private String registrationNumber;

    @Size(max = 255, message = "Website URL cannot exceed 255 characters")
    private String websiteUrl;
    @ValidPhoneNumber
    private String phoneNumber;
    @Email
    private String email;
    @Email(message = "Support email must be a valid email address")
    private String supportEmail;

    @Size(max = 255, message = "Address line cannot exceed 255 characters")
    private String addressLine;

    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    private String postalCode;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    private UUID countryId;
    private Boolean isActive;
    private Boolean isTestDistributor;

    @Size(max = 50, message = "Time zone cannot exceed 50 characters")
    private String timeZone;

    @Size(max = 10, message = "Default locale cannot exceed 10 characters")
    private String defaultLocale;
    private LocalDateTime onboardedAt;
    private LocalDateTime terminatedAt;
    private LocalDateTime createdAt;
    private UUID createdBy;
    private LocalDateTime updatedAt;
    private UUID updatedBy;
}
