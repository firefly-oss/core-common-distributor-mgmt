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
 * DTO for agency payment method information.
 * Represents payment methods configured for agency disbursements.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Agency payment method information for disbursements")
public class AgencyPaymentMethodDTO {

    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @NotNull(message = "Agency ID is required")
    @Schema(description = "Agency ID", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID agencyId;

    @NotNull(message = "Payment method type is required")
    @Size(max = 50, message = "Payment method type must not exceed 50 characters")
    @Schema(description = "Payment method type", example = "BANK_ACCOUNT", required = true, 
            allowableValues = {"BANK_ACCOUNT", "DIGITAL_WALLET", "WIRE_TRANSFER", "CHECK"})
    private String paymentMethodType;

    @Size(max = 100, message = "Payment provider must not exceed 100 characters")
    @Schema(description = "Payment provider name", example = "Bank of America")
    private String paymentProvider;

    @Size(max = 200, message = "Account holder name must not exceed 200 characters")
    @Schema(description = "Account holder name", example = "ABC Agency LLC")
    private String accountHolderName;

    @Size(max = 100, message = "Account number must not exceed 100 characters")
    @Schema(description = "Account number (masked)", example = "****1234")
    private String accountNumber;

    @Size(max = 50, message = "Routing number must not exceed 50 characters")
    @Schema(description = "Routing number", example = "021000021")
    private String routingNumber;

    @Size(max = 20, message = "SWIFT code must not exceed 20 characters")
    @Schema(description = "SWIFT/BIC code", example = "BOFAUS3N")
    private String swiftCode;

    @Size(max = 50, message = "IBAN must not exceed 50 characters")
    @Schema(description = "IBAN", example = "GB82WEST12345698765432")
    private String iban;

    @Size(max = 200, message = "Bank name must not exceed 200 characters")
    @Schema(description = "Bank name", example = "Bank of America")
    private String bankName;

    @Size(max = 200, message = "Bank branch must not exceed 200 characters")
    @Schema(description = "Bank branch", example = "Downtown Branch")
    private String bankBranch;

    @Size(max = 500, message = "Bank address must not exceed 500 characters")
    @Schema(description = "Bank address", example = "123 Main St, New York, NY 10001")
    private String bankAddress;

    @Size(max = 3, message = "Currency code must be 3 characters")
    @Schema(description = "Currency code", example = "USD")
    private String currencyCode;

    @Size(max = 100, message = "Wallet ID must not exceed 100 characters")
    @Schema(description = "Digital wallet ID", example = "wallet123456")
    private String walletId;

    @Size(max = 20, message = "Wallet phone must not exceed 20 characters")
    @Schema(description = "Wallet phone number", example = "+1234567890")
    private String walletPhone;

    @Size(max = 100, message = "Wallet email must not exceed 100 characters")
    @Schema(description = "Wallet email", example = "payments@agency.com")
    private String walletEmail;

    @Schema(description = "Whether this is the primary payment method", example = "true")
    private Boolean isPrimary;

    @Schema(description = "Whether this payment method is verified", example = "true")
    private Boolean isVerified;

    @Schema(description = "Verification timestamp", example = "2025-01-15T10:30:00")
    private LocalDateTime verifiedAt;

    @Schema(description = "User who verified this payment method", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID verifiedBy;

    @Schema(description = "Whether this payment method is active", example = "true")
    private Boolean isActive;

    @Size(max = 500, message = "Notes must not exceed 500 characters")
    @Schema(description = "Additional notes", example = "Primary disbursement account")
    private String notes;

    @Schema(description = "Additional metadata in JSON format", example = "{\"provider_id\": \"12345\"}")
    private String metadata;

    @Schema(description = "Creation timestamp", example = "2025-01-01T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "User who created this record", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID createdBy;

    @Schema(description = "Last update timestamp", example = "2025-01-15T10:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who last updated this record", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID updatedBy;
}

