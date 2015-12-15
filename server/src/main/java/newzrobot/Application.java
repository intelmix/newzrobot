package newzrobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

//TODO: move this into an app server like tomcat
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(GoogleUserRepository gur) {
        return (args) -> {
            gur.save(new GoogleUser("id", "name", "givenname", "fname", "link", "picture", "g", "locale", "at"));
        };

    }

}
