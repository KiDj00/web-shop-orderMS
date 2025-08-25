package rs.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@PropertySource("classpath:application-secret.properties")
@EnableFeignClients(basePackages = "rs.webshop.client")
public class ShopApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopApplication.class, args);
  }
}
