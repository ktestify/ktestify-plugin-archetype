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

import static org.junit.jupiter.api.Assertions.*;

import com.typesafe.config.ConfigFactory;
import io.github.ktestify.config.KtestifyConfig;
import org.junit.jupiter.api.*;

/**
 * Unit tests for {@link ${pluginPascalName}Config}.
 *
 * @since 1.0.0
 */
@DisplayName("${pluginPascalName}Config")
class ${pluginPascalName}ConfigTest {

    @AfterEach
    void tearDown() {
        KtestifyConfig.reset();
    }

    @Test
    @DisplayName("defaults are loaded from reference.conf")
    void defaultsFromReferenceConf() {
        KtestifyConfig cfg = KtestifyConfig.getOrLoad();
        ${pluginPascalName}Config pluginCfg = ${pluginPascalName}Config.from(cfg.getRaw());

        assertEquals(30_000L, pluginCfg.getReadTimeoutMs());
        assertEquals(500L, pluginCfg.getPollIntervalMs());
        assertFalse(pluginCfg.hasConnectionString());
    }

    @Test
    @DisplayName("connection string override is recognised")
    void connectionStringOverride() {
        KtestifyConfig cfg = KtestifyConfig.load(ConfigFactory.parseString(
                "ktestify.plugins.${pluginKebabId}.connection-string = \"Server=localhost;\""));
        ${pluginPascalName}Config pluginCfg = ${pluginPascalName}Config.from(cfg.getRaw());

        assertTrue(pluginCfg.hasConnectionString());
        assertEquals("Server=localhost;", pluginCfg.getConnectionString());
    }

    @Test
    @DisplayName("read-timeout override is applied")
    void readTimeoutOverride() {
        KtestifyConfig cfg =
                KtestifyConfig.load(ConfigFactory.parseString("ktestify.plugins.${pluginKebabId}.read-timeout = 60s"));
        ${pluginPascalName}Config pluginCfg = ${pluginPascalName}Config.from(cfg.getRaw());

        assertEquals(60_000L, pluginCfg.getReadTimeoutMs());
    }
}

