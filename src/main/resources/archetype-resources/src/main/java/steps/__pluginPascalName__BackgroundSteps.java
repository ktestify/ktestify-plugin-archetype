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
package ${package}.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Cucumber {@code @Given} step definitions for ${pluginPascalName} setup.
 *
 * @since 1.0.0
 */
@Slf4j
public class ${pluginPascalName}BackgroundSteps {

    private final Shared${pluginPascalName}Resources shared;

    public ${pluginPascalName}BackgroundSteps(Shared${pluginPascalName}Resources shared) {
        this.shared = shared;
    }

    @Given("${pluginPascalName} resource")
    public void given${pluginPascalName}Resource(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);

        String resourceName = row.get("resourceName");
        String resourceAlias = row.get("resourceAlias");
        String connectionString = row.get("connectionString");

        if (resourceName == null || resourceName.isBlank()) {
            throw new IllegalArgumentException("DataTable column 'resourceName' is required for ${pluginPascalName}.");
        }

        Ktestify${pluginPascalName}Entity resource = Ktestify${pluginPascalName}Entity.builder()
                .resourceName(resourceName)
                .resourceAlias(resourceAlias)
                .connectionString(connectionString)
                .build();

        shared.resources.register(resourceName, resourceAlias, resource);
        log.info("Registered ${pluginPascalName} resource '{}' (alias: '{}').", resourceName, resourceAlias);
    }

    @Given("${pluginPascalName} resources")
    public void given${pluginPascalName}Resources(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String resourceName = row.get("resourceName");
            String resourceAlias = row.get("resourceAlias");
            String connectionString = row.get("connectionString");

            if (resourceName == null || resourceName.isBlank()) {
                throw new IllegalArgumentException(
                        "DataTable column 'resourceName' is required for each ${pluginPascalName} row.");
            }

            Ktestify${pluginPascalName}Entity resource = Ktestify${pluginPascalName}Entity.builder()
                    .resourceName(resourceName)
                    .resourceAlias(resourceAlias)
                    .connectionString(connectionString)
                    .build();

            shared.resources.register(resourceName, resourceAlias, resource);
            log.info("Registered ${pluginPascalName} resource '{}' (alias: '{}').", resourceName, resourceAlias);
        }
    }

    @Given("${pluginPascalName} assets directory")
    public void given${pluginPascalName}AssetsDirectory(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        String path = row.get("absolutePath");
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException(
                    "DataTable column 'absolutePath' is required for 'Given ${pluginPascalName} assets directory'.");
        }
        shared.assetsDirectory = path;
        log.info("${pluginPascalName} assets directory set to '{}'.", path);
    }
}

