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
import io.cucumber.java.en.When;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Cucumber {@code @When} step definitions for ${pluginPascalName} actions (send/upload).
 *
 * @since 1.0.0
 */
@Slf4j
public class ${pluginPascalName}ActionSteps {

    private final Shared${pluginPascalName}Resources shared;

    public ${pluginPascalName}ActionSteps(Shared${pluginPascalName}Resources shared) {
        this.shared = shared;
    }

    @When("${pluginPascalName} record is sent from file")
    public void when${pluginPascalName}RecordIsSentFromFile(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);

        String resourceAlias = getRequired(row, "resourceAlias");
        String file = getRequired(row, "file");
        String recordId = getRequired(row, "recordId");

        Ktestify${pluginPascalName}Entity resource = shared.resources.getOrThrow(resourceAlias);
        String resolvedFile = resolve(shared.assetsDirectory, file);

        log.info(
                "Sending file '{}' as record '{}' to resource '{}'…",
                resolvedFile,
                recordId,
                resource.getResourceName());

        shared.actionService.send(resource, recordId, resolvedFile);
    }

    private static String getRequired(Map<String, String> row, String col) {
        String v = row.get(col);
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException("Required DataTable column '" + col + "' is missing.");
        }
        return v.trim();
    }

    private static String resolve(String assetsDir, String path) {
        if (assetsDir == null || assetsDir.isBlank() || path == null) return path;
        if (java.nio.file.Path.of(path).isAbsolute()) return path;
        return java.nio.file.Path.of(assetsDir, path).toString();
    }
}

