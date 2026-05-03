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

import com.firefly.core.distributor.interfaces.dtos.DistributorBrandingDTO;
import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.models.entities.DistributorBranding;
import com.firefly.core.distributor.models.entities.Product;
import org.mapstruct.*;

/**
 * Mapper for converting between DistributorBranding entity and DistributorBrandingDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistributorBrandingMapper {

    /**
     * Converts a DistributorBranding entity to a DistributorBrandingDTO.
     *
     * @param entity the DistributorBranding entity to convert
     * @return the corresponding DistributorBrandingDTO
     */
    DistributorBrandingDTO toDTO(DistributorBranding entity);

    /**
     * Converts a DistributorBrandingDTO to a DistributorBranding entity.
     *
     * @param dto the DistributorBrandingDTO to convert
     * @return the corresponding DistributorBranding entity
     */
    DistributorBranding toEntity(DistributorBrandingDTO dto);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DistributorBrandingDTO dto, @MappingTarget DistributorBranding entity);

}