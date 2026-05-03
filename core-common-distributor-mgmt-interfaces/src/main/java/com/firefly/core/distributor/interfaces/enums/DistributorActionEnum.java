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


package com.firefly.core.distributor.interfaces.enums;

/**
 * Enum representing the possible actions that can be performed on a distributor entity.
 * Used for audit logging purposes.
 */
public enum DistributorActionEnum {
    /**
     * Indicates that a distributor entity was created
     */
    CREATED,
    
    /**
     * Indicates that a distributor entity was updated
     */
    UPDATED,
    
    /**
     * Indicates that a distributor entity was terminated
     */
    TERMINATED
}