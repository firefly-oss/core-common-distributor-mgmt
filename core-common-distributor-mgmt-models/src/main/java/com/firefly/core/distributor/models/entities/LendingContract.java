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
 * Entity representing a lending contract between a customer and a distributor.
 * This includes various types of lending: leases, rentals, loans, and financing.
 * Maps to the 'lending_contract' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("lending_contract")
public class LendingContract {

    @Id
    private UUID id;

    @Column("contract_id")
    private UUID contractId;

    @Column("party_id")
    private UUID partyId;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("product_id")
    private UUID productId;

    @Column("lending_configuration_id")
    private UUID lendingConfigurationId;

    @Column("originating_agent_id")
    private UUID originatingAgentId;

    @Column("agency_id")
    private UUID agencyId;

    @Column("start_date")
    private LocalDate startDate;

    @Column("end_date")
    private LocalDate endDate;

    @Column("monthly_payment")
    private BigDecimal monthlyPayment;

    @Column("down_payment")
    private BigDecimal downPayment;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("status")
    private String status;

    @Column("approval_date")
    private LocalDateTime approvalDate;

    @Column("approved_by")
    private UUID approvedBy;

    @Column("terms_conditions")
    private String termsConditions;

    @Column("notes")
    private String notes;

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