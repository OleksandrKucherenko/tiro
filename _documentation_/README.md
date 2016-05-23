# Roadmap

I will try to follow traditional way of releases: ```Alpha --> Beta --> Release Candidate --> Release```.

## Version 1.0 alpha

### Select the frameworks:
  - ~~the code library, shared code~~
  - ~~the resources library, i18n~~
  - ~~Database entities~~

### Testing:
  - ~~unit testing of the code, low level class tests~~, jUnit/Mockito/AssertJ
  - ~~unit testing for API, REST api~~, in memory grizzly
  - ~~unit testing of requirements, high level language~~, Cucumber

### REST API:
  - ~~skeleton of the REST service~~
  - ~~versioning of the API~~
  - ~~JSON (de-)serialization~~
  - design REST API for all entities
  - common scenario: ```Authenticate --> Get Roles / Get Groups / Get Users```

### Data storage:
  - ~~DB independent, can select any implementation~~
  - ~~in memory database for unit testing~~

### web server / hosting:
  - ~~easy deployment~~
  - ~~web server independent~~

### Easy developer environment:
  - ~~command line for building the solution, gradle~~
  - ~~automated build and testing~~
  - ~~code coverage reports~~

### Performance
  - ~~define approach for testing the performance of the server~~, Apache Benchmark Tool

## Version 1.0 beta
  - Database:
    - MySQL binding configuration
  - Easy developer environment:
  - oAuth2 for REST API access
  - Web Pages, Admin pages for entities (New/Edit/Drop)
  - compose script to automatic benchmarking

## Version 1.0 RC1
  - Database, switch solution to MySQL
  - Hibernate L2 Cache

## Version 1.0
  - Caching
    -  memcache or similar solution
  - Health Tracking:
    - metrics of the solution:
      - define core performance/resources metrics
      - define core health metrics
      - define core business metrics
  - Deployment:
    - Docker image
    - Docker solution: NGINX, Server, MySQL
    - Be ready for google cloud deployment
