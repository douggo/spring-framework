package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.UnidadeRepository;

@Service
public class UnidadeService {

    private UnidadeRepository repository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.repository = unidadeRepository;
    }

    public void startService(Scanner scanner) {
        limpaConsole();
        System.out.println("Rotina selecionada: [UNIDADE]");
        System.out.println("0 - Sair");
        System.out.println("1 - Listar todas as unidades");
        System.out.println("2 - Cadastrar uma nova unidade");
        System.out.println("3 - Atualizar uma unidade");
        System.out.println("4 - Remover uma unidade");
        int opcao = scanner.nextInt();
        switch(opcao) {
            case 1:
                imprimeUnidades();
                System.out.println("Pressione qualquer tecla prosseguida por [Enter] para continuar");
                scanner.next();
            break;
            case 2: 
                cadastrar(scanner);
            break;
            case 3:
                atualizar(scanner);
            break;
            case 4:
                remover(scanner);
            break;
        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.println("Digite a descrição da Unidade:");
        Unidade unidade = new Unidade();
        unidade.setDescricao(scanner.next());
        System.out.println("Digite o endereço da Unidade:");
        unidade.setEndereco(scanner.next());
        this.repository.save(unidade);
        System.out.format("Unidade %s cadastrado com sucesso %n", unidade.getDescricao());
    }

    private void atualizar(Scanner scanner) {
        System.out.println("[UNIDADE] Você selecionou a opção de atualizar um unidade.");
        System.out.println("Qual unidade você deseja atualizar?");
        imprimeUnidades();
        System.out.println();
        System.out.println("Digite a ID do unidade pretendente:");
        Unidade unidade = this.repository.findById(scanner.nextInt()).get();
        System.out.println("Digite a nova descrição da Unidade:");
        unidade.setDescricao(scanner.next());
        System.out.println("Digite o novo endereço da Unidade:");
        unidade.setEndereco(scanner.next());
        this.repository.save(unidade);
        System.out.println();
        System.out.println("unidade atualizado com sucesso!");
        System.out.println("Pressione uma tecla + [ENTER] para voltar ao menu principal");
        scanner.next();
    }

    private void remover(Scanner scanner) {
        imprimeUnidades();
        System.out.println("Digite o ID da Unidade que você deseja remover:");
        int id = scanner.nextInt();
        Unidade unidade = this.repository.findById(id).get();
        this.repository.delete(unidade);
        System.out.println();
        System.out.println("Unidade removida com sucesso!");
        System.out.println("Pressione uma tecla + [ENTER] para voltar ao menu principal");
        scanner.next();
    }

    private void imprimeUnidades() {
        this.repository.findAll().forEach(unidade -> {
            String text = String.format("%s - %s - %s", unidade.getId(), unidade.getDescricao(), unidade.getEndereco());
            System.out.println(text);
        });
    }

    private void limpaConsole() {
        System.out.print("\033[H\033[2J");
    }

}
