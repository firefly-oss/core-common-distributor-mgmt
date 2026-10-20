/*
 * Copyright 2025 Firefly Software Foundation
 */

package io.fireflysoftware.core.distributor.models.repositories;

import io.fireflysoftware.core.distributor.models.entities.DistributorConfiguration;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface DistributorConfigurationRepository extends BaseRepository<DistributorConfiguration, UUID> {
}
