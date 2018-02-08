# CSYE 6225 Spring 2018 Starter Repository
###Team Members:
Rui
Tianjiao
Yi

### Objectives
### Setup GitHub Repository
Create a GitHub repository for assignments. This must be a private repository that only your team, TAs and instructor can access.
GitHub repository name must be csye6225-spring2018.
Add all TAs and me to your GitHub repository as collaborators. Our emails can be found on home page.
Set up README.md in your repository. Here is a very good template. You will not need everything from the template. Your readme file must contain following at the minimum:
Team member information such as Name and Email address.
Prerequisites for building and deploying your application locally.
Build and Deploy instructions for web application.
Instructions to run unit, integration and/or load tests.
Link to TravisCI build for the project.
### User Stories
As a user, I want to create an account by providing following information.
Email Address
Password
As a user, I expect to use my email address as my username.
As a user, when I want to navigate to home page (/) and I am not logged in, I should see Login/Signup options. If I am logged in, the page should display current date and time and option to logout.
As a user, if I try to create an account and it already exists, system should warn me that account already exists.
As a user, I expect my password to be stored securely using BCrypt password hashing scheme with salt.
As a user, I expect the code quality of the application to be maintained to highest standards using unit and/or integration tests.


a.	Team member

Rui Sun   rui.sun@northeastern.edu

Yi Chai   chai.yi@northeastern.edu

Tianjiao Xue   xue.ti@northeastern.edu

b.	Prerequisite for building and deploying application locally

1)	Our local application is built on Ubuntu 16.04 LTS. Please make sure if you run it on virtual machine which enough RAM (4GB), CPU (1-2) and disk(50GB).

2)	Please install Java 8, MySQL on your Ubuntu development virtual machine.

3)	You can use IntelliJ as IDE as you want.


c.	Build and deploy for application

1)	Our application is written in Java based on Spring Boot framework. 

2)	We use Persistent database (MySQL) connected by Hibernate



d.	Unit, integration and load test

We use Apache JMeter to do load test

Junit to unit test


e.	TravisCI build for the project

add .yml file 


