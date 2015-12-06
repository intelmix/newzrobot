NewzRobot server

After compile and pacakge, use this command to run the application:

`java -Djava.security.egd=file:/dev/./urandom -jar target/newzrobot-server.jar`

Note: Because of some unknown problems, you will need to specify source of random number generation (used to generate session IDs) when running the app.
