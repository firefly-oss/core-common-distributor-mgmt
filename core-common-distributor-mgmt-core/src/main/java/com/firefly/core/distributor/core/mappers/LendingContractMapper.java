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

import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.models.entities.LendingContract;
import com.firefly.core.distributor.models.entities.Product;
import org.mapstruct.*;

/**
 * Mapper for converting between LendingContract entity and LendingContractDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class LendingContractMapper {

    /**
     * Convert a LendingContract entity to a LendingContractDTO.
     *
     * @param leasingContract the LendingContract entity
     * @return the LendingContractDTO
     */
    public abstract LendingContractDTO toDto(LendingContract leasingContract);

    /**
     * Convert a LendingContractDTO to a LendingContract entity.
     *
     * @param leasingContractDTO the LendingContractDTO
     * @return the LendingContract entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract LendingContract toEntity(LendingContractDTO leasingContractDTO);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(LendingContractDTO dto, @MappingTarget LendingContract entity);

}