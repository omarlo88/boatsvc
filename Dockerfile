FROM openjdk:17-alpine
LABEL maintainer="omarlo@"
COPY target/boatsvc.jar boatsvc.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","boatsvc.jar"]


# Build image => docker build -t boatsvc:v0 .
# Build image => docker build --rm -t boatsvc:v0 .
#To run the container => docker run -d -p 8082:80 boatsvc:v0
#To run the container => docker run --name baotsvc -d -p 8082:80 boatsvc:v0
#Tag our image with repo in docker hub => docker tag boatsvc:v0 omarlo/first-repository:v0
# Push our image =>  docker push omarlo/first-repository:v0
