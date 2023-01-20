# Running remotely
## Requirements
* Java 11
* Docker
* Maven
### Build, compile, test and run application
* mvn clean install
### Build docker image application
* docker build --tag keylocker:0.0.1-SNAPSHOT .
### Pull images and start application
* docker-compose up
### Access swagger docs
* http://{docker-container-ipv4}:8082/keylocker/swagger-ui.html

# Debugging
* Start mongodb container
* Stop application container
* Start application setting vm options to use mongodb container 
  * -Dspring.data.mongodb.host=localhost -Dspring.data.mongodb.port=27018

# Payload examples
## Create
```
{
  "accountHolderFirstName": "Joao",
  "accountHolderLastName": "Silva",
  "accountNumber": "00000001",
  "accountType": "CORRENTE",
  "agencyNumber": "0001",
  "keyType": "CELULAR",
  "value": "+5535983788879"
}
```

## Update
```
{
  "accountNumber": "00000001",
  "agencyNumber": "0001",
  "keyType": "CELULAR",
  "value": "+5535983788500"
}
```