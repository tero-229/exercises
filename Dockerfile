FROM gradle:latest as build
COPY ./ /
WORKDIR /
RUN gradle bootJar

FROM alpine:latest
RUN apk add openjdk8
COPY --from=build /build/libs/*.jar /java-appis.jar
ENTRYPOINT java -jar /java-appis.jar
EXPOSE 8080



