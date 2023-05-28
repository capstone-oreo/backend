FROM openjdk:17-alpine

COPY ./build/libs/speech-maru-backend-0.0.1-SNAPSHOT.jar /speech-maru-backend-0.0.1-SNAPSHOT.jar

CMD ["java", "-Dspring.profiles.active=${SERVER_MODE}", \
"-Dspring.data.mongodb.uri=mongodb://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOST}:27017/oreo?authSource=admin", \
"-Doracle.cloud.bucket=${ORACLE_CLOUD_BUCKET}", "-Doracle.cloud.namespace=${ORACLE_CLOUD_NAMESPACE}", \
"-jar", "-Duser.timezone=Asia/Seoul", "/speech-maru-backend-0.0.1-SNAPSHOT.jar"]