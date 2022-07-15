package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CargoService {
    
    private final CargoRepository repository;

    public CargoService(CargoRepository cargoRepository) {
        this.repository = cargoRepository;        
    }

    public void startService(Scanner scanner) {
        System.out.println("[CARGO] Você deseja:");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar um novo cargo");
        System.out.println("2 - Atualizar um cargo");
        System.out.println("3 - Remover um cargo");
        System.out.println("9 - Listar todos os cargos");
        int opcao = scanner.nextInt();
        switch(opcao) {
            case 1:
                cadastrar(scanner);
            break;
            case 2: 
                atualizar(scanner);
            break;
            case 3:
                remover(scanner);
            break;
            case 9:
                imprimeCargos();
            break;
        }
        scanner.next();
    }

    public void cadastrar(Scanner scanner) {
        System.out.println("Digite a descrição do cargo:");
        Cargo cargo = new Cargo();
        cargo.setDescricao(scanner.next());
        this.repository.save(cargo);
        System.out.format("Cargo %s cadastrado com sucesso %n", cargo.getDescricao());
    }

    public void atualizar(Scanner scanner) {
        System.out.println("[CARGO] Você selecionou a opção de atualizar um cargo.");
        System.out.println("Qual cargo você deseja atualizar?");
        imprimeCargos();
        System.out.println();
        System.out.println("Digite a ID do cargo pretendente:");
        atualizaCargo(scanner);
    }

    public void remover(Scanner scanner) {
        System.out.println("[CARGO] Você selecionou a opção para excluir um cargo.");
        System.out.println("Qual cargo você deseja excluir?");
        imprimeCargos();
        System.out.println();
        System.out.println("Digite a ID do cargo pretendente:");
        removeCargo(scanner);
    }

    public void atualizaCargo(Scanner scanner) {
        Cargo cargo = this.repository.findById(scanner.nextInt()).get();
        System.out.println("Digite o novo nome da categoria:");
        cargo.setDescricao(scanner.next());
        this.repository.save(cargo);
        System.out.println();
        System.out.println("Cargo atualizado com sucesso!");
        System.out.println("Pressione uma tecla para voltar ao menu principal");
        scanner.next();
    }

    public void removeCargo(Scanner scanner) {
        Cargo cargo = this.repository.findById(scanner.nextInt()).get();
        this.repository.delete(cargo);
        System.out.println();
        System.out.println("Cargo removido com sucesso!");
        System.out.println("Pressione uma tecla + [ENTER] para voltar ao menu principal");
        scanner.next();
    }

    public void imprimeCargos() {
        this.repository.findAll().forEach(cargo -> {
            String text = String.format("%s - %s", cargo.getId(), cargo.getDescricao());
            System.out.println(text);
        });
    }

}
