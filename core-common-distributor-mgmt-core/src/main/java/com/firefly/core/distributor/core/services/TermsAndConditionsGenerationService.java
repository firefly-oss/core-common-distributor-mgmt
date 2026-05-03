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


package com.firefly.core.distributor.core.services;

import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

/**
 * Service interface for generating terms and conditions from templates.
 */
public interface TermsAndConditionsGenerationService {

    /**
     * Generate terms and conditions from a template with provided variables.
     *
     * @param templateId the template ID to use
     * @param distributorId the distributor ID
     * @param variables the variables to substitute in the template
     * @return a reactive {@code Mono} emitting the generated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> generateFromTemplate(UUID templateId, UUID distributorId, Map<String, Object> variables);

    /**
     * Generate terms and conditions from a template DTO with provided variables.
     *
     * @param template the template to use
     * @param distributorId the distributor ID
     * @param variables the variables to substitute in the template
     * @return a reactive {@code Mono} emitting the generated terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> generateFromTemplate(TermsAndConditionsTemplateDTO template, UUID distributorId, Map<String, Object> variables);

    /**
     * Validate template variables against the template's variable definitions.
     *
     * @param template the template containing variable definitions
     * @param variables the variables to validate
     * @return a reactive {@code Mono} emitting true if variables are valid
     */
    Mono<Boolean> validateVariables(TermsAndConditionsTemplateDTO template, Map<String, Object> variables);

    /**
     * Process template content by substituting variables.
     *
     * @param templateContent the template content with placeholders
     * @param variables the variables to substitute
     * @return the processed content with variables substituted
     */
    String processTemplate(String templateContent, Map<String, Object> variables);

    /**
     * Get default variables for a distributor (like distributor name, tax ID, etc.).
     *
     * @param distributorId the distributor ID
     * @return a reactive {@code Mono} emitting the default variables
     */
    Mono<Map<String, Object>> getDefaultVariablesForDistributor(UUID distributorId);

    /**
     * Preview generated content without creating a terms and conditions record.
     *
     * @param templateId the template ID
     * @param variables the variables to substitute
     * @return a reactive {@code Mono} emitting the preview content
     */
    Mono<String> previewGeneration(UUID templateId, Map<String, Object> variables);

    /**
     * Auto-renew terms and conditions based on template settings.
     *
     * @param termsAndConditionsId the existing terms and conditions ID
     * @return a reactive {@code Mono} emitting the renewed terms and conditions
     */
    Mono<DistributorTermsAndConditionsDTO> autoRenewTermsAndConditions(UUID termsAndConditionsId);

    /**
     * Check if terms and conditions need renewal.
     *
     * @param termsAndConditionsId the terms and conditions ID
     * @return a reactive {@code Mono} emitting true if renewal is needed
     */
    Mono<Boolean> needsRenewal(UUID termsAndConditionsId);
}
