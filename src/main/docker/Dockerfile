FROM eclipse-temurin:24_36-jre-alpine AS base
WORKDIR /app
EXPOSE 8080

# Optional JVM arguments, such as memory settings
ARG JVM_ARGS=""

ENV WEB_HTTP_PORT="8080"
ENV WEB_HTTP_PATH="/"

FROM eclipse-temurin:21 AS build
WORKDIR /project
COPY . .
RUN ./gradlew build -x test -x check

# -buster is required to have apt available
FROM base AS final

RUN apk --no-cache add curl
COPY --from=build project/build/libs/template.jar template.jar

HEALTHCHECK --interval=5s --timeout=5s --retries=10 CMD curl --fail http://localhost:8080/api/check/health

# Use "exec" for graceful termination (SIGINT) to reach JVM.
# ARG can not be used in ENTRYPOINT so storing value in an ENV variable
ENV ENV_JVM_ARGS=$JVM_ARGS
# use the "exec" syntax so that SIGINT reaches the JVM -> graceful termination
CMD ["sh", "-c", "exec java -Djava.security.egd=file:/dev/urandom -jar template.jar"]
