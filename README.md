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
JWT is used to authenticate user and make this service stateless.

HMAC is used for simplicity + this service is the sole issuer and consumer.
RSA is more suitable when issuer and consumers are different but more setups
are required as it involves private & public key.

## Design
### Storage
It is all in memory for simplicity. 
PlayerQueue & GameManager interfaces provides future options to implement different strategy of managing user & games
if that is required on different environment.

These interfaces are done for more of demonstration than practicality.
Unless it is quite clear that what methods an interface should have,
it is practical to code PlayerQueue & GameManager instance first,
then refactor & create an interface when it is needed.

Don't forget to update GameBeansConfig.java to associate new storage options to properties file. 

#### Memory Storage Player
User registration is not permanent. 
User is flushed every time game is over
or player is inactive for minutes specified in application.properties.

### Find the winner
InARowFinder.java is the interface to find 5 in row pattern in the board.
Each implementation will try to find the one of patterns.

This way each implementation is more simple to code & test.

### REST
I haven't used fine tuned http status response for API error.
Rather it only returns either 200 or 500.

This is driven by my personal opinion that on REST over HTTP

* API endpoint (per HTTP method) is single responsibility

POST /card is to create a card so success is always means a card is created.

* it makes easy for front-end developer to handle error

If it is 500, API consumers know that request pass through the framework related
process and actually hit the API logic.
To let consumers know the detail of the error, I prefer using a generic response object.
(e.g. GameResponse.java used at GameController#dropDisc method)



## Build & Run
`./gradlew clean build` for Mac & linux or `gradlew.bat clean build` for windows

`java -jar build/libs/wonlee_genesys_sb_backend-0.0.1-SNAPSHOT.jar `

If you want to use custom application.properties,
append java command with `--spring.config.location=` and path to the file.

To interact with this service, clone the cli project from 
[the repo](https://github.com/wolettie/2020-genesys-5-in-row-cli-node)
then run it locally (check "Run" section of README).
