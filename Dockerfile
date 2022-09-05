#
# Build stage
FROM gradle:5.6.2-jdk11 as build

COPY --chown=gradle:gradle ./src /home/gradle/src/
COPY --chown=gradle:gradle ./build.gradle /home/gradle/
COPY --chown=gradle:gradle ./settings.gradle /home/gradle/
WORKDIR /home/gradle
RUN gradle assemble --stacktrace --scan

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /home/gradle/build/libs/fleet-manager-0.0.1-SNAPSHOT.jar /app/fleet-manager.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/fleet-manager.jar"]
