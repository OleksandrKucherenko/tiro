# tiro

Simplest Java Web Application, Gradle + Jetty + EAR + WAR + JPA + RESTfull

## Purposes

- create sample for future web applications development
- create minimalistic structure for running Web Application on Jetty server
- pack WAR, EAR files during compilation
- 'Hello World' restfull service
- minimalistic microservice
- testing of different frameworks

## Features

- GRADLE build system
- Dependencies Update Report
    - ```./gradlew dU```
- Jetty Farm
- Minimalistic RESTfull service
- Minimalistic Web Application
- TDD Unit Testing (jUnit)

## Architecture

- Web Application Server should stay behind the NGINX;
- Web Application Server implements only HTTP protocol, NGINX takes HTTPS side;
- NGINX should take responsibilities of: compression, security, caching; 
- Web Application Server implements JSON, ByteBuffer etc. - additional data transfer types;
- Implement common Group/User/Role security pattern, that is often a start point for all solutions

### Persistence

- JavaX Persistence API used for working with data entities and become agnostic to DB implementation
    - Hibernate used as a concrete implementation of JPA
- Will be used MySQL instance for storing the data in production (most common case in our life):
    - used sqlite in memory database for unit tests running
- Tutorials:
    - http://www.vogella.com/tutorials/JavaPersistenceAPI/article.html
    - http://www.srccodes.com/p/article/7/Annotation-based-Hibernate-Hello-World-example-using-Maven-build-tool-and-SQLite-database
- Hibernate Documentations:
    - https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/index.html

## Performance

- https://github.com/smallnest/Jax-RS-Performance-Comparison
- http://www.asjava.com/jetty/jetty-vs-tomcat-performance-comparison/
- http://menelic.com/2016/01/06/java-rest-api-benchmark-tomcat-vs-jetty-vs-grizzly-vs-undertow/
- https://webtide.com/why-choose-jetty/
- https://ibmadvantage.com/2015/09/22/lightweight-java-servers-and-developer-view-on-the-app-server-update/

## RESTfull Frameworks

- http://resteasy.jboss.org/
    - [RESTFul Web Services for Java](http://docs.jboss.org/resteasy/docs/3.0.16.Final/userguide/html_single/index.html)
- http://vertx.io/

## Testing/Validation:

- Validate persistence.xml file: ```./gradlew checkPersistenceXml```
- Run unit tests: ```./gradlew test```
- jUnit tests for low-level system tests (frameworks integration)
- Cucumber for high-level business rules

## Debugging

- Attach IntelliJ debugger to port 5005 (default port)
    - do configuration according to images:
    ![Remote Debugger](_documentation_/intellij-remote-debug-configuration.png)
    ![Port Waiter](_documentation_/intellij-port-waiter-tool.png)
- Run the configuration in IntelliJ
- Run in terminal ```./gradlew farmRunDebugServer```
    ![Console Output](_documentation_/intellij-console-output.png)

### What happens? How it works?

- When web server will be ready to start the code it will open debug port 5005
- Our script is waiting for that (waiting for 5005 port opening)
    - all thanks goes to this line: ```nc localhost 5005```

## Deployment

- Getty plugin used: http://akhikhl.github.io/gretty-doc/Feature-overview.html
- Deploy ready for XCOPY and run solution with Jetty:
    - Run command: ```./gradlew buildProduct```

## All references

- https://www.nginx.com/resources/admin-guide/compression-and-decompression/
- https://nginx.org/en/docs/http/configuring_https_servers.html
- https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-with-ssl-as-a-reverse-proxy-for-jenkins
- https://github.com/ziroby/jetty-gradle-hello-world
- https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/index.html
- http://www.vogella.com/tutorials/JavaPersistenceAPI/article.html
- http://www.srccodes.com/p/article/7/Annotation-based-Hibernate-Hello-World-example-using-Maven-build-tool-and-SQLite-database
- http://akhikhl.github.io/gretty-doc/Feature-overview.html
- https://github.com/marc0der/gradle-spawn-plugin
