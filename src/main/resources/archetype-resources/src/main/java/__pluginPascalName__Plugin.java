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
package ${package};

import ${package}.config.${pluginPascalName}Config;
import io.github.ktestify.plugin.KtestifyPlugin;
import io.github.ktestify.plugin.PluginContext;
import lombok.extern.slf4j.Slf4j;

/**
 * ktestify plugin for ${pluginPascalName} transport.
 *
 * <p>Registered via {@code META-INF/services/io.github.ktestify.plugin.KtestifyPlugin} so it is discovered
 * automatically by {@link java.util.ServiceLoader}.
 *
 * <h2>Lifecycle</h2>
 *
 * <ol>
 *   <li>{@link #initialize(PluginContext)} — validates config at startup; logs a warning if no credentials are found.
 *   <li>Cucumber scenarios run — step definitions are discovered via {@link #getGluePackage()}.
 *   <li>{@link #shutdown()} — releases shared resources (if any).
 * </ol>
 *
 * @since 1.0.0
 * @see ${pluginPascalName}Config
 */
@Slf4j
public class ${pluginPascalName}Plugin implements KtestifyPlugin {

    private static final String PLUGIN_ID = "${pluginKebabId}";
    private static final String VERSION = "1.0-SNAPSHOT";

    @Override
    public String getId() {
        return PLUGIN_ID;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getAuthorName() {
        return "${authorName}";
    }

    @Override
    public String getAuthorEmail() {
        return "${authorEmail}";
    }

    @Override
    public String getGluePackage() {
        return "${package}.steps";
    }

    @Override
    public void initialize(PluginContext context) {
        log.info("Initializing {} plugin v{}…", getId(), getVersion());

        ${pluginPascalName}Config cfg = ${pluginPascalName}Config.from(context.getConfig().getRaw());

        if (!cfg.hasConnectionString()) {
            log.warn(
                    "[{}] No connection string configured. "
                            + "Set KTESTIFY_${pluginShortName.toUpperCase()}_CONNECTION_STRING or provide it per-step via DataTable.",
                    getId());
        }

        log.info("{} plugin initialized successfully.", getId());
    }

    @Override
    public void shutdown() {
        log.debug("{} plugin shut down.", getId());
    }
}

