package br.com.itau.todo.list.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
public class App {

	public static void main(String[] args) {

		var application = new SpringApplication(App.class);
		var env = application.run(args).getEnvironment();

		var port = env.getProperty("server.port");

		log.info("\n\n\t Swagger: http://localhost:{}/swagger-ui/index.html\n", port);
	}

}
