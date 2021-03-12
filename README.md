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

## Weekly Scrum Report
> ### Week 1 (03/02/2020 – 03/08/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Explore on how to use the AWS RDS as database for our application.
##### What am I planning to work on next?
Discuss with team on database schema.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Explore how to use AWS for the Spring application.
##### What am I planning to work on next?
Discuss with team on database schema.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Explore the Auto scaling feature of the AWS.
##### What am I planning to work on next?
Discuss with team on database schema.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Requirement gathering and analysis of the requirement to create basic design of the applilcation.
##### What am I planning to work on next?
Discuss with team on database schema.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 2 (03/09/2020 – 03/15/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Discussed and designed the schema for the application.
##### What am I planning to work on next?
Create basic structure of the Spring Application and implement APIs for the user registration and login.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Discussed and designed the schema for the application. Created database schema on cloud.
##### What am I planning to work on next?
Design and develop UI for managing vehicle types.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Discussed and designed the schema for the application.
##### What am I planning to work on next?
Create UI for Login and registration.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Discussed and designed the schema for the application.
##### What am I planning to work on next?
Implement backend to manage vehicle types.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 3 (03/16/2020 – 03/22/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Not able to work due to midterm.
##### What am I planning to work on next?
Create basic structure of the Spring Application and implement APIs for the user registration and login.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Did not do anything due to midterm.
##### What am I planning to work on next?
Design and develop UI for managing vehicle types.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Not able to do anything.
##### What am I planning to work on next?
Create UI for Login and registration.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Not able to complete the work due to midterm.
##### What am I planning to work on next?
Implement backend to manage vehicle types.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 4 (03/23/2020 – 03/29/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Created basic structure for the Spring boot application and created registration and login API. Still need to implement the user roles.
##### What am I planning to work on next?
Implement backend to manage rental locations.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Developed UI to manage vehicle types and prices.
##### What am I planning to work on next?
Develop UI for rental locations.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Created basic structure for the frontend application in React and implemented UI for user registration and login.
##### What am I planning to work on next?
Develop UI for vehicles.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Implemented APIs to manage vehicle types.
##### What am I planning to work on next?
Implement backend to manage vehicles.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 5 (03/30/2020 – 04/05/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Implemented CRUD operations for the user membership.
##### What am I planning to work on next?
Add user roles in spring security.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Not able to complete anything.
##### What am I planning to work on next?
Develop UI for rental locations.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Implemented UI to manage vehicles.
##### What am I planning to work on next?
Develop UI to manage membership.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Did not complete anything.
##### What am I planning to work on next?
Implement APIs to manage locations as well as vehicles.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 6 (04/06/2020 – 04/12/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Added user roles of admin and customer in the spring security.
##### What am I planning to work on next?
Add APIs for rental locations.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Did not worked on anything.
##### What am I planning to work on next?
Create UI for locations.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Not able to complete anything.
##### What am I planning to work on next?
Create UI to manage memberships.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Implemented backend for vehicles and vehicle locations.
##### What am I planning to work on next?
Implement APIs for reservations.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 7 (04/13/2020 – 04/19/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Implemented APIs for rental locations.
##### What am I planning to work on next?
Test Application
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Implemented UI for locations, to cancel reservations.
##### What am I planning to work on next?
Implement UI for vehicle location.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Implemented UI to manage memberships.
##### What am I planning to work on next?
UI for return vehicle and cancel reservation.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Implemented APIs for reservations and suggestions 
##### What am I planning to work on next?
Vehcile location backend
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 8 (04/20/2020 – 04/26/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Testing
##### What am I planning to work on next?
Testing
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Implemented vehicle locations and terminate membership from admin side
##### What am I planning to work on next?
Testing
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Added views for cancel and return vehicle
##### What am I planning to work on next?
Testing
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Testing
##### What am I planning to work on next?
Testing
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 9 (04/27/2020 – 05/03/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Nothing to do.
##### What am I planning to work on next?
Documentation and testing.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Testing
##### What am I planning to work on next?
Documentations and testing.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
Nothing to do.
##### What am I planning to work on next?
Documentation and AWS cloud deployment with auto scale.
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Changes in Vehicle location.
##### What am I planning to work on next?
Documentation.
##### What tasks are blocked waiting on another team member?
No blocker.

> ### Week 10 (05/04/2020 – 05/08/2020)
#### Farhan Bhoraniya
##### What tasks did I work on / complete?
Documentation
##### What am I planning to work on next?
NA
##### What tasks are blocked waiting on another team member?
No blocker.
#### Manan Shah
##### What tasks did I work on / complete?
Documentation
##### What am I planning to work on next?
NA
##### What tasks are blocked waiting on another team member?
No blocker.
#### Dharmang Solanki
##### What tasks did I work on / complete?
AWS cloud deployment with auto scaling.
##### What am I planning to work on next?
NA
##### What tasks are blocked waiting on another team member?
No blocker.
#### Inderjit Bassi
##### What tasks did I work on / complete?
Documentation
##### What am I planning to work on next?
NA
##### What tasks are blocked waiting on another team member?
No blocker.


## XP Core Value
### Simplicity
As a team, we believed in the simplicity. So we tried to start with the simple things instead of trying to build the complex things that we might not be able to complete in time. We took small steps and tried implement features one afte another. By doing that we have created the product that is easy to use for user and easy to maintain for developers.

### Authors 
Farhan Bhoraniya
Manan Shah
Dharmang Solanki
Inderjit Bassi
