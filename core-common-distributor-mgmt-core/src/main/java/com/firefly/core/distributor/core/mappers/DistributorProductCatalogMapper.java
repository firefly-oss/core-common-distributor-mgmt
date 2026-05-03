/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorProductCatalogDTO;
import com.firefly.core.distributor.models.entities.DistributorProductCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorProductCatalogMapper {
    DistributorProductCatalogMapper INSTANCE = Mappers.getMapper(DistributorProductCatalogMapper.class);
    DistributorProductCatalogDTO toDTO(DistributorProductCatalog entity);
    DistributorProductCatalog toEntity(DistributorProductCatalogDTO dto);
}
