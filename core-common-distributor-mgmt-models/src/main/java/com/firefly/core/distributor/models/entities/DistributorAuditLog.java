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

import com.firefly.core.distributor.interfaces.enums.DistributorActionEnum;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing an audit log entry for distributor-related actions.
 * Maps to the 'distributor_audit_log' table in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("distributor_audit_log")
public class DistributorAuditLog {

    @Id
    private UUID id;

    @Column("distributor_id")
    private UUID distributorId;

    @Column("action")
    private DistributorActionEnum action;

    @Column("entity")
    private String entity;

    @Column("entity_id")
    private String entityId;

    @Column("audit_metadata")
    private String auditMetadata;

    @Column("ip_address")
    private String ipAddress;

    @Column("user_id")
    private UUID userId;

    @Column("audit_timestamp")
    private LocalDateTime auditTimestamp;
}
