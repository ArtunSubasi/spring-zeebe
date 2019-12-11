package io.zeebe.spring.client.properties;

import static io.zeebe.spring.client.config.ZeebeClientSpringConfiguration.DEFAULT;

import io.zeebe.client.CredentialsProvider;
import java.time.Duration;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "zeebe.client")
@Slf4j
public class ZeebeClientConfigurationProperties implements ZeebeClientProperties {

  @NestedConfigurationProperty
  private Broker broker = new Broker();

  @NestedConfigurationProperty
  private Worker worker = new Worker();

  @NestedConfigurationProperty
  private Message message = new Message();

  @NestedConfigurationProperty
  private Security security = new Security();

  @NestedConfigurationProperty
  private Job job = new Job();

  private Duration requestTimeout = DEFAULT.getDefaultRequestTimeout();

  @Data
  public static class Broker {
    private String contactPoint = DEFAULT.getBrokerContactPoint();
  }

  @Data
  public static class Worker {
    private Integer maxJobsActive = DEFAULT.getDefaultJobWorkerMaxJobsActive();
    private Integer threads = DEFAULT.getNumJobWorkerExecutionThreads();
  }

  @Data
  public static class Job {
    private String worker = DEFAULT.getDefaultJobWorkerName();
    private Duration timeout = DEFAULT.getDefaultJobTimeout();
    private Duration pollInterval = DEFAULT.getDefaultJobPollInterval();
  }

  @Data
  public static class Message {
    private Duration timeToLive = DEFAULT.getDefaultMessageTimeToLive();
  }

  @Data
  public static class Security {
    private boolean plaintext = DEFAULT.isPlaintextConnectionEnabled();
    private String certPath = DEFAULT.getCaCertificatePath();

    /** The client ID used to request an access token from the authorization server. */
    private String clientId;

    /** The client secret used to request an access token from the authorization server. */
    @ToString.Exclude
    private String clientSecret;

    /** The address for which the token should be valid. */
    private String audience;

    /** The URL of the authorization server from which the access token will be requested. */
    private String authorizationServerUrl;

    /** The path to a cache file where the access tokens will be stored. */
    private String credentialsCachePath;

    public CredentialsProvider getCredentialsProvider() {
      if (clientId != null && clientSecret != null) {
        log.debug("Client ID and secret are configured. Creating OAuthCredientialsProvider with: {}", this);
        return CredentialsProvider.newCredentialsProviderBuilder()
          .clientId(clientId)
          .clientSecret(clientSecret)
          .audience(audience)
          .authorizationServerUrl(authorizationServerUrl)
          .credentialsCachePath(credentialsCachePath)
          .build();
      }
      return null;
    }
  }

  @Override
  public String getBrokerContactPoint() {
    return broker.getContactPoint();
  }

  @Override
  public Duration getDefaultRequestTimeout() {
    return getRequestTimeout();
  }

  @Override
  public int getNumJobWorkerExecutionThreads() {
    return worker.getThreads();
  }

  @Override
  public int getDefaultJobWorkerMaxJobsActive() {
    return worker.getMaxJobsActive();
  }

  @Override
  public String getDefaultJobWorkerName() {
    return job.getWorker();
  }

  @Override
  public Duration getDefaultJobTimeout() {
    return job.getTimeout();
  }

  @Override
  public Duration getDefaultJobPollInterval() {
    return job.getPollInterval();
  }

  @Override
  public Duration getDefaultMessageTimeToLive() {
    return message.getTimeToLive();
  }

  @Override
  public boolean isPlaintextConnectionEnabled() {
    return security.isPlaintext();
  }

  @Override
  public String getCaCertificatePath() {
    return security.getCertPath();
  }

  @Override
  public CredentialsProvider getCredentialsProvider() {
    return security.getCredentialsProvider();
  }

}
