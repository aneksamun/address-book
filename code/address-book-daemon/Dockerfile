FROM openjdk:8u171-alpine3.8
EXPOSE 8091
ADD /target/address-book-daemon-1.0.jar address-book-daemon-1.0.jar
ENTRYPOINT ["java","-jar","address-book-daemon-1.0.jar"]
