/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorConfigurationDTO;
import com.firefly.core.distributor.models.entities.DistributorConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorConfigurationMapper {
    DistributorConfigurationMapper INSTANCE = Mappers.getMapper(DistributorConfigurationMapper.class);
    DistributorConfigurationDTO toDTO(DistributorConfiguration entity);
    DistributorConfiguration toEntity(DistributorConfigurationDTO dto);
}
