FROM gradle:jdk21-alpine as build

ARG DB_USER=test
ARG DB_NAME=test
ARG DB_PASS=test
ARG JWT_SC_P=test
ARG JWT_EXP_ZONE=Asia/Yekaterinburg

ENV DB_USER $DB_USER
ENV DB_NAME $DB_NAME
ENV DB_PASS $DB_PASS
ENV JWT_SC_P $JWT_SC_P
ENV JWT_EXP_ZONE $JWT_EXP_ZONE

WORKDIR /tmp
COPY . /tmp

RUN gradle bootJar

FROM eclipse-temurin:21 as production
COPY --from=build /tmp/build/libs/CategoryService-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-Xms256M", "-Xmx312M","-jar","CategoryService-0.0.1-SNAPSHOT.jar"]