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


package com.firefly.core.distributor.models.repositories;

import com.firefly.core.distributor.models.entities.LendingContract;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing LendingContract entities.
 */
@Repository
public interface LendingContractRepository extends BaseRepository<LendingContract, UUID> {

    /**
     * Find all lending contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByDistributorId(UUID distributorId);

    /**
     * Find all lending contracts by product ID.
     *
     * @param productId the product ID
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByProductId(UUID productId);

    /**
     * Find all lending contracts by party ID.
     *
     * @param partyId the party ID
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByPartyId(UUID partyId);

    /**
     * Find a lending contract by contract ID.
     *
     * @param contractId the contract ID
     * @return a Mono of lending contract
     */
    Mono<LendingContract> findByContractId(UUID contractId);

    /**
     * Find all active lending contracts by distributor ID.
     *
     * @param distributorId the distributor ID
     * @return a Flux of active lending contracts
     */
    Flux<LendingContract> findByDistributorIdAndIsActiveTrue(UUID distributorId);

    /**
     * Find all lending contracts by status.
     *
     * @param status the status
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByStatus(String status);

    /**
     * Find all lending contracts by distributor ID and status.
     *
     * @param distributorId the distributor ID
     * @param status the status
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByDistributorIdAndStatus(UUID distributorId, String status);

    /**
     * Find all lending contracts by product ID and status.
     *
     * @param productId the product ID
     * @param status the status
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByProductIdAndStatus(UUID productId, String status);

    /**
     * Find all lending contracts by party ID and status.
     *
     * @param partyId the party ID
     * @param status the status
     * @return a Flux of lending contracts
     */
    Flux<LendingContract> findByPartyIdAndStatus(UUID partyId, String status);

    /**
     * Find all lending contracts approved after a specific date.
     *
     * @param approvalDate the approval date
     * @return a Flux of lending contracts
     */
    @Query("SELECT * FROM lending_contract WHERE approval_date >= :approvalDate")
    Flux<LendingContract> findByApprovalDateAfter(String approvalDate);
}