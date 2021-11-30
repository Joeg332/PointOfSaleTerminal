
# Point of Sale Terminal

The Point of sale terminal is a microservice application which will allow the user to manage a inventory of items and then create a transaction to purchase said items. By Default we load up the 4 items given in the example documentation.




## Run Locally

Clone the project

```bash
  git clone https://github.com/Joeg332/PointOfSaleTerminal.git
```

Go to the project directory

```bash
  cd pointOfSaleTerminal
```

Build Project

```bash
  ./gradlew build
```

Start the server

```bash
  ./gradlew bootRun
```
## Running Tests

To run tests, run the following command

```bash
  ./gradlew clean build jacocoTestReport -b build.gradle --stacktrace
```


## API Reference

#### Swagger API Documentation

```http
  GET /pointOfSaleTerminal/api/swagger-ui.html
```

#### Actuator HealthCheck

```http
  GET /pointOfSaleTerminal/api/actuator/health
```

Note: At this time you need to refer to Specific Endpoints using the included swagger doc


## Dependencies
- jdk-17.0.1
    - Availible to download At https://jdk.java.net/17/

## Data Model Diagrams
You can view the data model at the following address: https://lucid.app/lucidchart/39af56f9-d216-4c38-ba67-1a91631c48b3/edit?viewport_loc=117%2C256%2C2219%2C1055%2C0_0&invitationId=inv_10d4e142-3015-4f5a-a0d1-e95ea57ca4a0
## Considerations
- I created A DAL layer which would traditionally be used along with an ORM such as spring data to interface with the DB. In my case we're using it to mock data this is the least "accurate" of all of the classes regarding what I would ship to production
- I chose to model the data after a SQL database whilst this was not a requirement I decided that a POS/inventory system may need the ability to query data in various ways. For example if we were to expand this on to item categories, purchase history...etc
- This service is written as a singular service but its worth noting that if the project grew we may want to split the DAL out from the API logic and make them two or more services
- I would've prefered to use a UUID for ItemId instead of itemCode however I stayed with Item code due to the spec I was given
- Due to how I decided to manage the transaction showing the customer each instance of items they purchased I had to deal with syncronizing the items and the count of the number of items. This could be improved and I have a updated data model on my Lucidchart


## Improvements
- I would've preferred to use a database with an ORM that would've meade the code more realistic
- I would love to talk with the product owner regarding the limitations of using ItemCode versus something like a UUID
- We could expand this to be a multi tennant system by bounding it to a client entity
- I would've liked to understand the use case for this data. It may make more sense to go for a NoSQL approach such as MongoDB or DynamoDB depending upon the requirements
- Depending on the scope of the "project" we may want to break this out into multiple services
- I would have liked to create a docker image to run this on but due to time constrains that was not possible
- I would have liked to add more unit tests but due to time constraints I only included what was required
- I need to improve NPE saftey but considering the time spent on this project I considered it out of scope
- I need to add negative unit tests


## Tech Stack
- Spring Boot
- lombok
- spring actuator
- Swagger
- jacoco