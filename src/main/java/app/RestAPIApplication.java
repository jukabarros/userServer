package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("api")
public class RestAPIApplication {
	
	/**
	 * Executa a aplicacao
	 * @param args argumentos
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestAPIApplication.class, args);
	}

}
