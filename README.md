# Address Book  

Application reads the attached AddressBook file and answer the following questions:

    How many males are in the address book?
    Who is the oldest person in the address book?
    How many days older is Bill than Paul?


## Endpoints

| Path                                                 | Method | Response  | Details                                                                                   |
| ---------------------------------------------------- | ------ | --------- | ------------------------------------------------------------------------------------------|
| http://localhost:8091/contacts/males/count           | GET    | 200       | Counts males.                                                                             |
| http://localhost:8091/contacts/oldest                | GET    | 200/404   | Tells oldest person.                                                                      |
| http://localhost:8091/contacts/{name}/compare/{name} | GET    | 200/404   | Tells how many days one contact is older than other. The '{name}' is name search pattern. |

## Build and run instructions

- build 
```
./mvnw clean install
```
- run via IntelliJ by running/debugging configuration
- run JAR
```
cd code/address-book-daemon/target/
java -jar address-book-daemon-1.0.jar
```
- run and stop Docker image
```
cd code/address-book-daemon/
docker-compose up -d
docker ps
docker-compose stop
```
Please not requires Java 8 on path (otherwise no Jar and Docker build will work)
