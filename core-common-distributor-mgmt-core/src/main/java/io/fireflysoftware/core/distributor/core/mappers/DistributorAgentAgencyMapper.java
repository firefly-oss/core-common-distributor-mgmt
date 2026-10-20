/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.core.mappers;

import io.fireflysoftware.core.distributor.interfaces.dtos.DistributorAgentAgencyDTO;
import io.fireflysoftware.core.distributor.models.entities.DistributorAgentAgency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorAgentAgencyMapper {
    DistributorAgentAgencyMapper INSTANCE = Mappers.getMapper(DistributorAgentAgencyMapper.class);
    DistributorAgentAgencyDTO toDTO(DistributorAgentAgency entity);
    DistributorAgentAgency toEntity(DistributorAgentAgencyDTO dto);
}
