#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2026 ${authorName} (${authorEmail})
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ${package}.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;

/**
 * Typed configuration for the ${pluginPascalName} plugin.
 *
 * <p>Reads the {@code ktestify.plugins.${pluginKebabId}} HOCON subtree. All values can be overridden via environment
 * variables (see {@code reference.conf} in this module).
 *
 * @since 1.0.0
 */
@Getter
public final class ${pluginPascalName}Config {

    private static final String CONFIG_PATH = "ktestify.plugins.${pluginKebabId}";

    private final String connectionString;
    private final long readTimeoutMs;
    private final long pollIntervalMs;

    private ${pluginPascalName}Config(Config cfg) {
        this.connectionString = cfg.getString("connection-string");
        this.readTimeoutMs = cfg.getDuration("read-timeout").toMillis();
        this.pollIntervalMs = cfg.getDuration("poll-interval").toMillis();
    }

    public static ${pluginPascalName}Config from(Config root) {
        Config merged = root.withFallback(ConfigFactory.load()).resolve();
        return new ${pluginPascalName}Config(merged.getConfig(CONFIG_PATH));
    }

    public boolean hasConnectionString() {
        return connectionString != null && !connectionString.isBlank();
    }
}

