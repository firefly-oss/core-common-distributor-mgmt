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

import com.firefly.core.distributor.models.entities.Shipment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository for managing Shipment entities.
 */
@Repository
public interface ShipmentRepository extends BaseRepository<Shipment, UUID> {

    /**
     * Find all shipments by lending contract ID.
     *
     * @param lendingContractId the lending contract ID
     * @return a Flux of shipments
     */
    Flux<Shipment> findByLendingContractId(UUID lendingContractId);

    /**
     * Find all shipments by product ID.
     *
     * @param productId the product ID
     * @return a Flux of shipments
     */
    Flux<Shipment> findByProductId(UUID productId);

    /**
     * Find a shipment by tracking number.
     *
     * @param trackingNumber the tracking number
     * @return a Mono of shipment
     */
    Mono<Shipment> findByTrackingNumber(String trackingNumber);

    /**
     * Find all shipments by status.
     *
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByStatus(String status);

    /**
     * Find all shipments by lending contract ID and status.
     *
     * @param lendingContractId the lending contract ID
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByLendingContractIdAndStatus(UUID lendingContractId, String status);

    /**
     * Find all shipments by product ID and status.
     *
     * @param productId the product ID
     * @param status the status
     * @return a Flux of shipments
     */
    Flux<Shipment> findByProductIdAndStatus(UUID productId, String status);

    /**
     * Find all shipments by carrier.
     *
     * @param carrier the carrier
     * @return a Flux of shipments
     */
    Flux<Shipment> findByCarrier(String carrier);

    /**
     * Find all shipments shipped after a specific date.
     *
     * @param shippingDate the shipping date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE shipping_date >= :shippingDate")
    Flux<Shipment> findByShippingDateAfter(String shippingDate);

    /**
     * Find all shipments with estimated delivery date after a specific date.
     *
     * @param estimatedDeliveryDate the estimated delivery date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE estimated_delivery_date >= :estimatedDeliveryDate")
    Flux<Shipment> findByEstimatedDeliveryDateAfter(String estimatedDeliveryDate);

    /**
     * Find all shipments with actual delivery date after a specific date.
     *
     * @param actualDeliveryDate the actual delivery date
     * @return a Flux of shipments
     */
    @Query("SELECT * FROM shipment WHERE actual_delivery_date >= :actualDeliveryDate")
    Flux<Shipment> findByActualDeliveryDateAfter(String actualDeliveryDate);
}