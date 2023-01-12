1-
mvn clean install

2-
docker build --tag keylocker:0.0.1-SNAPSHOT .

3-
docker-compose up

4-
container = "IPAddress": "172.21.0.3"

5-
http://172.21.0.3:8082/keylocker/swagger-ui.html#/key-locker-controller

{
"accountHolderFirstName": "Lucas",
"accountHolderLastName": "Melo",
"accountNumber": "227053",
"accountType": "CORRENTE",
"agencyNumber": "1565",
"keyType": "CELULAR",
"value": "+5519991563697"
}