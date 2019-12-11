package io.zeebe.spring.client.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(
  properties = {
    "zeebe.client.broker.contactPoint=localhost12345",
    "zeebe.client.requestTimeout=99s",
    "zeebe.client.job.worker=testName",
    "zeebe.client.job.timeout=99s",
    "zeebe.client.job.pollInterval=99s",
    "zeebe.client.worker.maxJobsActive=99",
    "zeebe.client.worker.threads=99",
    "zeebe.client.message.timeToLive=99s",
    "zeebe.client.security.certpath=aPath",
    "zeebe.client.security.plaintext=true",
    "zeebe.client.security.clientId=someId",
    "zeebe.client.security.clientSecret=someSecret",
    "zeebe.client.security.audience=someAudience",
    "zeebe.client.security.authorizationServerUrl=https://auth",
    "zeebe.client.security.credentialsCachePath=$HOME/.camunda/accessTokens"
  }
)
@ContextConfiguration(classes = ZeebeClientSpringConfigurationPropertiesTest.TestConfig.class)
public class ZeebeClientSpringConfigurationPropertiesTest {

  @EnableConfigurationProperties(ZeebeClientConfigurationProperties.class)
  public static class TestConfig {

  }

  @Autowired
  private ZeebeClientConfigurationProperties properties;

  @Test
  public void hasBrokerContactPoint() throws Exception {
    assertThat(properties.getBrokerContactPoint()).isEqualTo("localhost12345");
  }

  @Test
  public void hasRequestTimeout() throws Exception {
    assertThat(properties.getRequestTimeout()).isEqualTo(Duration.ofSeconds(99));
  }

  @Test
  public void hasWorkerName() throws Exception {
    assertThat(properties.getDefaultJobWorkerName()).isEqualTo("testName");
  }

  @Test
  public void hasJobTimeout() throws Exception {
    assertThat(properties.getJob().getTimeout()).isEqualTo(Duration.ofSeconds(99));
  }

  @Test
  public void hasWorkerMaxJobsActive() throws Exception {
    assertThat(properties.getWorker().getMaxJobsActive()).isEqualTo(99);

  }

  @Test
  public void hasJobPollInterval() throws Exception {
    assertThat(properties.getJob().getPollInterval()).isEqualTo(Duration.ofSeconds(99));
  }

  @Test
  public void hasWorkerThreads() throws Exception {
    assertThat(properties.getWorker().getThreads()).isEqualTo(99);
  }

  @Test
  public void hasMessageTimeToLeave() throws Exception {
    assertThat(properties.getMessage().getTimeToLive()).isEqualTo(Duration.ofSeconds(99));
  }

  @Test
  public void isSecurityPlainTextDisabled() throws Exception {
    assertThat(properties.getSecurity().isPlaintext()).isTrue();
  }

  @Test
  public void hasSecurityCertificatePath() throws Exception {
    assertThat(properties.getSecurity().getCertPath()).isEqualTo("aPath");
  }

  @Test
  public void hasSecurityClientId() throws Exception {
    assertThat(properties.getSecurity().getClientId()).isEqualTo("someId");
  }

  @Test
  public void hasSecurityClientSecret() throws Exception {
    assertThat(properties.getSecurity().getClientSecret()).isEqualTo("someSecret");
  }

  @Test
  public void hasSecurityAudience() throws Exception {
    assertThat(properties.getSecurity().getAudience()).isEqualTo("someAudience");
  }

  @Test
  public void hasSecurityAuthorizationServerUrl() throws Exception {
    assertThat(properties.getSecurity().getAuthorizationServerUrl()).isEqualTo("https://auth");
  }

  @Test
  public void hasSecurityCredentialsCachePath() throws Exception {
    assertThat(properties.getSecurity().getCredentialsCachePath()).isEqualTo("$HOME/.camunda/accessTokens");
  }

  @Test
  public void getCredentialsProvider() throws Exception {
    assertThat(properties.getCredentialsProvider()).isNotNull();
  }

}
