/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.DistributorAgentDTO;
import io.fireflysoftware.core.distributor.models.entities.DistributorAgent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorAgentMapper {
    DistributorAgentMapper INSTANCE = Mappers.getMapper(DistributorAgentMapper.class);
    DistributorAgentDTO toDTO(DistributorAgent entity);
    DistributorAgent toEntity(DistributorAgentDTO dto);
}
