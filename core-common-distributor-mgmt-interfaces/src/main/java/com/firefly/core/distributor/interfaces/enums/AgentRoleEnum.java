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
 * Enumeration representing different roles that agents can have within a distributor agency.
 */
public enum AgentRoleEnum {
    /**
     * Administrator role with full access to all agency operations
     */
    ADMINISTRATOR,

    /**
     * Manager role with oversight of teams and operations
     */
    MANAGER,

    /**
     * Supervisor role with direct oversight of agents
     */
    SUPERVISOR,

    /**
     * Standard agent role for operational activities
     */
    AGENT,

    /**
     * Analyst role for data analysis and reporting
     */
    ANALYST,

    /**
     * Support role for customer and operational support
     */
    SUPPORT,

    /**
     * Viewer role with read-only access
     */
    VIEWER
}
