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

import com.firefly.core.distributor.interfaces.dtos.ProductDTO;
import com.firefly.core.distributor.models.entities.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for converting between Product entity and ProductDTO.
 */
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Convert a Product entity to a ProductDTO.
     *
     * @param product the Product entity
     * @return the ProductDTO
     */
    public abstract ProductDTO toDTO(Product product);

    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(ProductDTO dto, @MappingTarget Product entity);


    /**
     * Convert a ProductDTO to a Product entity.
     *
     * @param productDTO the ProductDTO
     * @return the Product entity
     */
    public abstract Product toEntity(ProductDTO productDTO);

    /**
     * Convert a JSON string to a JsonNode.
     *
     * @param json the JSON string
     * @return the JsonNode
     */
    @Named("stringToJsonNode")
    protected JsonNode stringToJsonNode(String json) {
        if (json == null || json.isEmpty()) {
            return objectMapper.createObjectNode();
        }
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode();
        }
    }

    /**
     * Convert a JsonNode to a JSON string.
     *
     * @param jsonNode the JsonNode
     * @return the JSON string
     */
    @Named("jsonNodeToString")
    protected String jsonNodeToString(JsonNode jsonNode) {
        if (jsonNode == null) {
            return "{}";
        }
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }


}