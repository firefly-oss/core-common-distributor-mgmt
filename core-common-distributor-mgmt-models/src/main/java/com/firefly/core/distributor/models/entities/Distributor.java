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


package com.firefly.core.distributor.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a distributor in the system.
 * Maps to the 'distributor' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor")
public class Distributor {

    @Id
    private UUID id;

    @Column("external_code")
    private String externalCode;

    @Column("name")
    private String name;

    @Column("display_name")
    private String displayName;

    @Column("tax_id")
    private String taxId;

    @Column("registration_number")
    private String registrationNumber;

    @Column("website_url")
    private String websiteUrl;

    @Column("phone_number")
    private String phoneNumber;

    @Column("email")
    private String email;

    @Column("support_email")
    private String supportEmail;

    @Column("address_line")
    private String addressLine;

    @Column("postal_code")
    private String postalCode;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("country_id")
    private UUID countryId;

    @Column("is_active")
    private Boolean isActive;

    @Column("is_test_distributor")
    private Boolean isTestDistributor;

    @Column("time_zone")
    private String timeZone;

    @Column("default_locale")
    private String defaultLocale;

    @Column("onboarded_at")
    private LocalDateTime onboardedAt;

    @Column("terminated_at")
    private LocalDateTime terminatedAt;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("created_by")
    private UUID createdBy;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("updated_by")
    private UUID updatedBy;
}