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

import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import com.firefly.core.distributor.models.entities.TermsAndConditionsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for converting between TermsAndConditionsTemplate entity and TermsAndConditionsTemplateDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TermsAndConditionsTemplateMapper {

    /**
     * Converts a TermsAndConditionsTemplate entity to a TermsAndConditionsTemplateDTO.
     *
     * @param entity the TermsAndConditionsTemplate entity to convert
     * @return the corresponding TermsAndConditionsTemplateDTO
     */
    TermsAndConditionsTemplateDTO toDTO(TermsAndConditionsTemplate entity);

    /**
     * Converts a TermsAndConditionsTemplateDTO to a TermsAndConditionsTemplate entity.
     *
     * @param dto the TermsAndConditionsTemplateDTO to convert
     * @return the corresponding TermsAndConditionsTemplate entity
     */
    TermsAndConditionsTemplate toEntity(TermsAndConditionsTemplateDTO dto);
}
