package com.alura.forumHub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumHubApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ForumHubApplication.class, args);
	}

	@Override
	public void run(String... args){
		Principal principal = new Principal();
		principal.executar();
	}

}
