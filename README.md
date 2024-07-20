# Java_Proxy_Server
This is the proxy server configurations for managing the client requests before they can reach the Springboot

## Add GeoIP2 Dependency
Add the GeoIP2 library to your pom.xml:

xml
Copy code
<dependency>
    <groupId>com.maxmind.geoip2</groupId>
    <artifactId>geoip2</artifactId>
    <version>2.15.0</version>
</dependency>


## Configure GeoIP2 Database
Download the GeoLite2 City database from MaxMind and place it in your resources directory.

4. Implement the Proxy Logic
Application Properties
Configure the URL of your actual Spring Boot application in application.properties:


proxy.target.url=http://localhost:8080


## Application Properties Update
Make sure to add the geolite2.db.location property to your application.properties:

properties
Copy code
geolite2.db.location=GeoLite2-City.mmdb
