# Team Project: Car Rental System

A car rental application in which user can rent vehicles for some time as per the need

## Design Decisions  
### Springboot  
Springboot is a popular framework for the REST API development in java. Using Spring in our application allowed us to easily write testable and reusable code. Spring boot requires minimum configurations and use of Hibernate allowed us to easily integrate database with our application.

### MySQL Database hosted on Amazon RDS
Like any application, our application also highly depends on data. Relational database was found more suitable for our application so we used the MYSQL server.
The database instance is hosted on the Amazon RDS. So that all team members can perform development and testing at the same time and also reduces the rework of database setup for every team member.

### Amazon AWS Auto Scaled EC2 Cluster with Load Balancer (Extra Credit)
Auto scaling monitors resources and automatically adjusts the resource allocation to provide reliable performance of application on AWS. This step will help to maintain the performance of the system for every user.

## Feature Set
* Admin Features
     * Add, Update, Delete vehicles
     * Add, Update, Delete locations
     * Add, Update, Delete vehicle type
     * Change vehicle locations
     * Manage price
     * Terminate user membership
* Customer Features
     * Register
     * Manage Profile
     * Search vehicles by location, vehicle type
     * Rent vehicle
     * Manage membership

## Diagrams
![Untitled Document (9)](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-03-team-project-scrum-masters/blob/master/Component%20Diagram.jpg?raw=true)
![Untitled Document (9)](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-03-team-project-scrum-masters/blob/master/Deployment%20Diagram.png?raw=true)

## XP Core Value
### Simplicity
As a team, we believed in the simplicity. So we tried to start with the simple things instead of trying to build the complex things that we might not be able to complete in time. We took small steps and tried implement features one afte another. By doing that we have created the product that is easy to use for user and easy to maintain for developers.

### Authors 
Farhan Bhoraniya
Manan Shah
Dharmang Solanki
Inderjit Bassi
