FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/twitch-mentions-discord.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar --spring.config.location=$CONFIG_LOCATION" ]