/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.models.repositories;

import io.fireflysoftware.core.distributor.models.entities.DistributorProductCatalog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface DistributorProductCatalogRepository extends BaseRepository<DistributorProductCatalog, UUID> {
}
