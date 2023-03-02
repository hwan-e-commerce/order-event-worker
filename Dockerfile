FROM adoptopenjdk/openjdk11
ARG JAR_FILE_PATH=build/libs/order-event-worker-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} stock-event-worker.jar

EXPOSE 8080
ENTRYPOINT [                     \
  "java",                        \
  "-jar",                        \
  "-Dspring.profiles.active=prod",  \
  "order-event-worker.jar"  \
]