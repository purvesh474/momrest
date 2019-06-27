package momrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

@EnableCaching
public class MomrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomrestApplication.class, args);
	}

}
