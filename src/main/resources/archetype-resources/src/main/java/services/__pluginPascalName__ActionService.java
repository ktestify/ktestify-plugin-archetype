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
package ${package}.services;

import ${package}.config.${pluginPascalName}Config;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for sending / uploading data via ${pluginPascalName}.
 *
 * @since 1.0.0
 */
@Slf4j
public class ${pluginPascalName}ActionService {

    private final ${pluginPascalName}Config globalConfig;

    public ${pluginPascalName}ActionService(${pluginPascalName}Config globalConfig) {
        this.globalConfig = globalConfig;
    }

    public void send(Ktestify${pluginPascalName}Entity resource, String recordId, String sourceFile) {
        log.info("Sending '{}' → record '{}' in resource '{}'…", sourceFile, recordId, resource.getResourceName());
        throw new UnsupportedOperationException("TODO: implement ${pluginPascalName}ActionService.send()");
    }

    private String resolveConnectionString(Ktestify${pluginPascalName}Entity resource) {
        String connStr = resource.getConnectionString();
        if (connStr != null && !connStr.isBlank()) return connStr;
        if (globalConfig.hasConnectionString()) return globalConfig.getConnectionString();
        throw new io.github.ktestify.exceptions.PluginException(
                "${pluginPascalName}: no connection string configured for resource '"
                        + resource.getResourceName() + "'. "
                        + "Set KTESTIFY_${pluginShortName.toUpperCase()}_CONNECTION_STRING or provide it in the step DataTable.");
    }
}

