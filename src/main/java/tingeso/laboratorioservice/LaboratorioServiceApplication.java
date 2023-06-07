package tingeso.laboratorioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LaboratorioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaboratorioServiceApplication.class, args);
	}

}
