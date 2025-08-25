package rs.webshop.config;

import feign.RequestInterceptor;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FeignConfig {

  @Value("${keycloak.token-uri}")
  private String tokenUri;

  @Value("${keycloak.client-id}")
  private String clientId;

  @Value("${keycloak.client-secret}")
  private String clientSecret;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      String token = getClientToken();
      requestTemplate.header("Authorization", "Bearer " + token);
    };
  }

  private String getClientToken() {
    RestTemplate rest = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body =
        "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

    HttpEntity<String> entity = new HttpEntity<>(body, headers);
    ResponseEntity<Map> response = rest.exchange(tokenUri, HttpMethod.POST, entity, Map.class);
    return (String) response.getBody().get("access_token");
  }
}
