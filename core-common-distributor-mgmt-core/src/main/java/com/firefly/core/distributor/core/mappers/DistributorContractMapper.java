/*
 * Copyright 2025 Firefly Software Foundation
 */

package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.DistributorContractDTO;
import com.firefly.core.distributor.models.entities.DistributorContract;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DistributorContractMapper {
    DistributorContractMapper INSTANCE = Mappers.getMapper(DistributorContractMapper.class);
    DistributorContractDTO toDTO(DistributorContract entity);
    DistributorContract toEntity(DistributorContractDTO dto);
}
