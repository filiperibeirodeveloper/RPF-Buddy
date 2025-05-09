package rfp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "rfp.repository")
public class RfpApplication {
    public static void main(String[] args) {
        SpringApplication.run(RfpApplication.class, args);
    }
}