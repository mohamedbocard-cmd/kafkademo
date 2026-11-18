package sn.armande.kafkademo;

import org.springframework.boot.SpringApplication;

public class TestKafkademoApplication {

	public static void main(String[] args) {
		SpringApplication.from(KafkademoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
