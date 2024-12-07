package End.Sem.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpiderWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderWebApplication.class, args);
	}

	// Global CORS configuration
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // Apply this to all endpoints
						.allowedOrigins("http://localhost:3000", "https://jolly-salamander-9c59e3.netlify.app")  // Allow React app's domain during development
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allowed HTTP methods
						.allowedHeaders("*")  // Allow all headers
						.allowCredentials(true);  // Allow credentials like cookies or authorization headers
			}
		};
	}
}
