# Dependencies in Project

Jetty server Architecture:
http://www.eclipse.org/jetty/documentation/9.3.x/architecture.html

## tiro

* [Jetty](http://www.eclipse.org/jetty/documentation/current/) - web/app server. Jetty is an open-source project providing an HTTP server, HTTP client, and javax.servlet container.

## EAR

Enterprise ARchive (EAR), combination of `war-api`, `war-tasks` & `war-time` modules.

## lib-core

* SLF4J (1.7.21) - logging. Used with additional helper libraries:
  * LOG4J-over-SLF4J,
  * JSL-over-SLF4J,
  * JUL-over-slf4j
  * Logback as a logs engine
* Hibernate JPA (1.0.0.Final):
  * Hibernate Entity Manager (5.1.0.Final)
* Jackson Annotations (2.8.0.rc2) - markup of POJOs to JSON
* Findbugs Annotations JSR305 (3.0.1) - code stability improvements

### Testing

* Jersey Container Servlet
  * Jersey Media JSON Jackson
* jUnit
  * Mockito
  * AssertJ
  * Reflections
* Prettytime - print time in human readable format
* Grizzly2 Test Framework Provider

## war-api

* Lib-Core
* Jersey (2.23.1):
  * Jersey Container Servlet (2.23.1)
  * Jersey Server - core of the framework
* Jersey Media Jackson - serialization of POJOs over the Jackson for Jersey
* JavaX Standards:
  * RS API, also known as [JAX-RS](https://jax-rs-spec.java.net/) - REST services Support
  * Servlet API, [Specification](https://java.net/projects/servlet-spec/) - Servlets Support
  * WebSocket API, [Specification](https://java.net/projects/websocket-spec/) - WebSocket Endpoint Support

### Inner dependencies

* [HK2](https://hk2.java.net/2.5.0-b05/index.html) - A light-weight and dynamic dependency injection framework. Used by Jersey.

### But ...

* WebSockets - attached only API, not an implementation. Implementation can be used [Tyrus](https://tyrus.java.net/) project.

```
```


Runtime
```
+--- project :lib-core
|    +--- org.slf4j:slf4j-api:+ -> 1.7.21
|    +--- org.slf4j:log4j-over-slf4j:+ -> 1.7.21
|    |    \--- org.slf4j:slf4j-api:1.7.21
|    +--- org.slf4j:jcl-over-slf4j:+ -> 1.7.21
|    |    \--- org.slf4j:slf4j-api:1.7.21
|    +--- org.slf4j:jul-to-slf4j:+ -> 1.7.21
|    |    \--- org.slf4j:slf4j-api:1.7.21
|    +--- ch.qos.logback:logback-classic:+ -> 1.1.7
|    |    +--- ch.qos.logback:logback-core:1.1.7
|    |    \--- org.slf4j:slf4j-api:1.7.20 -> 1.7.21
|    +--- org.hibernate.javax.persistence:hibernate-jpa-2.1-api:+ -> 1.0.0.Final
|    +--- com.google.code.findbugs:jsr305:+ -> 3.0.1
|    +--- com.fasterxml.jackson.core:jackson-annotations:+ -> 2.8.0.rc2
|    \--- org.hibernate:hibernate-entitymanager:+ -> 5.1.0.Final
|         +--- org.jboss.logging:jboss-logging:3.3.0.Final
|         +--- org.hibernate:hibernate-core:5.1.0.Final
|         |    +--- org.jboss.logging:jboss-logging:3.3.0.Final
|         |    +--- org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final
|         |    +--- org.javassist:javassist:3.20.0-GA
|         |    +--- antlr:antlr:2.7.7
|         |    +--- org.apache.geronimo.specs:geronimo-jta_1.1_spec:1.1.1
|         |    +--- org.jboss:jandex:2.0.0.Final
|         |    +--- com.fasterxml:classmate:1.3.0
|         |    +--- dom4j:dom4j:1.6.1
|         |    |    \--- xml-apis:xml-apis:1.0.b2
|         |    \--- org.hibernate.common:hibernate-commons-annotations:5.0.1.Final
|         |         \--- org.jboss.logging:jboss-logging:3.3.0.Final
|         +--- dom4j:dom4j:1.6.1 (*)
|         +--- org.hibernate.common:hibernate-commons-annotations:5.0.1.Final (*)
|         +--- org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final
|         +--- org.javassist:javassist:3.20.0-GA
|         \--- org.apache.geronimo.specs:geronimo-jta_1.1_spec:1.1.1
+--- org.glassfish.jersey.containers:jersey-container-servlet:+ -> 2.23.1
|    +--- org.glassfish.jersey.containers:jersey-container-servlet-core:2.23.1
|    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    +--- org.glassfish.jersey.core:jersey-common:2.23.1
|    |    |    +--- javax.ws.rs:javax.ws.rs-api:2.0.1
|    |    |    +--- javax.annotation:javax.annotation-api:1.2
|    |    |    +--- org.glassfish.jersey.bundles.repackaged:jersey-guava:2.23.1
|    |    |    +--- org.glassfish.hk2:hk2-api:2.4.0-b34
|    |    |    |    +--- javax.inject:javax.inject:1
|    |    |    |    +--- org.glassfish.hk2:hk2-utils:2.4.0-b34
|    |    |    |    |    \--- javax.inject:javax.inject:1
|    |    |    |    \--- org.glassfish.hk2.external:aopalliance-repackaged:2.4.0-b34
|    |    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    |    +--- org.glassfish.hk2:hk2-locator:2.4.0-b34
|    |    |    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    |    |    +--- org.glassfish.hk2.external:aopalliance-repackaged:2.4.0-b34
|    |    |    |    +--- org.glassfish.hk2:hk2-api:2.4.0-b34 (*)
|    |    |    |    +--- org.glassfish.hk2:hk2-utils:2.4.0-b34 (*)
|    |    |    |    \--- org.javassist:javassist:3.18.1-GA -> 3.20.0-GA
|    |    |    \--- org.glassfish.hk2:osgi-resource-locator:1.0.1
|    |    +--- org.glassfish.jersey.core:jersey-server:2.23.1
|    |    |    +--- org.glassfish.jersey.core:jersey-common:2.23.1 (*)
|    |    |    +--- org.glassfish.jersey.core:jersey-client:2.23.1
|    |    |    |    +--- javax.ws.rs:javax.ws.rs-api:2.0.1
|    |    |    |    +--- org.glassfish.jersey.core:jersey-common:2.23.1 (*)
|    |    |    |    +--- org.glassfish.hk2:hk2-api:2.4.0-b34 (*)
|    |    |    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    |    |    \--- org.glassfish.hk2:hk2-locator:2.4.0-b34 (*)
|    |    |    +--- javax.ws.rs:javax.ws.rs-api:2.0.1
|    |    |    +--- org.glassfish.jersey.media:jersey-media-jaxb:2.23.1
|    |    |    |    +--- org.glassfish.jersey.core:jersey-common:2.23.1 (*)
|    |    |    |    +--- org.glassfish.hk2:hk2-api:2.4.0-b34 (*)
|    |    |    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    |    |    +--- org.glassfish.hk2:hk2-locator:2.4.0-b34 (*)
|    |    |    |    \--- org.glassfish.hk2:osgi-resource-locator:1.0.1
|    |    |    +--- javax.annotation:javax.annotation-api:1.2
|    |    |    +--- org.glassfish.hk2:hk2-api:2.4.0-b34 (*)
|    |    |    +--- org.glassfish.hk2.external:javax.inject:2.4.0-b34
|    |    |    +--- org.glassfish.hk2:hk2-locator:2.4.0-b34 (*)
|    |    |    \--- javax.validation:validation-api:1.1.0.Final
|    |    \--- javax.ws.rs:javax.ws.rs-api:2.0.1
|    +--- org.glassfish.jersey.core:jersey-common:2.23.1 (*)
|    +--- org.glassfish.jersey.core:jersey-server:2.23.1 (*)
|    \--- javax.ws.rs:javax.ws.rs-api:2.0.1
+--- org.glassfish.jersey.media:jersey-media-json-jackson:+ -> 2.23.1
|    +--- org.glassfish.jersey.core:jersey-common:2.23.1 (*)
|    +--- org.glassfish.jersey.ext:jersey-entity-filtering:2.23.1
|    |    \--- javax.ws.rs:javax.ws.rs-api:2.0.1
|    +--- com.fasterxml.jackson.jaxrs:jackson-jaxrs-base:2.5.4
|    |    +--- com.fasterxml.jackson.core:jackson-core:2.5.4 -> 2.8.0.rc2
|    |    \--- com.fasterxml.jackson.core:jackson-databind:2.5.4 -> 2.8.0.rc2
|    |         +--- com.fasterxml.jackson.core:jackson-annotations:2.8.0.rc1 -> 2.8.0.rc2
|    |         \--- com.fasterxml.jackson.core:jackson-core:2.8.0.rc2
|    +--- com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:2.5.4
|    |    +--- com.fasterxml.jackson.jaxrs:jackson-jaxrs-base:2.5.4 (*)
|    |    +--- com.fasterxml.jackson.core:jackson-core:2.5.4 -> 2.8.0.rc2
|    |    +--- com.fasterxml.jackson.core:jackson-databind:2.5.4 -> 2.8.0.rc2 (*)
|    |    \--- com.fasterxml.jackson.module:jackson-module-jaxb-annotations:2.5.4
|    |         +--- com.fasterxml.jackson.core:jackson-core:2.5.4 -> 2.8.0.rc2
|    |         \--- com.fasterxml.jackson.core:jackson-databind:2.5.4 -> 2.8.0.rc2 (*)
|    \--- com.fasterxml.jackson.core:jackson-annotations:2.5.4 -> 2.8.0.rc2
+--- com.fasterxml.jackson.core:jackson-databind:+ -> 2.8.0.rc2 (*)
+--- javax.servlet:javax.servlet-api:3.1.0
\--- javax.websocket:javax.websocket-api:1.0
```

JaCoCo
```
\--- org.jacoco:org.jacoco.ant:+ -> 0.7.7.201606060606
     +--- org.jacoco:org.jacoco.core:0.7.7.201606060606
     |    \--- org.ow2.asm:asm-debug-all:5.1
     +--- org.jacoco:org.jacoco.report:0.7.7.201606060606
     |    +--- org.jacoco:org.jacoco.core:0.7.7.201606060606 (*)
     |    \--- org.ow2.asm:asm-debug-all:5.1
     \--- org.jacoco:org.jacoco.agent:0.7.7.201606060606
```