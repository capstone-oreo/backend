FROM openjdk:8-jre

COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar /backend-0.0.1-SNAPSHOT.jar

CMD ["java", "-Dspring.profiles.active=${SERVER_MODE}", "-Dspring.data.mongodb.uri=mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:27017/oreo?authSource=admin", "-jar", "/backend-0.0.1-SNAPSHOT.jar"]