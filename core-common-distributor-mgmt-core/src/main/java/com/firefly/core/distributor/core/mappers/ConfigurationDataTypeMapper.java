/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.ConfigurationDataTypeDTO;
import com.firefly.core.distributor.models.entities.ConfigurationDataType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConfigurationDataTypeMapper {
    ConfigurationDataTypeMapper INSTANCE = Mappers.getMapper(ConfigurationDataTypeMapper.class);
    ConfigurationDataTypeDTO toDTO(ConfigurationDataType entity);
    ConfigurationDataType toEntity(ConfigurationDataTypeDTO dto);
}
