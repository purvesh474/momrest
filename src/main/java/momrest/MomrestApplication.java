package momrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import momrest.model.FileStorageProperties;

@SpringBootApplication

@EnableCaching
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class MomrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomrestApplication.class, args);
	}

}
