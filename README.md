<p align="center">
  <img src="https://raw.githubusercontent.com/ktestify/.github/refs/heads/main/profile/assets/png/ktestify-banner-2x.png" alt="ktestify-plugin-archetype" width="100%"/>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/build-passing-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="build passing"/>
  <img src="https://img.shields.io/badge/license-Apache%202.0-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="license"/>
  <img src="https://img.shields.io/badge/java-25-2DD4BF?style=flat-square&labelColor=0C1018&color=2DD4BF" alt="java 25"/>
  <img src="https://img.shields.io/badge/version-1.0--SNAPSHOT-6EE7B7?style=flat-square&labelColor=0C1018&color=6EE7B7" alt="version"/>
</p>

<br/>

**ktestify-plugin-archetype** is a Maven archetype that generates a complete, ready-to-build [ktestify](https://github.com/ktestify) plugin project in seconds. The generated project includes the full three-layer architecture (transport → orchestration → assertion), Cucumber step definitions, configuration, SPI registration, unit tests, Spotless formatting, and a release profile for Maven Central.

---

## Quick Start

### Generate a plugin project

```bash
mvn archetype:generate \
  -DarchetypeGroupId=io.github.ktestify \
  -DarchetypeArtifactId=ktestify-plugin-archetype \
  -DarchetypeVersion=1.0-SNAPSHOT \
  -DgroupId=io.github.ktestify \
  -DartifactId=ktestify-plugin-s3 \
  -Dversion=1.0-SNAPSHOT \
  -Dpackage=io.github.ktestify.s3 \
  -DpluginShortName=s3 \
  -DpluginKebabId=s3 \
  -DpluginPascalName=S3 \
  -DauthorName="Your Name" \
  -DauthorEmail="your.email@example.com" \
  -DgithubOrg=your-github-username \
  -DgithubRepoName=ktestify-plugin-s3 \
  -DinteractiveMode=false
```

### Build the generated project

```bash
cd ktestify-plugin-s3
mvn compile
```

That's it — you have a compilable plugin skeleton with all the boilerplate done.

---

## Archetype Parameters

| Parameter | Description | Example |
|---|---|---|
| `groupId` | Maven groupId for the generated project | `io.github.ktestify` |
| `artifactId` | Maven artifactId (should start with `ktestify-plugin-`) | `ktestify-plugin-s3` |
| `version` | Initial Maven version | `1.0-SNAPSHOT` |
| `package` | Base Java package | `io.github.ktestify.s3` |
| `pluginShortName` | Short lowercase name (file names, log files) | `s3` |
| `pluginKebabId` | Kebab-case plugin ID (HOCON config paths) | `s3` |
| `pluginPascalName` | PascalCase name (Java class names) | `S3` |
| `authorName` | Author display name (startup banner + POM) | `Your Name` |
| `authorEmail` | Author contact email | `your.email@example.com` |
| `githubOrg` | GitHub username or organisation | `your-github-username` |
| `githubRepoName` | GitHub repository name | `ktestify-plugin-s3` |

> **ktestify-core version**: The generated project depends on the latest released version of `ktestify-core`. This version is managed by Dependabot — when a new version of ktestify-core is released, Dependabot automatically opens a PR to bump it in the archetype template.

---

## What the Generated Project Contains

```
ktestify-plugin-s3/
├── pom.xml                          ← Full POM with Spotless, JaCoCo, release profile
├── README.md                        ← Plugin README with step examples
├── LICENSE                          ← Apache 2.0
├── cliff.toml                       ← git-cliff changelog config
├── .gitignore
├── .spotless/
│   └── HEADER.txt                   ← Apache 2.0 license header (with your name)
└── src/
    ├── main/
    │   ├── java/io/github/ktestify/s3/
    │   │   ├── S3Plugin.java                    ← KtestifyPlugin SPI implementation
    │   │   ├── config/
    │   │   │   └── S3Config.java                ← Typed HOCON config reader
    │   │   ├── io/
    │   │   │   ├── S3RecordFetcher.java         ← RecordFetcher<String> (transport layer)
    │   │   │   ├── S3Consumer.java              ← Orchestration (fetch → match → result)
    │   │   │   └── S3ConsumerContext.java       ← Immutable per-fetch context
    │   │   ├── entities/
    │   │   │   └── KtestifyS3Entity.java        ← Resource entity (name, alias, credentials)
    │   │   ├── services/
    │   │   │   ├── S3ActionService.java         ← Send/upload service
    │   │   │   └── S3ValidationService.java     ← Validation + timeout orchestration
    │   │   └── steps/
    │   │       ├── S3BackgroundSteps.java       ← @Given steps
    │   │       ├── S3ActionSteps.java           ← @When steps
    │   │       ├── S3ValidationSteps.java       ← @Then / @And steps
    │   │       └── SharedS3Resources.java       ← PicoContainer shared state
    │   └── resources/
    │       ├── reference.conf                   ← Default HOCON config
    │       ├── log4j2.properties                ← Logging config
    │       └── META-INF/services/
    │           └── io.github.ktestify.plugin.KtestifyPlugin
    └── test/
        └── java/io/github/ktestify/s3/
            ├── S3PluginTest.java                ← Plugin lifecycle tests
            └── config/
                └── S3ConfigTest.java            ← Config loading tests
```

---

## What You Need to Implement

The generated code is a **compilable skeleton** with TODO markers. After generation, you need to:

1. **Implement the transport client** — Replace the stubs in `S3RecordFetcher.java` and `S3ActionService.java` with your transport SDK calls
2. **Add transport-specific dependencies** — Add your SDK (e.g. AWS SDK, JMS client) to `pom.xml`
3. **Customize step wording** — Rename the Gherkin step text to match your transport (e.g. "S3 bucket" instead of "S3 resource")
4. **Add integration tests** — Use Testcontainers to test against a real or emulated service
5. **Update the README** — Replace the TODO paragraph with a description of what your plugin does

---

## Development

```bash
# Build and install the archetype locally
mvn clean install

# Generate a test project
mvn archetype:generate \
  -DarchetypeGroupId=io.github.ktestify \
  -DarchetypeArtifactId=ktestify-plugin-archetype \
  -DarchetypeVersion=1.0-SNAPSHOT \
  -DgroupId=com.example \
  -DartifactId=test-plugin \
  -Dversion=1.0-SNAPSHOT \
  -Dpackage=com.example.testplugin \
  -DpluginShortName=testplugin \
  -DpluginKebabId=test-plugin \
  -DpluginPascalName=TestPlugin \
  -DinteractiveMode=false

# Verify the generated project compiles
cd test-plugin
mvn compile
```

---

## Related

- **[ktestify-core](https://github.com/ktestify/ktestify-core)** — the foundation library and plugin SPI
- **[ktestify-cucumber](https://github.com/ktestify/ktestify-cucumber)** — the BDD runner
- **[ktestify-plugin-skeleton](https://github.com/ktestify/ktestify-plugin-skeleton)** — the reference template (this archetype is derived from it)
- **[ktestify-plugin-azureblob](https://github.com/ktestify/ktestify-plugin-azureblob)** — full reference implementation
- **[docs.ktestify.xyz](https://docs.ktestify.xyz)** — full documentation and configuration reference

---

## Contributing

Contributions are welcome. Please read the contributing guide before opening a pull request.

1. Fork the repository
2. Create a feature branch — `git checkout -b feat/my-feature`
3. Commit with [Conventional Commits](https://www.conventionalcommits.org/) — `git commit -m "feat: add my feature"`
4. Push and open a Pull Request against `main`

---

## License

ktestify-plugin-archetype is licensed under the [Apache License 2.0](LICENSE).

---

<p align="center">
  <img src="https://raw.githubusercontent.com/ktestify/.github/refs/heads/main/profile/assets/png/ktestify-logo-128.png" width="32" height="32" alt="KTestify"/>
  <br/>
  <sub>Assert the stream. Own the pipeline.</sub>
</p>

