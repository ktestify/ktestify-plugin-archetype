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

import io.github.ktestify.config.KtestifyConfig;
import io.github.ktestify.manager.ObjectManager;
import ${package}.config.${pluginPascalName}Config;
import ${package}.entities.Ktestify${pluginPascalName}Entity;
import ${package}.services.${pluginPascalName}ActionService;
import ${package}.services.${pluginPascalName}ValidationService;

/**
 * PicoContainer-managed shared state for the ${pluginPascalName} step definitions.
 *
 * @since 1.0.0
 */
public class Shared${pluginPascalName}Resources {

    public final ObjectManager<Ktestify${pluginPascalName}Entity> resources = new ObjectManager<>();
    public final ${pluginPascalName}Config config;
    public final ${pluginPascalName}ActionService actionService;
    public final ${pluginPascalName}ValidationService validationService;
    public String assetsDirectory;

    public Shared${pluginPascalName}Resources() {
        KtestifyConfig cfg = KtestifyConfig.getOrLoad();
        this.config = ${pluginPascalName}Config.from(cfg.getRaw());
        this.actionService = new ${pluginPascalName}ActionService(config);
        this.validationService = new ${pluginPascalName}ValidationService(config);

        cfg.getFramework()
                .getAssetsDirectory()
                .filter(path -> !path.isBlank())
                .ifPresent(path -> this.assetsDirectory = path);
    }
}

