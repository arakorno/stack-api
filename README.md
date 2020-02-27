## stack-api project

## Architecture decisions and your reasoning behind them
1. I decided to use MongoDB for simplifying the way of searching collections items by some array's
attributes (like searching questions by tags). And also using spring data it's easy to create and work with MongoDB database.

2. For cache managing, I used guava cache manager. This manager easy to configure and
covers all the needs of this application.

3. Also for populating data on the start of running application was used spring framework approach
via using a method annotated with @PostConstruct annotation.

## Libraries' usage
1. For creating integration tests I like to use mockserver library for 
    mocking third-party APIs and parse json responses with jsonassert library.     
## How to build
````
1)  mvn clean install
2)  docker-compose up
````

After that, you can type in browser http://CONTAINER_IP_ADDRESS:8080/swagger-ui.html
and check the swagger documentation for all created end-points.