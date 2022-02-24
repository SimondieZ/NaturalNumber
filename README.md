# Spring Boot REST API  with PostgreSQL Database

Natural Numbers is a simple JSON based REST API using Spring Boot (version 2.6.3) and PostgreSQL as database.   
Provides such an information about natural numbers as divisors, representation of a number in Roman and binary notations, fact about the number from a mathematical point of view.

# Technology stack
 * Spring Boot
 * Spring Data JPA
 * Spring Security (http Basic Auth, Dao Authentication Provider)
 * Spring HATEOAS
 * PostgreSQL
 * Jasypt
 * Mockito
 * JUnit 5
 * OpenAPI (Swagger)
 * slf4J 
 * Maven
 
# Prerequisites

You need to install below software(s) before you begin with. If you have them already installed in your Machine, well and good.
    
   * JDK / JRE - Java 11 or later
   * Apach Maven - Install only when you build project outside of your IDE. IDE does come with built in Maven.
   * PostgreSQL
   * IDE(optional) - Any one from below of your choice:
       * Eclipse
       * IntelliJ IDEA
   * HTTP Client to test REST Api (Any one from below of your choice):
       * Postman - It also comes as Chrome extension.
       * Advanced REST Client - It also comes as Chrome extension.
        
# How to Run
## 1. Create database
First of all we need to create a table named '{your_schema}.numbers' to store our numbers and another datatable 
that will store 2 users with different roles: user and admin.
Below is an example of how we need to create these datatables using PostgreSQL as a database:
<details> 
  <summary>
    <strong>create table numbers</strong>
  </summary>
   
```
  CREATE TABLE {your_schema}.numbers (
      id bigserial NOT NULL,
      value bigint NOT NULL,
      binary_notation character varying,
      roma_notation character varying,
      description character varying,
      divisors integer[],
      PRIMARY KEY (id)
  );
```
</details>
<details> 
  <summary>
    <strong>create table users</strong>
  </summary>
   
```
  CREATE TABLE {your_schema}.users
  (
      id bigserial NOT NULL,
      email character varying(255) NOT NULL,
      first_name character varying(50) NOT NULL,
      last_name character varying(100),
      password character varying(255) NOT NULL,
      role character varying(20) NOT NULL DEFAULT 'USER',
      status character varying(20) DEFAULT 'ACTIVE',
      PRIMARY KEY (id),
      UNIQUE (email)
  );
```
</details>

The data for the tables described above will be automatically once populated when the application starts. 

## 2. Clone the repository
This application is packaged as a jar which has Apache Tomcat 9 embedded. Neither Tomcat or JBoss installation is necessary. 

  *  Clone this repository
  *  Make sure you are using JDK 11 (or later), Maven 3.x and PostgreSQL
   
<details> 
  <summary>
    <strong>Project structure</strong>
  </summary>
   
```
.                                        # main directory project 
+-- java
|   +-- configuration                   # Configuration classes
|   +-- controller                      # Rest controller that handle request/responses
|   +-- entity                          # Define domain models or entities
|   +-- exceptions                      # Define exception handle
|   +-- logging                         # Includes both AOP and logging functionality
|   +-- repository                      # Talks to data source directly, has operations commonly known as CRUD
|   +-- representation                  # Class(-es) for convert types into a RepresentationModel
|   +-- security                        # Defines classes to support security 
|   +-- service                         # Business logic abstractions
|   +-- NaturalNumbersApplication.java  # App starting point
+-- resources 
|   +-- application.properties          # Configurations files            


+-- test/java                           # main test directory project
|   +-- springnumbers                   # Junit & Mockito testing
|   +-- utility                         # Defines utility classes
```
</details>

## 3. Set application.properties <a id="password"></a>

The next thing we need to do is go to the application.properties (src\main\resources) and set some of your preferences:

```
## JPA
spring.jpa.properties.hibernate.default_schema={your_PostgreSQL_schema} 

## PostgreSQL
spring.datasource.url={your_datasource_url}
spring.datasource.username={your_datasource_username}
spring.datasource.password={encoded_password}
```
It goes without saying that we can hard-code the password, but we'll do it a bit securely.  
All we need is to execute one command from the root on the command line (for Windows OC)
```
java -cp .m2/repository/org/jasypt/jasypt/1.9.3/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="real_password" password=your_secretkey algorithm=PBEWithMD5AndDES
```
here:
 - **input** - your real PostgreSQL password
 - **password** - a secret key to be used for encoding and decoding. We will need it when starting the application 
 - **algorithm** - encryption algorithm  

After executing this command, the response will look something like this. 
<details> 
  <summary>
    <strong>command result</strong>
  </summary>
   
  ```
   ----ENVIRONMENT-----------------

  Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 17.0.2+8-LTS-86



  ----ARGUMENTS-------------------

  input: real_password
  password: secretkey
  algorithm: PBEWithMD5AndDES



  ----OUTPUT----------------------

  MFpkNPCU8i4kggSgT3YsgOCTrK7t4Xn0
  ```
</details>

Copy and paste the encoded password from --OUTPUT---- section to [`spring.datasource.password={encoded_password}`](#password) property.

## 4. Build and Run
You can build the project and run the tests by running mvn clean package.    
Once successfully built, you can run the service by one of these two methods:  

  ```
     java -jar -Djasypt.encryptor.password=your_secretkey target/springnumbers-0.0.1-SNAPSHOT.jar
  ```  
    
or

  ```
     set JASYPT_ENCRYPTOR_PASSWORD=your_secretkey
     mvn spring-boot:run
  ```
If you hard-codded your password in application.property, just execute

```
    mvn spring-boot:run
```

After doing the above, the application is ready to use on port 8080.
    
# About the Service
    
If your database connection properties work, you can call some REST endpoints defined in com.simondiez.springnumbers.controller.NumbersController on port 8080 (see below).  
The Natural Numbers API lives at the route /api/v1/numbers. If your application is running on localhost:8080, you would access the API via http://localhost:8080/api/v1/numbers. 
Moreover, the request requires authorization(Basic Auth). 

</details>
<details> 
  <summary>
    <strong>Auth credentials</strong>
  </summary>
   
```
    User - login: user@gmail.com, password: user   
    Admin - login: admin@gmail.com, password: admin 
```
</details>


Method|Url|Description|Request Content-Type|Response Content-Type
:------|:---:|:----------:|:--------------------:|:---------------------:
GET|/api/v1/numbers|Get all numbers|-|application/hal+json
GET|/api/v1/numbers/{id}|Get number by id|-|application/hal+json
POST|/api/v1/numbers|Create a new number (only for admin)|application/json|application/hal+json
PUT|/api/v1/numbers/{id}|Update the number (only for admin)|application/json|application/hal+json
DELETE|/api/v1/numbers/{id}|Delete the number (only for admin)|-|-
       
       
  Create a new number -> /api/v1/numbers  
```
  {
      "value": 4,
      "romaNotation": "IV",
      "binaryNotation": "100",
      "description": "The first positive non-Fibonacci number.",
      "divisors": [
          1,
          2,
          4
      ]
   }
```
   Update the number -> /api/v1/numbers/{id}   
```   
   {
      "id": 5,
      "value": 5,
      "romaNotation": "V",
      "binaryNotation": "101",
      "description": "The third prime number.",
      "divisors": [
          1,
          5
      ]
    }
```
# Swagger
To learn more about the specifications visit http://localhost:8080/swagger-ui/index.html
  
# Author
Created by  <a class="button" href="mailto:chaffeeusa@gmail.com">Serafim Sokhin</a>
