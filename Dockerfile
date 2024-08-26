FROM m2:1 AS m2

FROM ghcr.io/graalvm/native-image-community:17 AS  build
COPY --from=m2 /root/.m2/repository /root/.m2/repository
COPY repository/org/graalvm/buildtools /root/.m2/repository/org/graalvm/buildtools
WORKDIR /app
COPY . .
RUN ./mvnw clean install -DskipTests=true -Dmaven.javadoc.skip=true
RUN ./mvnw -pl modules/core test -Pnative -Dagent=true -Dmaven.javadoc.skip=true

FROM alpine:3.14
WORKDIR /app
COPY --from=build /root/.m2/repository /root/.m2/repository
RUN echo m2



#FROM m2ignite AS m2
#
#
#FROM ghcr.io/graalvm/native-image-community:17 AS  build
#COPY --from=m2 /root/.m2/repository /root/.m2/repository
#COPY repository/org/graalvm/buildtools /root/.m2/repository/org/graalvm/buildtools
#WORKDIR /app
#COPY . .
#RUN ./mvnw clean install -DskipTests=true -Dmaven.javadoc.skip=true
#RUN ./mvnw -pl modules/ignite-core-trace test -Pnative -Dagent=true -Dmaven.javadoc.skip=true
#
#FROM alpine:3.14
#WORKDIR /app
#COPY --from=build /root/.m2/repository /root/.m2/repository
#
#RUN echo m2
