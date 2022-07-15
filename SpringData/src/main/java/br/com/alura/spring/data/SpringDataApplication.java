package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CargoService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private Boolean isSystemRunning = true;

	private final CargoService service;

	public SpringDataApplication(CargoService cargoService) {
		this.service = cargoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		while(isSystemRunning) {
			limpaConsole();
			System.out.println("Qual ação você deseja realizar?");
			System.out.println("0 - SAIR");
			System.out.println("1 - CARGO");
			acao(scanner);
		}
	}

	private void acao(Scanner scanner) {
		switch(scanner.nextInt()) {
			case 1:
				this.service.startService(scanner);
			break;
			default:
				this.isSystemRunning = false;
			break;
		}
	}

	private void limpaConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}