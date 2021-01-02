# Programming Challenge: 5-in-a-Row, Backend

## Project Setup
### Java & Gradle
This project uses Java 11 & Gradle 6.7.1

### Lombok
Lombok Project helps us avoiding boilerplate code on POJO class

Setup on IntelliJ 2020.3 => Go Preference then `Enable Annotation Processing`

[Setup on IntelliJ prior to 2020.3](https://projectlombok.org/setup/intellij)

[Setup on Eclipse](https://projectlombok.org/setup/eclipse)

## Spring Boot & Security
JWT is used to authenticate user.

## Design
### Storage
It is all in memory for simplicity. 
PlayerQueue & GameManager interfaces provides options to implement different storage options
if that is required on different environment.

Don't forget to update GameBeansConfig.java to associate new storage options to properties file. 

#### Memory Storage Player
User registration is not permanent. 
User is flushed every time game is over
or player is inactive for minutes specified in application.properties.

## Build & Run
`./gradew  clean build` for Mac & linux or `gradlew.bat clean build` for windows

`java -jar build/libs/wonlee_accela-0.0.1-SNAPSHOT.jar`

If you want to use custom application.properties,
append java command with `--spring.config.location=` and path to the file.
