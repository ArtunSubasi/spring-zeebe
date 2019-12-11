# Spring Zeebe

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.zeebe.spring/spring-zeebe/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.zeebe.spring/spring-zeebe)

[![Build Status](https://travis-ci.org/zeebe-io/spring-zeebe.svg?branch=master)](https://travis-ci.org/zeebe-io/spring-zeebe)
[![codecov](https://codecov.io/gh/zeebe-io/spring-zeebe/branch/master/graph/badge.svg)](https://codecov.io/gh/zeebe-io/spring-zeebe)
[![Project Stats](https://www.openhub.net/p/spring-zeebe/widgets/project_thin_badge.gif)](https://www.openhub.net/p/spring-zeebe)

This project allows to leverage Zeebe within your Spring or Spring Boot environment easily. It is basically a wrapper around the [Zeebe Java Client](https://docs.zeebe.io/java-client/).



# How to use

## Connect to Zeebe Broker

Just add the `@EnableZeebeClient` annotation to your Spring Boot Application:

```
@SpringBootApplication
@EnableZeebeClient
@ZeebeDeployment(classPathResource = "demoProcess.bpmn")
public class MySpringBootApplication {
```

## Deploy Workflow Models

Use the `@ZeebeDeployment` annotation:

```
@SpringBootApplication
@EnableZeebeClient
@ZeebeDeployment(classPathResource = "demoProcess.bpmn")
public class MySpringBootApplication {
```

## Implement Job Worker

```
@ZeebeWorker(type = "foo")
public void handleJobFoo(final JobClient client, final ActivatedJob job) {
  // do whatever you need to do
  client.newCompleteCommand(job.getKey()) 
     .variables("{\"fooResult\": 1}")
     .send().join();
}
```

## Configuring Zeebe Connection

```
zeebe.worker.name="foo-worker"
zeebe.client.broker.contactPoint=127.0.0.1:26500
zeebe.client.security.plaintext=true
```

For a full set of configuration options please see [ZeebeClientConfigurationProperties.java](blob/master/client/spring-zeebe-starter/src/main/java/io/zeebe/spring/client/properties/ZeebeClientConfigurationProperties.java)

## Configuring Camunda Cloud Connection

This example uses a yaml-based configuration:
```yaml
zeebe:
  worker:
    name: foo-worker
  client:
    broker:
      contactPoint: your-cluster-id.zeebe.camunda.io:443
    security:
      clientId: your-client-id
      clientSecret: your-client-secret
      audience: your-cluster-id.zeebe.camunda.io
```

## Add Spring Boot Starter to Your Project

Just add the following Maven dependency to your Spring Boot Starter project:

```
<dependency>
	<groupId>io.zeebe.spring</groupId>
	<artifactId>spring-zeebe-starter</artifactId>
	<version>${CURRENT_VERSION}</version>
</dependency>
```

## Examples

Have a look into the [examples/](examples/) folder for working Maven projects that might serve as inspiration.

# Code of Conduct

This project adheres to the Contributor Covenant [Code of
Conduct](/.github/CODE_OF_CONDUCT.md). By participating, you are expected to uphold
this code. Please report unacceptable behavior to
code-of-conduct@zeebe.io.
