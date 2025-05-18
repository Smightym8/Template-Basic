# Template Repository for projects using Eclipse Dataspace Components

## Quick start

To get the example up-and-running, there are two possibilities:

- using Docker:
  ```shell
  ./gradlew dockerize # on x86/64 platforms
  ./gradlew dockerize -Dplatform="linux/amd64" # on arm platforms, e.g. Apple M1
  ```
  
- using a native Java process
  ```shell
  ./gradlew build
  java -jar runtime/build/libs/runtime.jar
  ```

In both cases configuration must be supplied, either using Docker environment variables, or using Java
application properties.

## Things you **NEED** to change

This is a template repository, so some adaptations will obviously have to be made.

1. update `gradle.properties`: this should reflect the appropriate `group` and `version` of _your project_
2. update the `pom` block in [build.gradle.kts](./build.gradle.kts)
3. alter `LICENSE`, `SECURITY.md`, `NOTICE.md` and `CONTRIBUTING.md` as needed by your project.

## Directory structure

- `config`: contains the configuration file for the Checkstyle plugin
- `gradle`: contains the Gradle Wrapper and the Version Catalog
- `runtimes`: contains executable module

