/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorAgentAgencyDTO;
import com.firefly.core.distributor.models.entities.DistributorAgentAgency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorAgentAgencyMapper {
    DistributorAgentAgencyMapper INSTANCE = Mappers.getMapper(DistributorAgentAgencyMapper.class);
    DistributorAgentAgencyDTO toDTO(DistributorAgentAgency entity);
    DistributorAgentAgency toEntity(DistributorAgentAgencyDTO dto);
}
