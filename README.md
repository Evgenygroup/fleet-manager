# Fleet Management System Project

## Project description

An application that stores vehicles information into the fleet management system with a possibility 
to add/update/fetch/delete vehicles. The following details of a vehicle are stored: name, vin, license plate number
and properties (any further information such as color, number of doors, number of wheels etc).

The application uses Java 11, Gradle, Springboot as well as Docker database MySQL. The entire application can be 
deployed on a local machine.

To run the project you need to have installed [Docker](http://docker.com).

### Install Docker on Ubuntu:
https://docs.docker.com/engine/install/ubuntu/
### Install Docker on Windows:
https://docs.docker.com/toolbox/toolbox_install_windows/
### Install Docker on MacOS:
https://docs.docker.com/toolbox/toolbox_install_mac/

## Run the project

Run in the project directory:
```
docker-compose up --build -d
```
Once the application is up and running its endpoints will be exposed at http://localhost:8080/swagger-ui.html

