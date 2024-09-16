# fasttrack-assignment
This project is used for holiday application and management for employees.

# Technologies Involved
* JAVA
* Spring Boot
* H2 Database
* Spring Schedulers

# Features
* Employee Management
* Holiday Management
* Schedulers for Holiday Status Management

# APIs
All APIs defined in the solution is exposed using swagger. It could be accessed using [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Implementation
**fasttrack-assignment** application consists of the following workflow
### Employee Management
Use the PUT API provided to add Employees by giving the employee name. The application uses `com.airfranceklm.fasttrack.assignment.dao.generator.PrimaryKeyGenerator` to create the unique Employee ID with the format **klmXXXXXX**.
Other GET APIs can be used to retrieve the employee details as well.

### Holiday Management
Holidays can be added, edited, deleted, and listed using the APIs provided. Please take a look at the swagger DOC for more detailed insights.
<br> The user can ideally use the UI application to request holidays in **DRAFT** and **REQUESTED** status. Later the application makes use of two schedulers to change the status to **SCHEDULED** and **ARCHIVED**

### Workflow
1. Choose a user from [http://localhost:4200](http://localhost:4200/users) (after running the Angular UI app)
2. User 'Add new holiday' button to create a new request.
3. User can add a new entry with either 'DRAFT' or 'REQUESTED' status from the popup.
4. Use the buttons 'Edit' or 'Cancel Holiday' to change the edit or delete the entry respectively.
5. Two schedulers are designed for changing the status of the holidays
    - First scheduler will run on a specific interval to iterate through all **'REQUESTED'** entries and convert to **'SCHEDULED'** if the start date matches the current date
    - Second scheduler will run on a specific interval to iterate through all **'SCHEDULED'** entries and convert to **'ARCHIVED'** if the end date exceeded the current date

# Run Local
`mvn clean install`<br/>
`mvn spring-boot:run`

# Testing
`mvn test`
