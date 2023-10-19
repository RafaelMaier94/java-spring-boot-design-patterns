package one.digitalinnovation.springdesignpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringDesignPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDesignPatternsApplication.class, args);
	}

}
