package ecse321.t08.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("ecse321.t08.rideshare")
@SpringBootApplication
public class rideshareApplication {
  
    public static void main(String[] args) {
		SpringApplication.run(rideshareApplication.class, args);
	}
  
}
