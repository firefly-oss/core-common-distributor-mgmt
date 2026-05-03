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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a contract between the platform and a distributor.
 * Includes financial terms, product conditions, service level agreements, and special terms.
 * Maps to the 'distributor_contract' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_contract")
public class DistributorContract {

    @Id
    private UUID id;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("contract_number")
    private String contractNumber;

    @Column("contract_type")
    private String contractType;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("status")
    private String status; // Will be mapped to contract_status_enum in database

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("renewal_date")
    private LocalDate renewalDate;

    @Column("notice_period_days")
    private Integer noticePeriodDays;

    @Column("auto_renewal")
    private Boolean autoRenewal;

    @Column("contract_value")
    private BigDecimal contractValue;

    @Column("currency_code")
    private String currencyCode;

    @Column("payment_terms")
    private String paymentTerms;

    @Column("special_terms")
    private String specialTerms;

    @Column("financial_conditions")
    private String financialConditions; // Stored as JSON string

    @Column("product_conditions")
    private String productConditions; // Stored as JSON string

    @Column("service_level_agreements")
    private String serviceLevelAgreements; // Stored as JSON string

    @Column("metadata")
    private String metadata; // Stored as JSON string

    @Column("signed_date")
    private LocalDate signedDate;

    @Column("signed_by")
    private UUID signedBy;

    @Column("approved_date")
    private LocalDate approvedDate;

    @Column("approved_by")
    private UUID approvedBy;

    @Column("terminated_date")
    private LocalDate terminatedDate;

    @Column("terminated_by")
    private UUID terminatedBy;

    @Column("termination_reason")
    private String terminationReason;

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
