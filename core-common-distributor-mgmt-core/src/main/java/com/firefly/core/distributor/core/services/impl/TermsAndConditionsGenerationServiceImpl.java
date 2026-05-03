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


package com.firefly.core.distributor.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firefly.core.distributor.core.services.DistributorService;
import com.firefly.core.distributor.core.services.DistributorTermsAndConditionsService;
import com.firefly.core.distributor.core.services.TermsAndConditionsGenerationService;
import com.firefly.core.distributor.core.services.TermsAndConditionsTemplateService;
import com.firefly.core.distributor.interfaces.dtos.DistributorDTO;
import com.firefly.core.distributor.interfaces.dtos.DistributorTermsAndConditionsDTO;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

/**
 * Implementation of the TermsAndConditionsGenerationService interface.
 */
@Service
public class TermsAndConditionsGenerationServiceImpl implements TermsAndConditionsGenerationService {

    @Autowired
    private TermsAndConditionsTemplateService templateService;

    @Autowired
    private DistributorTermsAndConditionsService distributorTermsAndConditionsService;

    @Autowired
    private DistributorService distributorService;

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{([^}]+)\\}\\}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Mono<DistributorTermsAndConditionsDTO> generateFromTemplate(UUID templateId, UUID distributorId, Map<String, Object> variables) {
        return templateService.getTemplateById(templateId)
                .flatMap(template -> generateFromTemplate(template, distributorId, variables));
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> generateFromTemplate(TermsAndConditionsTemplateDTO template, UUID distributorId, Map<String, Object> variables) {
        return validateVariables(template, variables)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new IllegalArgumentException("Invalid variables provided for template"));
                    }
                    
                    return getDefaultVariablesForDistributor(distributorId)
                            .map(defaultVars -> {
                                Map<String, Object> allVariables = new HashMap<>(defaultVars);
                                allVariables.putAll(variables);
                                return allVariables;
                            })
                            .map(allVars -> {
                                String processedContent = processTemplate(template.getTemplateContent(), allVars);
                                
                                return DistributorTermsAndConditionsDTO.builder()
                                        .distributorId(distributorId)
                                        .templateId(template.getId())
                                        .title(template.getName())
                                        .content(processedContent)
                                        .version(template.getVersion())
                                        .effectiveDate(LocalDateTime.now())
                                        .status("DRAFT")
                                        .isActive(true)
                                        .build();
                            })
                            .flatMap(distributorTermsAndConditionsService::createDistributorTermsAndConditions);
                });
    }

    @Override
    public Mono<Boolean> validateVariables(TermsAndConditionsTemplateDTO template, Map<String, Object> variables) {
        return Mono.fromCallable(() -> {
            String variablesJson = template.getVariables();
            if (variablesJson == null || variablesJson.trim().isEmpty()) {
                return true; // No validation needed if no variables defined
            }
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode variableDefinitions = objectMapper.readTree(variablesJson);
            if (variableDefinitions == null || variableDefinitions.isEmpty()) {
                return true; // No validation needed if no variables defined
            }

            // Check required variables
            variableDefinitions.fields().forEachRemaining(entry -> {
                String varName = entry.getKey();
                JsonNode varDef = entry.getValue();
                
                if (varDef.has("required") && varDef.get("required").asBoolean()) {
                    if (!variables.containsKey(varName) || variables.get(varName) == null) {
                        throw new IllegalArgumentException("Required variable '" + varName + "' is missing");
                    }
                }
                
                // Type validation could be added here
                if (variables.containsKey(varName)) {
                    Object value = variables.get(varName);
                    String expectedType = varDef.has("type") ? varDef.get("type").asText() : "string";
                    
                    if (!isValidType(value, expectedType)) {
                        throw new IllegalArgumentException("Variable '" + varName + "' has invalid type. Expected: " + expectedType);
                    }
                }
            });
            
            return true;
        }).onErrorReturn(false);
    }

    @Override
    public String processTemplate(String templateContent, Map<String, Object> variables) {
        if (templateContent == null || variables == null) {
            return templateContent;
        }

        String result = templateContent;
        Matcher matcher = VARIABLE_PATTERN.matcher(templateContent);
        
        while (matcher.find()) {
            String variableName = matcher.group(1);
            Object value = variables.get(variableName);
            
            if (value != null) {
                String replacement = formatValue(value);
                result = result.replace("{{" + variableName + "}}", replacement);
            }
        }
        
        return result;
    }

    @Override
    public Mono<Map<String, Object>> getDefaultVariablesForDistributor(UUID distributorId) {
        return distributorService.getDistributorById(distributorId)
                .map(this::extractDefaultVariables)
                .defaultIfEmpty(new HashMap<>());
    }

    @Override
    public Mono<String> previewGeneration(UUID templateId, Map<String, Object> variables) {
        return templateService.getTemplateById(templateId)
                .map(template -> processTemplate(template.getTemplateContent(), variables));
    }

    @Override
    public Mono<DistributorTermsAndConditionsDTO> autoRenewTermsAndConditions(UUID termsAndConditionsId) {
        return distributorTermsAndConditionsService.getDistributorTermsAndConditionsById(termsAndConditionsId)
                .flatMap(existingTerms -> {
                    if (existingTerms.getTemplateId() == null) {
                        return Mono.error(new IllegalStateException("Cannot auto-renew terms without template"));
                    }
                    
                    return templateService.getTemplateById(existingTerms.getTemplateId())
                            .flatMap(template -> {
                                if (!Boolean.TRUE.equals(template.getAutoRenewal())) {
                                    return Mono.error(new IllegalStateException("Template does not support auto-renewal"));
                                }
                                
                                Map<String, Object> variables = new HashMap<>();
                                variables.put("effectiveDate", LocalDateTime.now().format(DATE_FORMATTER));
                                
                                if (template.getRenewalPeriodMonths() != null) {
                                    LocalDateTime expirationDate = LocalDateTime.now().plusMonths(template.getRenewalPeriodMonths());
                                    variables.put("expirationDate", expirationDate.format(DATE_FORMATTER));
                                }
                                
                                return generateFromTemplate(template, existingTerms.getDistributorId(), variables);
                            });
                });
    }

    @Override
    public Mono<Boolean> needsRenewal(UUID termsAndConditionsId) {
        return distributorTermsAndConditionsService.getDistributorTermsAndConditionsById(termsAndConditionsId)
                .map(terms -> {
                    if (terms.getExpirationDate() == null) {
                        return false;
                    }
                    
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime renewalThreshold = terms.getExpirationDate().minusDays(30); // 30 days before expiration
                    
                    return now.isAfter(renewalThreshold);
                })
                .defaultIfEmpty(false);
    }

    private Map<String, Object> extractDefaultVariables(DistributorDTO distributor) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("distributorName", distributor.getName());
        variables.put("distributorDisplayName", distributor.getDisplayName());
        variables.put("distributorTaxId", distributor.getTaxId());
        variables.put("distributorEmail", distributor.getSupportEmail());
        variables.put("distributorAddress", distributor.getAddressLine());
        variables.put("distributorCity", distributor.getCity());
        variables.put("distributorState", distributor.getState());
        variables.put("distributorCountryId", distributor.getCountryId() != null ? distributor.getCountryId().toString() : "");
        variables.put("distributorPostalCode", distributor.getPostalCode());
        variables.put("distributorWebsite", distributor.getWebsiteUrl());
        variables.put("currentDate", LocalDateTime.now().format(DATE_FORMATTER));
        variables.put("currentDateTime", LocalDateTime.now().format(DATETIME_FORMATTER));
        return variables;
    }

    private boolean isValidType(Object value, String expectedType) {
        if (value == null) return true;
        
        switch (expectedType.toLowerCase()) {
            case "string":
                return value instanceof String;
            case "number":
                return value instanceof Number;
            case "boolean":
                return value instanceof Boolean;
            case "date":
                return value instanceof String; // Assuming dates are passed as strings
            default:
                return true; // Unknown types are considered valid
        }
    }

    private String formatValue(Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DATETIME_FORMATTER);
        }
        
        return value.toString();
    }
}
