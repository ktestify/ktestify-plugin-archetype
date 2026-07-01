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

import static org.junit.jupiter.api.Assertions.*;

import com.typesafe.config.ConfigFactory;
import io.github.ktestify.config.KtestifyConfig;
import io.github.ktestify.plugin.PluginContext;
import org.junit.jupiter.api.*;

/**
 * Unit tests for {@link ${pluginPascalName}Plugin} — lifecycle, metadata, and configuration validation.
 *
 * @since 1.0.0
 */
@DisplayName("${pluginPascalName}Plugin")
class ${pluginPascalName}PluginTest {

    private ${pluginPascalName}Plugin plugin;
    private PluginContext ctx;

    @BeforeEach
    void setUp() {
        KtestifyConfig.reset();
        plugin = new ${pluginPascalName}Plugin();
        ctx = KtestifyConfig::getOrLoad;
    }

    @AfterEach
    void tearDown() {
        KtestifyConfig.reset();
    }

    @Nested
    @DisplayName("Metadata")
    class MetadataTests {

        @Test
        @DisplayName("getId() returns '${pluginKebabId}'")
        void idIsCorrect() {
            assertEquals("${pluginKebabId}", plugin.getId());
        }

        @Test
        @DisplayName("getVersion() is non-blank")
        void versionIsNonBlank() {
            assertNotNull(plugin.getVersion());
            assertFalse(plugin.getVersion().isBlank());
        }

        @Test
        @DisplayName("getGluePackage() returns the steps package")
        void gluePackageIsStepsPackage() {
            assertEquals("${package}.steps", plugin.getGluePackage());
        }
    }

    @Nested
    @DisplayName("initialize()")
    class InitializeTests {

        @Test
        @DisplayName("initialize() succeeds when config section is present (no credentials = warn only)")
        void initializeSucceedsWithNoCreds() {
            assertDoesNotThrow(() -> plugin.initialize(ctx));
        }

        @Test
        @DisplayName("initialize() succeeds when connection string is set")
        void initializeSucceedsWithConnectionString() {
            KtestifyConfig cfg = KtestifyConfig.load(
                    ConfigFactory.parseString("ktestify.plugins.${pluginKebabId}.connection-string = \"my-conn-string\""));

            assertDoesNotThrow(() -> plugin.initialize(() -> cfg));
        }
    }

    @Nested
    @DisplayName("shutdown()")
    class ShutdownTests {

        @Test
        @DisplayName("shutdown() before initialize() does not throw")
        void shutdownBeforeInitDoesNotThrow() {
            assertDoesNotThrow(plugin::shutdown);
        }

        @Test
        @DisplayName("shutdown() after initialize() does not throw")
        void shutdownAfterInitDoesNotThrow() {
            plugin.initialize(ctx);
            assertDoesNotThrow(plugin::shutdown);
        }
    }
}

