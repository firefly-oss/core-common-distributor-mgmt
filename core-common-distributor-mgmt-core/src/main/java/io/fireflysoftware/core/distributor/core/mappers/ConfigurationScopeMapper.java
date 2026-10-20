/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.ConfigurationScopeDTO;
import io.fireflysoftware.core.distributor.models.entities.ConfigurationScope;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConfigurationScopeMapper {
    ConfigurationScopeMapper INSTANCE = Mappers.getMapper(ConfigurationScopeMapper.class);
    ConfigurationScopeDTO toDTO(ConfigurationScope entity);
    ConfigurationScope toEntity(ConfigurationScopeDTO dto);
}
