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

import com.firefly.core.distributor.interfaces.dtos.AgencyPaymentMethodDTO;
import com.firefly.core.distributor.models.entities.AgencyPaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper for converting between AgencyPaymentMethod entity and AgencyPaymentMethodDTO.
 */
@Mapper(componentModel = "spring")
public interface AgencyPaymentMethodMapper {
    AgencyPaymentMethodMapper INSTANCE = Mappers.getMapper(AgencyPaymentMethodMapper.class);
    
    /**
     * Converts AgencyPaymentMethod entity to AgencyPaymentMethodDTO.
     *
     * @param entity the AgencyPaymentMethod entity to convert
     * @return the converted AgencyPaymentMethodDTO
     */
    AgencyPaymentMethodDTO toDTO(AgencyPaymentMethod entity);
    
    /**
     * Converts AgencyPaymentMethodDTO to AgencyPaymentMethod entity.
     *
     * @param dto the AgencyPaymentMethodDTO to convert
     * @return the converted AgencyPaymentMethod entity
     */
    AgencyPaymentMethod toEntity(AgencyPaymentMethodDTO dto);
}

