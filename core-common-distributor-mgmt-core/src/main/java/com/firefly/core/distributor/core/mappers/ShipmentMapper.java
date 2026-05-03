/*
 * Copyright 2025 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.distributor.core.mappers;

import com.firefly.core.distributor.interfaces.dtos.ShipmentDTO;
import com.firefly.core.distributor.models.entities.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Shipment entity and ShipmentDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, LendingContractMapper.class})
public abstract class ShipmentMapper {

    /**
     * Convert a Shipment entity to a ShipmentDTO.
     *
     * @param shipment the Shipment entity
     * @return the ShipmentDTO
     */
    public abstract ShipmentDTO toDto(Shipment shipment);

    /**
     * Convert a ShipmentDTO to a Shipment entity.
     *
     * @param shipmentDTO the ShipmentDTO
     * @return the Shipment entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Shipment toEntity(ShipmentDTO shipmentDTO);
}