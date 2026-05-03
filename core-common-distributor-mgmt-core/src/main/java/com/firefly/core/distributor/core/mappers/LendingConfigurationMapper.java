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

import com.firefly.core.distributor.interfaces.dtos.LendingConfigurationDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.models.entities.LendingConfiguration;
import com.firefly.core.distributor.models.entities.Product;
import org.mapstruct.*;

/**
 * Mapper for converting between LendingConfiguration entity and LendingConfigurationDTO.
 */
@Mapper(componentModel = "spring")
public interface LendingConfigurationMapper {

    /**
     * Convert a LendingConfiguration entity to a LendingConfigurationDTO.
     *
     * @param lendingConfiguration the LendingConfiguration entity
     * @return the LendingConfigurationDTO
     */
    LendingConfigurationDTO toDTO(LendingConfiguration lendingConfiguration);

    /**
     * Convert a LendingConfigurationDTO to a LendingConfiguration entity.
     *
     * @param lendingConfigurationDTO the LendingConfigurationDTO
     * @return the LendingConfiguration entity
     */
    LendingConfiguration toEntity(LendingConfigurationDTO lendingConfigurationDTO);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LendingConfigurationDTO dto, @MappingTarget LendingConfiguration entity);

}