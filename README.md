
# Point of Sale Terminal

The Point of sale terminal is a microservice application which will allow the user to manage a inventory of items and then create a transaction to purchase said items.



## Running Locally
## API Endpoints



## Data Model Diagrams
You can view the Data model at the following address: https://lucid.app/lucidchart/39af56f9-d216-4c38-ba67-1a91631c48b3/edit?viewport_loc=117%2C256%2C2219%2C1055%2C0_0&invitationId=inv_10d4e142-3015-4f5a-a0d1-e95ea57ca4a0
## Considerations
- I Created A DAL layer which would traditionally be used along with an ORM such as spring data to interface with the DB. In my case we're using it to mock data this is the least "accurate" of all of the classes regarding what I would ship to production
- I chose to model the data after a SQL database whilst this was not a requirement I decided that a POS/ inventory system may need the ability to query data invarious ways. for example if we were to expand this on to item categories, purchase history...etc
- this service is written as a singular service but its worth noting that if the project grew we may want to split the DAL out from the API logic and make them two or more services
- I Wouldve prefered to use a UUID for ItemId instead of itemCode however I Stuck with Item code due to the spec I was given
- Due to how I decided to manage the transaction showing the customer each instance of items they purchased I had to deal with synchgronizing the items and the count of the number of items. This could be improved 


## Improvements
- I would've preferred to use a database with an ORM that would've meade the code more realistic
- I Could love to talk with the Product owner regarding the limitations of using ItemCode versus something like a UUID
- We could expand this to be a multi tennant system by bounding it to a client entity
- I would've liked to understand the use case foir this data. it may make more sense to go for a NoSQL approach such as MongoDB or DynamoDB depending upon the requirements
- Depending on the scope of the "project" we may want to break this out into multiple services
- I would have liked to create a docker image to run this on but due to time constrains that was not possible


## Tech Stack
- Spring Boot
- lombok
- spriong actuator
- Swagger