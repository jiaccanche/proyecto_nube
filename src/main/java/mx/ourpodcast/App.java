package mx.ourpodcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class App {
    public static void main(String[] args) {
      SpringApplication app = new SpringApplication(App.class);
      app.run(args);
    }
}