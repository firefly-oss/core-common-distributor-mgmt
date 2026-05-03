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
import com.firefly.core.distributor.interfaces.dtos.LendingContractDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service for managing lending contracts.
 */
public interface LendingContractService {

    /**
     * Create a new lending contract.
     *
     * @param lendingContractDTO the lending contract to create
     * @return the created lending contract
     */
    Mono<LendingContractDTO> createLendingContract(LendingContractDTO lendingContractDTO);

    /**
     * Update an existing lending contract.
     *
     * @param id the ID of the lending contract to update
     * @param lendingContractDTO the lending contract data to update
     * @return the updated lending contract
     */
    Mono<LendingContractDTO> updateLendingContract(UUID id, LendingContractDTO lendingContractDTO);

    /**
     * Delete a lending contract.
     *
     * @param id the ID of the lending contract to delete
     * @return a Mono completing when the operation is done
     */
    Mono<Void> deleteLendingContract(UUID id);

    /**
     * Get a lending contract by ID.
     *
     * @param id the ID of the lending contract to retrieve
     * @return the lending contract
     */
    Mono<LendingContractDTO> getLendingContractById(UUID id);

    /**
     * Get a lending contract by contract ID.
     *
     * @param contractId the contract ID of the lending contract to retrieve
     * @return the lending contract
     */
    Mono<LendingContractDTO> getLendingContractByContractId(UUID contractId);

    /**
     * Get all lending contracts for a distributor.
     *
     * @param distributorId the ID of the distributor
     * @return a Flux of lending contracts
     */
    Flux<LendingContractDTO> getLendingContractsByDistributorId(UUID distributorId);

    /**
     * Get all lending contracts for a product.
     *
     * @param productId the ID of the product
     * @return a Flux of lending contracts
     */
    Flux<LendingContractDTO> getLendingContractsByProductId(UUID productId);

    /**
     * Get all lending contracts for a party.
     *
     * @param partyId the ID of the party
     * @return a Flux of lending contracts
     */
    Flux<LendingContractDTO> getLendingContractsByPartyId(UUID partyId);

    /**
     * Get all lending contracts with a specific status.
     *
     * @param status the status
     * @return a Flux of lending contracts
     */
    Flux<LendingContractDTO> getLendingContractsByStatus(String status);

    /**
     * Approve a lending contract.
     *
     * @param id the ID of the lending contract to approve
     * @param approvedBy the ID of the user approving the contract
     * @return the approved lending contract
     */
    Mono<LendingContractDTO> approveLendingContract(UUID id, UUID approvedBy);

    /**
     * Filter lending contracts based on criteria.
     *
     * @param filterRequest the filter request containing criteria and pagination
     * @return a PaginationResponse with the filtered lending contracts
     */
    Mono<PaginationResponse<LendingContractDTO>> filterLendingContracts(FilterRequest<LendingContractDTO> filterRequest);
}