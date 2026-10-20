/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.ConfigurationDataTypeDTO;
import io.fireflysoftware.core.distributor.models.entities.ConfigurationDataType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConfigurationDataTypeMapper {
    ConfigurationDataTypeMapper INSTANCE = Mappers.getMapper(ConfigurationDataTypeMapper.class);
    ConfigurationDataTypeDTO toDTO(ConfigurationDataType entity);
    ConfigurationDataType toEntity(ConfigurationDataTypeDTO dto);
}
