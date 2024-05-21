package com.literatura.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.literatura.app.client.Client;
import com.literatura.app.repository.AutorRepository;
import com.literatura.app.repository.LibroRepository;

@SpringBootApplication
public class LiteraturaAPP implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepositorio;
	@Autowired
	private AutorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaAPP.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client client = new Client(libroRepositorio, autorRepositorio);
		client.menu();
	}

}
