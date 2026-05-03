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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a distributor agent (user) with their profile information.
 * Agents are employees or representatives who work for distributors and operate within specific regions/agencies.
 * Maps to the 'distributor_agent' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_agent")
public class DistributorAgent {

    @Id
    private UUID id;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("user_id")
    private UUID userId;

    @Column("employee_code")
    private String employeeCode;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("department")
    private String department;

    @Column("unit")
    private String unit;

    @Column("job_title")
    private String jobTitle;

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

    @Column("administrative_division_id")
    private UUID administrativeDivisionId; // References administrative_division(division_id) from master data

    @Column("hire_date")
    private LocalDate hireDate;

    @Column("termination_date")
    private LocalDate terminationDate;

    @Column("is_active")
    private Boolean isActive;

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
