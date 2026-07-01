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

import static io.github.ktestify.match.RecordMatcherFactory.METHOD_MATCH_FILE;

import io.github.ktestify.exceptions.ConsumerException;
import ${package}.config.${pluginPascalName}Config;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import ${package}.io.${pluginPascalName}Consumer;
import ${package}.io.${pluginPascalName}ConsumerContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

/**
 * Orchestrates ${pluginPascalName} validation for Cucumber step definitions.
 *
 * @since 1.0.0
 */
@Slf4j
public class ${pluginPascalName}ValidationService {

    private static final long BUFFER_TIME_MS = 5_000L;

    private final ${pluginPascalName}Config globalConfig;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public ${pluginPascalName}ValidationService(${pluginPascalName}Config globalConfig) {
        this.globalConfig = globalConfig;
    }

    public void validateFromFile(Map<String, String> row, Ktestify${pluginPascalName}Entity resource, String assetsDir) {
        String recordId = getRequired(row, "recordId");
        String file = resolve(assetsDir, getRequired(row, "file"));
        List<String> excluded = splitComma(getString(row, "excludedKeys"));
        Long readTimeoutMs = getReadTimeoutMs(row);

        ${pluginPascalName}ConsumerContext ctx = ${pluginPascalName}ConsumerContext.builder()
                .resourceName(resource.getResourceName())
                .connectionString(resource.getConnectionString())
                .recordId(recordId)
                .matchMethod(METHOD_MATCH_FILE)
                .matchFilePaths(List.of(file))
                .excludedFields(excluded)
                .readTimeoutMs(readTimeoutMs)
                .build();

        execute(ctx, resource, readTimeoutMs);
    }

    public void validateRecordAbsent(Map<String, String> row, Ktestify${pluginPascalName}Entity resource) {
        String recordId = getRequired(row, "recordId");
        Long readTimeoutMs = getReadTimeoutMs(row);

        ${pluginPascalName}ConsumerContext ctx = ${pluginPascalName}ConsumerContext.builder()
                .resourceName(resource.getResourceName())
                .connectionString(resource.getConnectionString())
                .recordId(recordId)
                .readTimeoutMs(readTimeoutMs)
                .build();

        boolean found;
        try {
            found = runWithTimeout(new ${pluginPascalName}Consumer(ctx, globalConfig), readTimeoutMs);
        } catch (ConsumerException e) {
            log.info("Record '{}' not found in resource '{}' as expected.", recordId, resource.getResourceName());
            return;
        }
        if (found) {
            throw new AssertionError("Expected record '" + recordId + "' to be absent in resource '"
                    + resource.getResourceName() + "', but it was found.");
        }
    }

    private void execute(${pluginPascalName}ConsumerContext ctx, Ktestify${pluginPascalName}Entity resource, Long readTimeoutMs) {
        boolean passed = runWithTimeout(new ${pluginPascalName}Consumer(ctx, globalConfig), readTimeoutMs);
        if (!passed) {
            throw new AssertionError("${pluginPascalName} validation failed for record '" + ctx.getRecordId()
                    + "' in resource '" + resource.getResourceName() + "'.");
        }
    }

    private boolean runWithTimeout(java.util.concurrent.Callable<Boolean> consumer, Long readTimeoutMs) {
        long effectiveMs = (readTimeoutMs != null ? readTimeoutMs : globalConfig.getReadTimeoutMs()) + BUFFER_TIME_MS;
        Future<Boolean> future = executor.submit(consumer);
        try {
            return Boolean.TRUE.equals(future.get(effectiveMs, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new ConsumerException("Outer timeout exceeded after " + effectiveMs + "ms.");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConsumerException ce) throw ce;
            throw new ConsumerException("${pluginPascalName} consumer execution failed: " + cause.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConsumerException("${pluginPascalName} consumer thread interrupted.");
        }
    }

    private static String getString(Map<String, String> row, String col) {
        String v = row.get(col);
        return (v != null && !v.isBlank()) ? v : null;
    }

    private static String getRequired(Map<String, String> row, String col) {
        String v = getString(row, col);
        if (v == null) throw new IllegalArgumentException("Required DataTable column '" + col + "' is missing.");
        return v;
    }

    private static Long getReadTimeoutMs(Map<String, String> row) {
        String v = getString(row, "readTimeout");
        return v != null ? Long.parseLong(v.trim()) * 1000L : null;
    }

    private static List<String> splitComma(String value) {
        if (value == null || value.isBlank()) return Collections.emptyList();
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    private static String resolve(String assetsDir, String path) {
        if (assetsDir == null || assetsDir.isBlank() || path == null) return path;
        if (java.nio.file.Path.of(path).isAbsolute()) return path;
        return java.nio.file.Path.of(assetsDir, path).toString();
    }
}

