/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.DistributorProductCatalogDTO;
import io.fireflysoftware.core.distributor.models.entities.DistributorProductCatalog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorProductCatalogMapper {
    DistributorProductCatalogMapper INSTANCE = Mappers.getMapper(DistributorProductCatalogMapper.class);
    DistributorProductCatalogDTO toDTO(DistributorProductCatalog entity);
    DistributorProductCatalog toEntity(DistributorProductCatalogDTO dto);
}
