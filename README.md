# spending-tracker-tool-backend

### Building and running the tests
Clone the repository and run:  
`./gradlew clean test --info`

### Running the app 
Start the database using Docker:  
`docker-compose -f docker-compose.yml --env-file .docker-env up -d`

Run the application   
`./gradlew bootRun -DJDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/spending_tracker -DJDBC_DATABASE_USERNAME=postgres -DJDBC_DATABASE_PASSWORD=postgres`

### OpenAPI spec
You can find the OpenAPI specification under http://localhost:8080/swagger-ui/index.html
