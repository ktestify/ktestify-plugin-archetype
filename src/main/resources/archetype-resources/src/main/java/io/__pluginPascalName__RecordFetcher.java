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
package ${package}.io;

import io.github.ktestify.exceptions.FetchException;
import io.github.ktestify.io.core.RecordFetcher;
import io.github.ktestify.models.ConsumedRecord;
import ${package}.config.${pluginPascalName}Config;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transport-layer implementation of {@link RecordFetcher} for ${pluginPascalName}.
 *
 * <p>Polls for a specific record until it exists (or the read timeout expires). Returns the record's content as a
 * {@link ConsumedRecord}{@code <String>} — the common currency shared with all ktestify matchers.
 *
 * @since 1.0.0
 * @see ${pluginPascalName}ConsumerContext
 * @see ${pluginPascalName}Consumer
 */
public class ${pluginPascalName}RecordFetcher implements RecordFetcher<String> {

    private static final Logger LOG = LoggerFactory.getLogger(${pluginPascalName}RecordFetcher.class);

    private final ${pluginPascalName}ConsumerContext context;
    private final ${pluginPascalName}Config globalConfig;

    public ${pluginPascalName}RecordFetcher(${pluginPascalName}ConsumerContext context, ${pluginPascalName}Config globalConfig) {
        this.context = context;
        this.globalConfig = globalConfig;
    }

    @Override
    public List<ConsumedRecord<String>> fetch() throws FetchException {
        String recordId = context.getRecordId();
        String resourceName = context.getResourceName();
        long deadlineMs = System.currentTimeMillis() + resolveReadTimeoutMs();
        long pollMs = resolvePollIntervalMs();

        LOG.info(
                "Waiting for record '{}' in resource '{}' (timeout={}ms, poll={}ms)…",
                recordId,
                resourceName,
                resolveReadTimeoutMs(),
                pollMs);

        while (System.currentTimeMillis() < deadlineMs) {
            // TODO: check whether the record exists using your transport client.
            boolean exists = false; // ← replace this stub

            if (exists) {
                LOG.info("Record '{}' found — fetching content.", recordId);
                return List.of(downloadRecord(resourceName, recordId));
            }

            LOG.debug("Record '{}' not yet present — retrying in {}ms…", recordId, pollMs);
            sleep(pollMs, recordId);
        }

        throw new FetchException(String.format(
                "Timed out after %dms waiting for record '%s' in resource '%s'.",
                resolveReadTimeoutMs(), recordId, resourceName));
    }

    @Override
    public void close() {
        LOG.debug("${pluginPascalName}RecordFetcher closed.");
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private ConsumedRecord<String> downloadRecord(String resourceName, String recordId) {
        try {
            // TODO: download the record content via your transport client.
            String content = "TODO: replace with actual content fetch";
            Instant timestamp = Instant.now();
            Map<String, String> headers = Collections.emptyMap();

            LOG.debug("Fetched record '{}'.", recordId);
            return new ConsumedRecord<>(resourceName, 0, -1L, recordId, content, timestamp, headers);
        } catch (Exception e) {
            throw new FetchException("Failed to fetch record '" + recordId + "': " + e.getMessage(), e);
        }
    }

    private long resolveReadTimeoutMs() {
        return context.getReadTimeoutMs() != null ? context.getReadTimeoutMs() : globalConfig.getReadTimeoutMs();
    }

    private long resolvePollIntervalMs() {
        return context.getPollIntervalMs() != null ? context.getPollIntervalMs() : globalConfig.getPollIntervalMs();
    }

    private static void sleep(long ms, String recordId) throws FetchException {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FetchException("Interrupted while waiting for record '" + recordId + "'.");
        }
    }
}

