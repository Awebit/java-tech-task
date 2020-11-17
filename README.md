# Lunch Microservice

The service provides an endpoint that will determine, from a set of recipes, what I can have for lunch at a given date, based on my fridge ingredient's expiry date, so that I can quickly decide what I’ll be having to eat, and the ingredients required to prepare the meal.

## Prerequisites

* [Java 11 Runtime](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Docker](https://docs.docker.com/get-docker/) & [Docker-Compose](https://docs.docker.com/compose/install/)

*Note: Docker is used for the local MySQL database instance, feel free to use your own instance or any other SQL database and insert data from lunch-data.sql script* 


### Run

1. Start database:

    ```
    docker-compose up -d
    ```
   
2. Add test data from  `sql/lunch-data.sql` to the database. Here's a helper script if you prefer:


    ```
    CONTAINER_ID=$(docker inspect --format="{{.Id}}" lunch-db)
    ```
    
    ```
    docker cp sql/lunch-data.sql $CONTAINER_ID:/lunch-data.sql
    ```
    
    ```
    docker exec $CONTAINER_ID /bin/sh -c 'mysql -u rezdy -prezdytechtask lunch </lunch-data.sql'
    ```
    
3. Run Springboot LunchApplication


Lunch Endpoint:

* **URL**

  http://localhost:8080/lunch

* **Method:**

  `GET`

*  **URL Params**

   **Required:**
 
   `date=[string in ISO Date Format]`


* **Sample Call:**

   `http://localhost:8080/lunch?date=2020-11-17`


### Assumptions/Notes

1. Changed database user from 'root' to 'rezdy' for security and due to running in local database.
2. The Best Before & Use By dates are inclusive. i.e. A Use By date that falls on the supplied date is still valid.
3. For a more complicated use case, you would map the domain model to a view model rather than returning the domain model directly so that the API isn't tied directly to the business domain.
4. For a more complicated use case, you might consider structuring the packages by feature instead of by layer.
5. The Integration tests in their current state require a database to be running with the supplied data. To do this properly for CI/CD, you would start-up a docker container with specific data populated for test purposes.
6. For simplicity, we are only returning the Title of each ingredient, we could return the date fields but would need to add further logic for serialization/deserialization of LocalDate.
7. Taking it further we would add generic validation messages such as for 'typeMismatch.date' to give a proper error message in those cases.
