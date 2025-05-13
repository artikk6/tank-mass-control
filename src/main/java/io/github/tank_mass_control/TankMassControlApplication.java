package io.github.tank_mass_control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TankMassControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TankMassControlApplication.class, args);
	}

}
