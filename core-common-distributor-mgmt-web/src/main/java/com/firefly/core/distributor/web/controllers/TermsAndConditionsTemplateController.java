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


package com.firefly.core.distributor.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.core.services.TermsAndConditionsGenerationService;
import com.firefly.core.distributor.core.services.TermsAndConditionsTemplateService;
import com.firefly.core.distributor.interfaces.dtos.TermsAndConditionsTemplateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

/**
 * REST controller for managing terms and conditions templates.
 */
@RestController
@RequestMapping("/api/v1/terms-and-conditions-templates")
@Tag(name = "Terms and Conditions Templates", description = "API for managing terms and conditions templates")
@RequiredArgsConstructor
public class TermsAndConditionsTemplateController {

    private final TermsAndConditionsTemplateService templateService;
    private final TermsAndConditionsGenerationService generationService;

    @Operation(summary = "Filter terms and conditions templates", description = "Returns a paginated list of templates based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved templates",
                content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<TermsAndConditionsTemplateDTO>>> filterTemplates(
            @Valid @RequestBody FilterRequest<TermsAndConditionsTemplateDTO> filterRequest) {
        return templateService.filterTermsAndConditionsTemplates(filterRequest)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Create a new template", description = "Creates a new terms and conditions template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Template successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid template data provided", 
                content = @Content),
        @ApiResponse(responseCode = "409", description = "Template name already exists", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> createTemplate(
            @Valid @RequestBody TermsAndConditionsTemplateDTO templateDTO) {
        
        return templateService.templateNameExists(templateDTO.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Template name already exists"));
                    }
                    return templateService.createTemplate(templateDTO);
                })
                .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result));
    }

    @Operation(summary = "Get template by ID", description = "Returns a template based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved template",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> getTemplateById(
            @Parameter(description = "ID of the template", required = true)
            @PathVariable UUID templateId) {
        return templateService.getTemplateById(templateId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update template", description = "Updates an existing template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Template successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid template data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "409", description = "Template name already exists", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> updateTemplate(
            @Parameter(description = "ID of the template to update", required = true)
            @PathVariable UUID templateId,
            @Valid @RequestBody TermsAndConditionsTemplateDTO templateDTO) {
        
        return templateService.templateNameExistsForDifferentTemplate(templateDTO.getName(), templateId)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Template name already exists"));
                    }
                    return templateService.updateTemplate(templateId, templateDTO);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete template", description = "Deletes a template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Template successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{templateId}")
    public Mono<ResponseEntity<Void>> deleteTemplate(
            @Parameter(description = "ID of the template to delete", required = true)
            @PathVariable UUID templateId) {
        return templateService.deleteTemplate(templateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @Operation(summary = "Get all active templates", description = "Returns all active templates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active templates",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<TermsAndConditionsTemplateDTO>> getActiveTemplates() {
        return ResponseEntity.ok(templateService.getActiveTemplates());
    }

    @Operation(summary = "Get templates by category", description = "Returns templates in a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved templates",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<TermsAndConditionsTemplateDTO>> getTemplatesByCategory(
            @Parameter(description = "Category of templates", required = true)
            @PathVariable String category) {
        return ResponseEntity.ok(templateService.getTemplatesByCategory(category));
    }

    @Operation(summary = "Get active templates by category", description = "Returns active templates in a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved active templates",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/category/{category}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<TermsAndConditionsTemplateDTO>> getActiveTemplatesByCategory(
            @Parameter(description = "Category of templates", required = true)
            @PathVariable String category) {
        return ResponseEntity.ok(templateService.getActiveTemplatesByCategory(category));
    }

    @Operation(summary = "Get template by name", description = "Returns a template based on its name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved template",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> getTemplateByName(
            @Parameter(description = "Name of the template", required = true)
            @PathVariable String name) {
        return templateService.getTemplateByName(name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get default templates", description = "Returns all default templates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved default templates",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<TermsAndConditionsTemplateDTO>> getDefaultTemplates() {
        return ResponseEntity.ok(templateService.getDefaultTemplates());
    }

    @Operation(summary = "Get default template by category", description = "Returns the default template for a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved default template",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Default template not found for category", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/default/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> getDefaultTemplateByCategory(
            @Parameter(description = "Category", required = true)
            @PathVariable String category) {
        return templateService.getDefaultTemplateByCategory(category)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Preview template generation", description = "Previews the generated content from a template with provided variables")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully generated preview",
                content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Invalid template or variables provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/{templateId}/preview", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> previewGeneration(
            @Parameter(description = "ID of the template", required = true)
            @PathVariable UUID templateId,
            @RequestBody Map<String, Object> variables) {
        
        return generationService.previewGeneration(templateId, variables)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Activate template", description = "Activates a template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Template successfully activated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{templateId}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> activateTemplate(
            @Parameter(description = "ID of the template to activate", required = true)
            @PathVariable UUID templateId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return templateService.activateTemplate(templateId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deactivate template", description = "Deactivates a template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Template successfully deactivated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{templateId}/deactivate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> deactivateTemplate(
            @Parameter(description = "ID of the template to deactivate", required = true)
            @PathVariable UUID templateId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return templateService.deactivateTemplate(templateId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Set template as default", description = "Sets a template as default for its category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Template successfully set as default",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{templateId}/set-default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> setAsDefault(
            @Parameter(description = "ID of the template to set as default", required = true)
            @PathVariable UUID templateId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return templateService.setAsDefault(templateId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove default status", description = "Removes default status from a template")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Default status successfully removed",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TermsAndConditionsTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PatchMapping(value = "/{templateId}/remove-default", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TermsAndConditionsTemplateDTO>> removeDefault(
            @Parameter(description = "ID of the template to remove default status from", required = true)
            @PathVariable UUID templateId,
            @Parameter(description = "ID of the user performing the update")
            @RequestParam(required = false) UUID updatedBy) {
        return templateService.removeDefault(templateId, updatedBy)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
