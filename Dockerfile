FROM openjdk:17-alpine
LABEL maintainer="omarlo@"
COPY target/boatsvc.jar boatsvc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","boatsvc.jar"]

#To run the container => docker run -d -p 8082:80 boatsvc:v0