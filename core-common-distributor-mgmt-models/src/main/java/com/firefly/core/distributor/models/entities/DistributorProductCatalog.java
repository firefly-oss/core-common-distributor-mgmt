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
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a product in a distributor's catalog with custom configurations.
 * Allows distributors to customize product information, pricing, shipping, and availability.
 * Maps to the 'distributor_product_catalog' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_product_catalog")
public class DistributorProductCatalog {

    @Id
    private UUID id;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("product_id")
    private UUID productId;

    @Column("catalog_code")
    private String catalogCode;

    @Column("display_name")
    private String displayName;

    @Column("custom_description")
    private String customDescription;

    @Column("is_featured")
    private Boolean isFeatured;

    @Column("is_available")
    private Boolean isAvailable;

    @Column("availability_start_date")
    private LocalDateTime availabilityStartDate;

    @Column("availability_end_date")
    private LocalDateTime availabilityEndDate;

    @Column("display_order")
    private Integer displayOrder;

    @Column("min_quantity")
    private Integer minQuantity;

    @Column("max_quantity")
    private Integer maxQuantity;

    @Column("shipping_available")
    private Boolean shippingAvailable;

    @Column("shipping_cost")
    private BigDecimal shippingCost;

    @Column("shipping_time_days")
    private Integer shippingTimeDays;

    @Column("special_conditions")
    private String specialConditions;

    @Column("metadata")
    private String metadata; // Stored as JSON string

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
