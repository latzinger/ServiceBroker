# Service Broker

The implementation was part of the lecture Enterprise Programming.
The task was to build a service broker which, according to the Open Service Broker API,  
triggers the deployment of backend services (PostgreSQL, Redis and Co.) via Helm on a Kubernetes cluster.

## Supported Services
* Redis
* Postgres

## Service Catalog Options

| Option     | Description    |
| ------------- | ------------- |
| Small  | single small container with low resources |
| Standard | single container with many resources |
| Cluster | clusters of containers |

## Installation/Usage

Configure Database: [application-default.yml](https://github.com/latzinger/ServiceBroker/blob/master/servicebroker/src/main/resources/application-default.yml)

Build Project:
```
mvn clean install
```

## Authentication Methods
1. permit all
2. inMemory Authentication
3. Database Authentication

###application-default.yml:

```properties
osb-security.permit-all = true
osb-security.use-db = false
osb-security.username = epro
osb-security.password = epro
```

when both permit-all and use-db equals false, inMemory Authentication will be used!

## Built With

* [Spring](https://spring.io) - Spring Framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com) - Container Virtualization
* [Kubernetes](https://kubernetes.io) - Container Orchestration
* [Helm](https://helm.sh) - Package Manager for Kubernetes

## Testing

* [OSB-Checker](https://github.com/evoila/osb-checker-kotlin) - Testing Framework by evoila GmbH

use *[OSB-Checker application.yml](https://bitbucket.org/jhueg/epro-projekt/src/develop/testing/osb-checker/application.yml)* for minimal testing.

* [Postman](https://www.getpostman.com) - API Development Environment

use our *[Postman-Collection](https://bitbucket.org/jhueg/epro-projekt/src/develop/testing/postman/ServiceBroker.postman_collection.json)* for testing.


## Useful Links

* [OpenServiceBrokerAPI](https://www.openservicebrokerapi.org) - Open Service Broker API
* [SwaggerUI](http://petstore.swagger.io/?url=https://raw.githubusercontent.com/openservicebrokerapi/servicebroker/v2.14/openapi.yaml) - Open Service Broker API
* [CloudFoundry](https://docs.cloudfoundry.org/services/overview.html) - Overview CloudFoundry

## Authors

* **Jonas Hueg** - *Main work* - [jhueg](https://github.com/jhueg)
* **Lars Atzinger** - *Main work* - [larsatzinger](https://github.com/latzinger)
