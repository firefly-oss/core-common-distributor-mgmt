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
 * Entity representing payment methods configured for agency disbursements.
 * Each agency can have multiple payment methods for receiving funds from the platform.
 * Supports bank accounts, digital wallets, and other payment instruments.
 * Maps to the 'agency_payment_method' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("agency_payment_method")
public class AgencyPaymentMethod {

    @Id
    private UUID id;

    @Column("agency_id")
    private UUID agencyId;

    @Column("payment_method_type")
    private String paymentMethodType; // BANK_ACCOUNT, DIGITAL_WALLET, WIRE_TRANSFER, CHECK, etc.

    @Column("payment_provider")
    private String paymentProvider; // Bank name, wallet provider, etc.

    @Column("account_holder_name")
    private String accountHolderName;

    @Column("account_number")
    private String accountNumber; // Encrypted

    @Column("routing_number")
    private String routingNumber;

    @Column("swift_code")
    private String swiftCode;

    @Column("iban")
    private String iban;

    @Column("bank_name")
    private String bankName;

    @Column("bank_branch")
    private String bankBranch;

    @Column("bank_address")
    private String bankAddress;

    @Column("currency_code")
    private String currencyCode;

    @Column("wallet_id")
    private String walletId; // For digital wallets

    @Column("wallet_phone")
    private String walletPhone; // For mobile wallets

    @Column("wallet_email")
    private String walletEmail; // For email-based wallets

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("is_verified")
    private Boolean isVerified;

    @Column("verified_at")
    private LocalDateTime verifiedAt;

    @Column("verified_by")
    private UUID verifiedBy;

    @Column("is_active")
    private Boolean isActive;

    @Column("notes")
    private String notes;

    @Column("metadata")
    private String metadata; // JSON for additional provider-specific data

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

