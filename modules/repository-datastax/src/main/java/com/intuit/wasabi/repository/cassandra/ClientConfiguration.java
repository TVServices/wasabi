/*******************************************************************************
 * Copyright 2016 Intuit
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
 *******************************************************************************/
package com.intuit.wasabi.repository.cassandra;

import com.datastax.driver.core.ConsistencyLevel;
import com.intuit.wasabi.cassandra.datastax.CassandraDriver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.intuit.autumn.utils.PropertyFactory.create;
import static com.intuit.autumn.utils.PropertyFactory.getProperty;
import static java.lang.Boolean.FALSE;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Property-driven CassandraDriver configuration
 */
public class ClientConfiguration implements CassandraDriver.Configuration {

    private Properties properties;

    /**
     * Create an instance bound to the property context
     *
     * @param propertyContext property context
     */
    public ClientConfiguration(final String propertyContext) {
        super();

        properties = create(checkNotNull(propertyContext));
    }

    /*
     * @see com.intuit.wasabi.cassandra.CassandraDriver.Configuration#getNodeHosts()
     */
    @Override
    public List<String> getNodeHosts() {
        return Arrays.stream(getProperty("nodeHosts", properties).split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public int getPort() {
        return parseInt(getProperty("port", properties, "9160"));
    }

    @Override
    public Boolean useSSL() {
        return Boolean.valueOf(getProperty("useSSL", properties, FALSE.toString()));
    }

    @Override
    public String getSSLTrustStore() {
        return getProperty("trustStore", properties);
    }

    @Override
    public String getSSLTrustStorePassword() {
        return getProperty("trustStorePassword", properties);
    }

    @Override
    public String getKeyspaceName() {
        return getProperty("keyspaceName", properties);
    }

    @Override
    public int getKeyspaceReplicationFactor() {
        return parseInt(getProperty("keyspaceReplicationFactor", properties));
    }

    @Override
    public String getKeyspaceStrategyClass() {
        return getProperty("keyspaceStrategyClass", properties);
    }

    @Override
    public int getMaxConnectionsPerHost() {
        return parseInt(getProperty("maxConnectionsPerHost", properties, "10"));
    }

    @Override
    public ConsistencyLevel getDefaultReadConsistency() {
        String value = getProperty("defaultReadConsistency", properties);

        return !isBlank(value) ? ConsistencyLevel.valueOf(value) : ConsistencyLevel.LOCAL_QUORUM;
    }

    @Override
    public ConsistencyLevel getDefaultWriteConsistency() {
        String value = getProperty("defaultWriteConsistency", properties);

        return !isBlank(value) ? ConsistencyLevel.valueOf(value) : ConsistencyLevel.LOCAL_QUORUM;
    }

    /**
     * Returns the string reflecting the values of the replication factor for each mentioned data center.
     * Format of the value is DataCenter1:ReplicationFactor,DataCenter2:ReplicationFactor,....
     */
    @Override
    public String getNetworkTopologyReplicationValues() {
        return getProperty("networkTopologyReplicationValues", properties);
    }

    public Optional<String> getTokenAwareLoadBalancingLocalDC() {
        return Optional.ofNullable(getProperty("tokenAwareLoadBalancingLocalDC", properties));
    }

    public Integer getTokenAwareLoadBalancingUsedHostsPerRemoteDc() {
        return Integer.valueOf(getProperty("tokenAwareLoadBalancingUsedHostsPerRemoteDc", properties, "1"));
    }
}
