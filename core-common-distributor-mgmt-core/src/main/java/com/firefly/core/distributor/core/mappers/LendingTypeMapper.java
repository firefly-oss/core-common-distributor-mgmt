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

import com.firefly.core.distributor.interfaces.dtos.LendingTypeDTO;
import com.firefly.core.distributor.models.entities.LendingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper for converting between LendingType entity and LendingTypeDTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LendingTypeMapper {

    /**
     * Convert a LendingType entity to a LendingTypeDTO.
     *
     * @param lendingType The LendingType entity to convert
     * @return The corresponding LendingTypeDTO
     */
    LendingTypeDTO toDto(LendingType lendingType);

    /**
     * Convert a LendingTypeDTO to a LendingType entity.
     *
     * @param lendingTypeDTO The LendingTypeDTO to convert
     * @return The corresponding LendingType entity
     */
    LendingType toEntity(LendingTypeDTO lendingTypeDTO);
}