FROM openjdk:21-oracle AS TEMP_BUILD_IMAGE

RUN microdnf install findutils

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
COPY buildSrc $APP_HOME/buildSrc

RUN ./gradlew build
COPY . .
RUN ./gradlew build -x test

FROM openjdk:21-oracle

ENV ARTIFACT_NAME=application.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8080
ENTRYPOINT java -jar ./$ARTIFACT_NAME