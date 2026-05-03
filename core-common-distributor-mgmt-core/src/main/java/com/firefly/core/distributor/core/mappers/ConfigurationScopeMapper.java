/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.ConfigurationScopeDTO;
import com.firefly.core.distributor.models.entities.ConfigurationScope;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConfigurationScopeMapper {
    ConfigurationScopeMapper INSTANCE = Mappers.getMapper(ConfigurationScopeMapper.class);
    ConfigurationScopeDTO toDTO(ConfigurationScope entity);
    ConfigurationScope toEntity(ConfigurationScopeDTO dto);
}
