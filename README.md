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
**fasttrack-assignment** application is consists of the following workflow
### Employee Management
Use the PUT API provided to add Employees by giving the employee name. Application uses `com.airfranceklm.fasttrack.assignment.dao.generator.PrimaryKeyGenerator` to create the unique Employee ID with the format **klmXXXXXX**.
Other GET APIs can be used to retrieve the employee details as well.

### Holiday Management
Holidays can be added, edited, deleted and listed using the APIs provided. Please go through the swagger DOC for more detailed insights.
<br> The user can ideally use the UI application to request for holidays in **DRAFT** and **REQUESTED** status. Later the application make use of two schedulers to change the status to **SCHEDULED** and **ARCHIVED**

# Run Local
`mvn clean install`<br/>
`mvn spring-boot:run`

# Testing
`mvn test`
