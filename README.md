# Spring Boot User Admin Rest Service

### How to build and start the application?
```
./mvnw clean install
./mvnw spring-boot:run -pl webservice
```
Launch as compiled jar
```
mvn clean install
java -jar webservice/target/webservice-0.0.1-SNAPSHOT.jar
```

### Api Documentation
```
http://localhost:8080/api/swagger-ui/
```

### Testing

Run E2E tests only
```
mvn clean test -pl standalone-e2e-test -DskipTests=false
```
By default it runs against localhost:8080

Optionally specify 'targetenv' environment variable
```
mvn clean test -pl standalone-e2e-test -DskipTests=false -Dtargetenv=local
```
options:
local -> http://localhost:8080/  (default)

prod -> http://prod:8080/


### Todo: 
bump spring-boot-starter-parent to latest - that means need to bump to swagger 3 (openapi) 
https://stackoverflow.com/questions/72357737/i-am-getting-this-error-failed-to-start-bean-documentationpluginsbootstrapper

### Additional notes
PR link
```
https://github.com/kovaku/spring-boot-user-admin-rest-service/pull/1
```