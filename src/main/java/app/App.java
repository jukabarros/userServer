package app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author juccelino.barros
 *
 */
@Configuration
public class AppInitializer extends SpringBootServletInitializer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RestAPIApplication.class);
	}

}
