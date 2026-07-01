#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<p align="center">
  <img src="https://raw.githubusercontent.com/ktestify/.github/refs/heads/main/profile/assets/png/ktestify-banner-2x.png" alt="ktestify-plugin-${pluginShortName}" width="100%"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/build-passing-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="build passing"/>
  <img src="https://img.shields.io/badge/license-Apache%202.0-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="license"/>
  <img src="https://img.shields.io/badge/java-25-2DD4BF?style=flat-square&labelColor=0C1018&color=2DD4BF" alt="java 25"/>
  <img src="https://img.shields.io/badge/version-1.0--SNAPSHOT-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="version"/>
</p>

<br/>

**ktestify-plugin-${pluginShortName}** is a [ktestify](https://github.com/ktestify) plugin that adds **${pluginPascalName}** transport support. It implements the `KtestifyPlugin` SPI from [ktestify-core](https://github.com/ktestify/ktestify-core) and ships ready-to-use Cucumber step definitions.

> **TODO:** Replace this paragraph with a 1–2 sentence description of what your plugin does.

Drop the JAR into your `ktestify-cucumber` setup and the steps are automatically discovered — no code changes required.

---

## Installation

```xml
<dependency>
    <groupId>io.github.ktestify</groupId>
    <artifactId>ktestify-plugin-${pluginShortName}</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>test</scope>
</dependency>
```

### With ktestify-cucumber (fat JAR / Docker)

Drop the plugin JAR into the `/workspace/plugins` mount and ktestify-cucumber will load it automatically via `ServiceLoader` at startup:

```bash
docker run --rm \
  -v $(pwd)/features:/workspace/features \
  -v $(pwd)/assets:/workspace/assets \
  -v $(pwd)/plugins:/workspace/plugins \
  ghcr.io/ktestify/ktestify-cucumber:latest \
  /workspace/features
```

---

## Configuration

Add to your `application.conf` (or override via environment variables):

```hocon
ktestify.plugins.${pluginKebabId} {
  # TODO: document your plugin's config keys here
  connection-string = ""
  connection-string = ${symbol_dollar}{?KTESTIFY_${pluginShortName.toUpperCase()}_CONNECTION_STRING}

  read-timeout  = 30s
  read-timeout  = ${symbol_dollar}{?KTESTIFY_${pluginShortName.toUpperCase()}_READ_TIMEOUT}

  poll-interval = 500ms
}
```

---

## Gherkin Steps

### Background — register a resource

```gherkin
Background:
  Given ${pluginPascalName} resource
    | resourceName | resourceAlias | connectionString |
    | my-resource  | my-alias      | ...              |
```

### Action — produce / upload

```gherkin
When ${pluginPascalName} record is sent from file
  | resourceAlias | file         |
  | my-alias      | payload.json |
```

### Validation — assert content

```gherkin
Then expected ${pluginPascalName} record from file
  | resourceAlias | file          | readTimeout | excludedKeys |
  | my-alias      | expected.json | 30          | timestamp,id |
```

### Negative assertion

```gherkin
And ${pluginPascalName} record should not appear
  | resourceAlias | readTimeout |
  | my-alias      | 10          |
```

---

## Architecture

This plugin follows the three-layer separation defined by ktestify-core:

```
┌──────────────────────────────────────────────────────────────┐
│  TRANSPORT                 │  ORCHESTRATION  │  ASSERTION    │
│  ${pluginPascalName}RecordFetcher  │  ${pluginPascalName}Consumer│ RecordMatcher │
│  implements RecordFetcher  │                 │ (from core)   │
└──────────────────────────────────────────────────────────────┘
```

- **Transport** — `${pluginPascalName}RecordFetcher` implements `RecordFetcher<String>` from ktestify-core.
- **Orchestration** — `${pluginPascalName}Consumer` wires fetch → match → result.
- **Assertion** — all standard `RecordMatcher` implementations from ktestify-core are reused as-is.

---

## Development

```bash
# Compile
mvn compile

# Unit tests only (no Docker)
mvn test

# All tests including integration tests (requires Docker)
mvn verify

# Code formatting
mvn spotless:apply
```

---

## License

Apache License 2.0 — see [LICENSE](LICENSE).

