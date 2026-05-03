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

import com.firefly.core.distributor.core.mappers.ProductCategoryMapper;
import com.firefly.core.distributor.core.services.ProductCategoryService;
import com.firefly.core.distributor.interfaces.dtos.ProductCategoryDTO;
import com.firefly.core.distributor.models.entities.ProductCategory;
import com.firefly.core.distributor.models.repositories.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the ProductCategoryService interface.
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public Flux<ProductCategoryDTO> getAllProductCategories() {
        return productCategoryRepository.findAll()
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Flux<ProductCategoryDTO> getActiveProductCategories() {
        return productCategoryRepository.findByIsActiveTrue()
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Mono<ProductCategoryDTO> getProductCategoryById(UUID id) {
        return productCategoryRepository.findById(id)
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Mono<ProductCategoryDTO> getProductCategoryByCode(String code) {
        return productCategoryRepository.findByCode(code)
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Mono<ProductCategoryDTO> createProductCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        productCategory.setCreatedAt(LocalDateTime.now());
        productCategory.setIsActive(true);
        
        return productCategoryRepository.save(productCategory)
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Mono<ProductCategoryDTO> updateProductCategory(UUID id, ProductCategoryDTO productCategoryDTO) {
        return productCategoryRepository.findById(id)
                .flatMap(existingCategory -> {
                    ProductCategory updatedCategory = productCategoryMapper.toEntity(productCategoryDTO);
                    updatedCategory.setId(id);
                    updatedCategory.setCreatedAt(existingCategory.getCreatedAt());
                    updatedCategory.setCreatedBy(existingCategory.getCreatedBy());
                    updatedCategory.setUpdatedAt(LocalDateTime.now());
                    
                    return productCategoryRepository.save(updatedCategory);
                })
                .map(productCategoryMapper::toDto);
    }

    @Override
    public Mono<Void> deleteProductCategory(UUID id) {
        return productCategoryRepository.deleteById(id);
    }
}