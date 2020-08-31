# movie-reactor-backend

## What is it?
This project is a continuation of the Movie Reactor Application (please find it at 
https://github.com/balintsimon/movie-reactor-application) which started out as a React demo project, using
"The MovieDb API" (find more information at https://developers.themoviedb.org/3/getting-started/introduction), 
providing backend services either for the original frontend or otherwise.

The current version of the project is an exercise to rewrite the original monolithic backend service using
microservices. Servers:
- `api-gateway` on port 8762: Netflix Zuul API Gateway, containing visitor service
- `booking` on port 8201: manages seat reservations for shows (N.B.: depends upon visitor id, show id and show id)
- `cinema` on port 8101: contains information on movie theater room and their seats
- `eureka` on port 8761: Netflix Eureka server discovery
- `moviecatalog` on port 8091: contains information on shows (N.B.: depends upon movie id and room id)
- `movieservice` on port 8081: contains the movies that can be on the agenda

## How to start the servers?
Please install dependencies for each server (find the corresponding pom.xml).

The project uses H2 database as default. Please provide environment variable value for `DB_URL` for each database 
(e.g. in the form `DB_URL=jdbc:h2:~/[directory location]`).

Also note that the project - for demonstration purposes - uses dummy content through `CommandLineRunner`. As data
must be read from some services at aggregate points, please:
- start `moviecatalog` service after `cinema`
- start `booking` after all other services have started

## API
Please note that endpoints are in the process of being rewritten at the moment to comply with REST.

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
- enable Spring Security on endpoints
- move dummy content creation under separate service or flag