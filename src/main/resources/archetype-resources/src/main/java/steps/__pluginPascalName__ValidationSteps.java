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
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * Cucumber {@code @Then} and {@code @And} step definitions for ${pluginPascalName} validations.
 *
 * @since 1.0.0
 */
@Slf4j
public class ${pluginPascalName}ValidationSteps {

    private final Shared${pluginPascalName}Resources shared;

    public ${pluginPascalName}ValidationSteps(Shared${pluginPascalName}Resources shared) {
        this.shared = shared;
    }

    @Then("expected ${pluginPascalName} record from file")
    public void thenExpected${pluginPascalName}RecordFromFile(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        Ktestify${pluginPascalName}Entity resource = resolveResource(row);
        log.info("Validating record '{}' in resource '{}'…", row.get("recordId"), resource.getResourceName());
        shared.validationService.validateFromFile(row, resource, shared.assetsDirectory);
    }

    @And("${pluginPascalName} record should not appear")
    public void and${pluginPascalName}RecordShouldNotAppear(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        Ktestify${pluginPascalName}Entity resource = resolveResource(row);
        log.info(
                "Asserting record '{}' does not appear in resource '{}'…",
                row.get("recordId"),
                resource.getResourceName());
        shared.validationService.validateRecordAbsent(row, resource);
    }

    @And("${pluginPascalName} record should appear")
    public void and${pluginPascalName}RecordShouldAppear(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        Ktestify${pluginPascalName}Entity resource = resolveResource(row);
        log.info("Asserting record '{}' appears in resource '{}'…", row.get("recordId"), resource.getResourceName());
        shared.validationService.validateFromFile(
                new java.util.HashMap<>(row) {
                    {
                        putIfAbsent("file", "__existence_check__");
                    }
                },
                resource,
                shared.assetsDirectory);
    }

    private Ktestify${pluginPascalName}Entity resolveResource(Map<String, String> row) {
        String alias = row.get("resourceAlias");
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("DataTable column 'resourceAlias' is required.");
        }
        return shared.resources.getOrThrow(alias);
    }
}

