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

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.distributor.interfaces.dtos.DistributorContractDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing distributor contracts.
 */
public interface DistributorContractService {
    /**
     * Filters contracts based on the given criteria for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor owning the contracts
     * @param filterRequest the request object containing filtering criteria
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list
     */
    Mono<PaginationResponse<DistributorContractDTO>> filterContracts(UUID distributorId, FilterRequest<DistributorContractDTO> filterRequest);
    
    /**
     * Creates a new contract for a specific distributor.
     *
     * @param distributorId the unique identifier of the distributor
     * @param dto the DTO object containing details
     * @return a Mono that emits the created DistributorContractDTO object
     */
    Mono<DistributorContractDTO> createContract(UUID distributorId, DistributorContractDTO dto);
    
    /**
     * Updates an existing contract.
     *
     * @param distributorId the unique identifier of the distributor
     * @param contractId the unique identifier of the contract
     * @param dto the data transfer object containing updated details
     * @return a reactive Mono containing the updated DistributorContractDTO
     */
    Mono<DistributorContractDTO> updateContract(UUID distributorId, UUID contractId, DistributorContractDTO dto);
    
    /**
     * Deletes a contract by its unique ID.
     *
     * @param distributorId the unique identifier of the distributor
     * @param contractId the unique identifier of the contract
     * @return a Mono that completes when successfully deleted
     */
    Mono<Void> deleteContract(UUID distributorId, UUID contractId);
    
    /**
     * Retrieves a contract by its unique identifier.
     *
     * @param distributorId the unique identifier of the distributor
     * @param contractId the unique identifier of the contract
     * @return a Mono emitting the DistributorContractDTO if found
     */
    Mono<DistributorContractDTO> getContractById(UUID distributorId, UUID contractId);
}
