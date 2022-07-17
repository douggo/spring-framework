package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatorioService {

    private FuncionarioRepository repository;

    public RelatorioService(FuncionarioRepository funcionarioRepository) {
        this.repository = funcionarioRepository;
    }

    public void startService(Scanner scanner) {
        limpaConsole();
        System.out.println("Rotina selecionada: [RELATÓRIOS]");
        System.out.println("0 - Sair");
        System.out.println("1 - Pesquisar todos os funcionários por {NOME}");
        System.out.println("2 - Pesquisar por {NOME}, {SALÁRIO}, {DATA DA CONTRATAÇÃO}");
        System.out.println("3 - Lista {SALÁRIO} dos funcionários");
        int opcao = scanner.nextInt();
        switch(opcao) {
            case 1:
                imprimeFuncionariosPorNome(scanner);
            break;
            case 2:
                imprimeFuncionariosPorNomeSalarioData(scanner);
            break;
            case 3:
                imprimeSalarioFuncionarios();
                scanner.next();
            break;
        }
    }

    private void imprimeFuncionariosPorNomeSalarioData(Scanner scanner) {
        System.out.println("Informe o nome que o sistema deverá filtrar:");
        String nome = scanner.next();
        System.out.println("Informe o salário que o sistema deverá filtrar:");
        BigDecimal salario = new BigDecimal(scanner.next());
        System.out.println("Informe a data de contratação:");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataContratacao = LocalDate.parse(scanner.next(), format);
        List<Funcionario> funcionarios = this.repository.findByNomeSalarioDataContratacao(nome, salario, dataContratacao);
        limpaConsole();
        System.out.println("Foram encontrados " + funcionarios.size() + " funcionários:");
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
        System.out.println("Pressione uma tecla seguida por [Enter] para prosseguir.");
        scanner.next();
    }

    private void imprimeFuncionariosPorNome(Scanner scanner) {
        System.out.println("Informe o nome que o sistema deverá filtrar:");
        List<Funcionario> funcionarios = this.repository.findByNome(scanner.next());
        limpaConsole();
        System.out.println("Foram encontrados " + funcionarios.size() + " funcionário com este nome:");
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
        System.out.println("Pressione qualquer tecla seguindo de [Enter] para continuar.");
        scanner.next();
    }

    private void imprimeSalarioFuncionarios() {
        limpaConsole();
        System.out.println("Listando todos os funcionários e seus salários...");
        String text = "Funcionário %s: %s | R$ %s %n";
        this.repository.findFuncionarioProjecao().forEach(
            f -> System.out.format(text, f.getId(), f.getNome(), f.getSalario())
        );
        System.out.println("Pressione qualquer tecla seguido por [Enter] para continuar.");
    }

    private void limpaConsole() {
        System.out.print("\033[H\033[2J");
    }
    
}
