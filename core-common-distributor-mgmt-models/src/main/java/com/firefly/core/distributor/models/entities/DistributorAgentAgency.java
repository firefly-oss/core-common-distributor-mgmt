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
 * Entity representing the relationship between agents and agencies with specific roles.
 * Agents can be assigned to multiple agencies with different roles in each.
 * Maps to the 'distributor_agent_agency' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_agent_agency")
public class DistributorAgentAgency {

    @Id
    private UUID id;

    @Column("agent_id")
    private UUID agentId;

    @Column("agency_id")
    private UUID agencyId;

    @Column("role_id")
    private UUID roleId;

    @Column("is_primary_agency")
    private Boolean isPrimaryAgency;

    @Column("is_active")
    private Boolean isActive;

    @Column("assigned_at")
    private LocalDateTime assignedAt;

    @Column("unassigned_at")
    private LocalDateTime unassignedAt;

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
