# tiro

Simplest Java Web Application, Gradle + Jetty + EAR + WAR

## Purposes

- create sample for future web applications development
- create minimalistic structure for running Web Application on Jetty server
- pack WAR, EAR files during compilation
- 'Hello World' restfull service
- minimalistic microservice
- testing of different frameworks

## Features

- GRADLE build system
- Dependenies Update Report
- Jetty Farm
- Minimalistic RESTfull service
- Minimalistic Web Application
- Minimalistic Unit Testing (jUnit)

## Architecture

- Web Application Server should stay behind the NGINX;
- Web Application Server implements only HTTP protocol, NGINX takes HTTPS side;
- NGINX should take responsibilities of: compression, security, caching; 
- Web Application Server implements JSON, ByteBuffer etc. - additional data transfare types;

## References

https://www.nginx.com/resources/admin-guide/compression-and-decompression/

https://nginx.org/en/docs/http/configuring_https_servers.html

https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-with-ssl-as-a-reverse-proxy-for-jenkins
