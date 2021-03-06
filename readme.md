Has been fixed since Spring Boot 2.6.7

# Spring Boot and Security 2.7.0-SNAPSHOT

Having a custom exception entry point (that returns 401) and a context path set causes Spring Security to
display a white page instead of a 401 page even if `.antMatchers("/error").permitAll()` has been set.

## How to reproduce

* Build with Maven and Java 17.
* Run
* Goto http://localhost:8080/example
* Click on the link
* A white page is displayed

## Workarounds
1. Dowgrade to Spring Boot 2.5.8
2. explictly add context path to permitted AntMatchers: `.antMatchers("/example/error").permitAll()`

