/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.DistributorConfigurationDTO;
import io.fireflysoftware.core.distributor.models.entities.DistributorConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorConfigurationMapper {
    DistributorConfigurationMapper INSTANCE = Mappers.getMapper(DistributorConfigurationMapper.class);
    DistributorConfigurationDTO toDTO(DistributorConfiguration entity);
    DistributorConfiguration toEntity(DistributorConfigurationDTO dto);
}
