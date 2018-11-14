# RideShare

**TravisCI Build Status for Master:** [![Build Status](https://travis-ci.com/ECSE321-Fall2018/t08.svg?token=atEt1SppUvzajjRzBkhC&branch=master)](https://travis-ci.com/ECSE321-Fall2018/t08)

**Heroku Website:** rideshare08.herokuapp.com

**Heroku dashboard:** heroku.com

**Our wiki:** https://github.com/ECSE321-Fall2018/t08/wiki

## Main Components
* Spring Boot Backend (RESTful API)
  * Maven Build System
  * JPA
  * Heroku (hosting backend and PostgreSQL database)
  * JUnit and Mockito

* Android Passenger Application
  * Multi-Activity Application
  * Includes Master/Detail Flow, DatePickerDialog, TimePickerDialog, etc...
  * Calls backend API endpoints
  * JUnit and Mockito
  * Gradle Build System
  
* Android Driver Application
  * Multi-Activity Application
  * Includes Master/Detail Flow, DatePickerDialog, TimePickerDialog, etc...
  * Calls backend API endpoints
  * JUnit and Mockito
  * Gradle Build System
  
* TravisCI Build Matrix
