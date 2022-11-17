mvn clean install -f standalone-e2e-test/pom.xml

By default it runs against localhost:8080

Optionally specify 'targetenv' environment variable
mvn clean install -f standalone-e2e-test/pom.xml -Dtargetenv=local

options: 
local -> http://localhost:8080/  (default)
prod -> http://prod:8080/