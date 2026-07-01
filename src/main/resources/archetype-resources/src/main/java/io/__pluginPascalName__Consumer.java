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

import io.github.ktestify.exceptions.ConsumerException;
import io.github.ktestify.exceptions.FetchException;
import io.github.ktestify.io.core.AbstractConsumer;
import io.github.ktestify.match.MatchContext;
import io.github.ktestify.match.MatchResult;
import io.github.ktestify.match.RecordMatcher;
import io.github.ktestify.match.RecordMatcherFactory;
import io.github.ktestify.models.ConsumedRecord;
import ${package}.config.${pluginPascalName}Config;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Orchestration-layer consumer for ${pluginPascalName}.
 *
 * <p>Follows the three-layer separation defined by ktestify-core:
 *
 * <ol>
 *   <li><b>Transport</b> — {@link ${pluginPascalName}RecordFetcher}: polls for and fetches the record.
 *   <li><b>Orchestration</b> — this class: wires fetch → match → result.
 *   <li><b>Assertion</b> — {@link RecordMatcher} implementation selected by {@code matchMethod}.
 * </ol>
 *
 * @since 1.0.0
 * @see ${pluginPascalName}RecordFetcher
 */
@Slf4j
public class ${pluginPascalName}Consumer extends AbstractConsumer {

    private final ${pluginPascalName}ConsumerContext context;
    private final ${pluginPascalName}Config globalConfig;
    private final RecordMatcher<String> matcher;

    public ${pluginPascalName}Consumer(${pluginPascalName}ConsumerContext context, ${pluginPascalName}Config globalConfig) {
        super(Collections.emptyMap());
        this.context = context;
        this.globalConfig = globalConfig;
        this.matcher = RecordMatcherFactory.forRaw(context.getMatchMethod());
    }

    @Override
    public Boolean call() {
        ${pluginPascalName}RecordFetcher fetcher = new ${pluginPascalName}RecordFetcher(context, globalConfig);
        try {
            List<ConsumedRecord<String>> records = fetcher.fetch();
            MatchContext matchCtx = buildMatchContext();
            MatchResult result = matcher.match(records, matchCtx);

            if (!result.isPassed()) {
                log.error(
                        "${pluginPascalName} content mismatch for record '{}' in resource '{}':\n{}",
                        context.getRecordId(),
                        context.getResourceName(),
                        result.getDiff());
            }
            return result.isPassed();

        } catch (FetchException e) {
            throw new ConsumerException("${pluginPascalName} fetch failed for record '" + context.getRecordId()
                    + "' in resource '" + context.getResourceName() + "': " + e.getMessage());
        } finally {
            fetcher.close();
        }
    }

    protected MatchContext buildMatchContext() {
        return MatchContext.builder()
                .matchMethod(context.getMatchMethod())
                .matchFilePaths(context.getMatchFilePaths())
                .excludedFields(context.getExcludedFields())
                .strictMatching(false)
                .build();
    }
}

