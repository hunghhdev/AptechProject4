FROM openjdk:12-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} hotelservice.war
ENTRYPOINT ["java","-jar","/hotelservice.war"]