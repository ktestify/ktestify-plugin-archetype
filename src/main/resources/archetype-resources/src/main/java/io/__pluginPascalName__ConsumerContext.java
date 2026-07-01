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

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Immutable context object that configures a {@link ${pluginPascalName}RecordFetcher} for a single fetch operation.
 *
 * @since 1.0.0
 * @see ${pluginPascalName}RecordFetcher
 */
@Value
@Builder
public class ${pluginPascalName}ConsumerContext {

    String resourceName;
    String recordId;
    String connectionString;

    String matchMethod;

    @Builder.Default
    List<String> matchFilePaths = Collections.emptyList();

    @Builder.Default
    List<String> excludedFields = Collections.emptyList();

    Long readTimeoutMs;
    Long pollIntervalMs;

    public String getMatchFilePath() {
        return matchFilePaths != null && !matchFilePaths.isEmpty() ? matchFilePaths.get(0) : null;
    }
}

