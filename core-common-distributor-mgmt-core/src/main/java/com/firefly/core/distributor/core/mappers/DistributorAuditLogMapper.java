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


package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorAuditLogDTO;
import com.firefly.core.distributor.models.entities.DistributorAuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorAuditLog entity and DistributorAuditLogDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorAuditLogMapper {

    /**
     * Converts a DistributorAuditLog entity to a DistributorAuditLogDTO.
     *
     * @param entity the DistributorAuditLog entity to convert
     * @return the corresponding DistributorAuditLogDTO
     */
    DistributorAuditLogDTO toDTO(DistributorAuditLog entity);

    /**
     * Converts a DistributorAuditLogDTO to a DistributorAuditLog entity.
     *
     * @param dto the DistributorAuditLogDTO to convert
     * @return the corresponding DistributorAuditLog entity
     */
    DistributorAuditLog toEntity(DistributorAuditLogDTO dto);
}