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

import com.firefly.core.distributor.interfaces.dtos.DistributorOperationDTO;
import com.firefly.core.distributor.models.entities.DistributorOperation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between DistributorOperation entity and DistributorOperationDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorOperationMapper {

    /**
     * Converts a DistributorOperation entity to a DistributorOperationDTO.
     *
     * @param entity the DistributorOperation entity to convert
     * @return the corresponding DistributorOperationDTO
     */
    DistributorOperationDTO toDTO(DistributorOperation entity);

    /**
     * Converts a DistributorOperationDTO to a DistributorOperation entity.
     *
     * @param dto the DistributorOperationDTO to convert
     * @return the corresponding DistributorOperation entity
     */
    DistributorOperation toEntity(DistributorOperationDTO dto);
}
