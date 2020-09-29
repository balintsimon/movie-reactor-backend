# movie-reactor-backend

## What is it?
This project is a continuation of the Movie Reactor front-end application project (please find it at 
https://github.com/balintsimon/movie-reactor-application). The front-end project started out as a learn-to-use-React 
demo project using "The MovieDb API" (find more information at 
https://developers.themoviedb.org/3/getting-started/introduction) as a data source. The back-end project started later 
and its aim was to provide backend services for the original front-end. The current version of the project 
is an exercise in Spring Boot to rewrite the original monolithic backend service using microservices. As such, it has been built with 
the existing capabilities of the front-end in mind (that is, it should provide all the previously existing features), 
albeit is rewritten from the ground up, rethinking database structure and endpoints.

The aim of this project is to explore how a previously existing monolithic design with a robust database with
inter-dependent tables may transfer to a microservice architecture.

Please note that the project may switch to event-driven architecture.

## Servers
1. `api-gateway` on port 8762: Netflix Zuul API Gateway, containing visitor service
1. `booking` on port 8201: manages seat reservations for shows (N.B.: depends upon visitor id, show id and show id)
1. `cinema` on port 8101: contains information on movie theater room and their seats
1. `eureka` on port 8761: Netflix Eureka server discovery
1. `moviecatalog` on port 8091: contains information on shows (N.B.: depends upon movie id and room id)
1. `movieservice` on port 8081: contains the movies that can be on the agenda

## How to start services?
Please install dependencies for each server (find the corresponding pom.xml).

The project uses H2 database as default. Please provide environment variable value for `DB_URL` for each database 
(e.g. in the form `DB_URL=jdbc:h2:~/[directory location]`).

Also note that the project - for demonstration purposes - uses dummy content through `CommandLineRunner`. As data
must be read from some services at aggregate points, please:
- start `moviecatalog` service after `cinema`
- start `booking` after all other services have started

## API
Please note that endpoints are in the process of being rewritten at the moment to comply with REST.
API at localhost port 8762:

Endpoint | Method | Response
--- | --- | ---
/auth/login | POST | log in existing visitor by request body credentials
/auth/register | POST | register new visitor by request body credentials 
/user | GET | get all users
/user/{id} | GET | get user by ID
/watchlist | GET | get watchlist of logged-in visitor
/watchlist/{movie_id} | POST | add new item to visitor's watchlist by movie item's ID 
/watchlist/{movie_id} | DELETE | delete item from visitor's watchlist by movie item's ID
/booking/reservation | GET | if visitor is logged in, shows visitor's reservation, if admin is logged in it shows all reservations
/booking/reservation | POST | save new reservation for seats of a show
/booking/reservation | DELETE | delete booked reservation for seats of a show
/booking/reservation/user/{id} | GET | get all reservation info of a user by its ID
/booking/reservation/show/{id} | GET | get all reservation info of a show by its ID
/cinema/room | GET | get all information on all rooms
/cinema/room/{id} | GET | get room information by room's ID
/cinema/seat | GET | get all seats information
/cinema/seat/{id} | GET | get seat by its ID
/cinema/seat/room/{id} | GET | get all seats by the room's ID they are in
/moviecatalog/schedule | GET | get currently running shows
/moviecatalog/show | GET | get all shows, irrelevant whether running or not at the moment
/moviecatalog/show/movie | GET | get all movies that has ever been on show
/moviecatalog/show/{id} | GET | get show by its ID
/moviecatalog/show/{id} | PUT | update show, selected by its ID
/moviecatalog/show/{id} | DELETE | delete show, selected by its ID
/movieservice/movie | GET | get all movies that is/has been on show
/movieservice/movie/{id} | GET | get movie that is/has been on show by its ID

Please note that although Spring Security is installed, endpoints have not been secured yet.  

## Version
The project is under development, in beta.

Implemented:
- all servers are running with dummy content initialization
- cinema schedule
- seat reservation
- user (visitor) registration

Under implementation:
- aggregate API endpoint calls to minimize API calls from front-end
- separate visitor users from admins (currently using roles)
- admin functions: register new show instead of watchlist
- admin functions: schedule show from possible movies list
- admin functions: manage each show's reservations
- admin functions: manage users' data
- schedule check for past shows (void registration after start of show)
- store JWT in cookie instead of local.storage
- RESTful API: implement HATEOAS
- RESTful API: change endpoint names to follow naming convention
- enable Spring Security on endpoints
- move dummy content creation under separate service or flag

## Technology
Services use:
- Spring Boot (with Lombok and Spring Security)
- H2 database through Hibernate ORM (where applicable)

If viewed with IDE, please note that the project uses Lombok. If the IDE is not capable to interpret Lombok annotations
it may show errors. In this case and if possible, please install an extension that interprets Lombok annotations for 
the IDE of your choice.
