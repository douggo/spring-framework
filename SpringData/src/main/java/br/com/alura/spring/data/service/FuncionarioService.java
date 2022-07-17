package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.Unidade;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeRepository;

@Service
public class FuncionarioService {
    
    private FuncionarioRepository repository;
    private CargoRepository cargoRepository;
    private UnidadeRepository unidadeRepository;

    public FuncionarioService(
        FuncionarioRepository funcionarioRepository,
        CargoRepository cargoRepository,
        UnidadeRepository unidadeRepository
    ) {
        this.repository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public void startService(Scanner scanner) {
        limpaConsole();
        System.out.println("Rotina selecionada: [FUNCIONÁRIO]");
        System.out.println("0 - Sair");
        System.out.println("1 - Listar todos os funcionários");
        System.out.println("2 - Cadastrar um novo funcionário");
        System.out.println("3 - Atualizar um funcionário");
        System.out.println("4 - Remover um funcionário");
        int opcao = scanner.nextInt();
        switch(opcao) {
            case 1:
                imprimeFuncionariosPaginado(scanner);
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

    private void imprimeFuncionariosPaginado(Scanner scanner) {
        System.out.println("Informe a página que você deseja acessar:");
        Pageable page = PageRequest.of(scanner.nextInt(), 5, Sort.by(Sort.Direction.ASC, "salario"));
        Page<Funcionario> funcionarios = this.repository.findAll(page);
        System.out.println();
        System.out.println("Página atual: " + funcionarios.getNumber() + "/" + (funcionarios.getTotalPages() - 1));
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
        System.out.println();
        System.out.println("Registros encontrados: " + funcionarios.getTotalElements());
    }

    private void imprimeFuncionarios() {
        this.repository.findAll().forEach(funcionario -> System.out.println(funcionario));
    }

    private void cadastrar(Scanner scanner) {
        boolean parado = false;
        Funcionario funcionario = new Funcionario();

        funcionario.setDataContratacao(LocalDate.now());

        System.out.println("Digite o nome do Funcionário:");
        funcionario.setNome(scanner.next());

        System.out.println("Digite o CPF do Funcionário:");
        funcionario.setCPF(scanner.next());

        System.out.println("Digite o salário do Funcionário " + funcionario.getNome());
        funcionario.setSalario(new BigDecimal(scanner.next()));

        System.out.println("Digite o ID do cargo do Funcionário");
        funcionario.setCargo(this.cargoRepository.findById(scanner.nextInt()).get());

        while(!parado) {
            System.out.println("Digite o ID da Unidade do Funcionário (utilize 0 para finalizar)");
            int id = scanner.nextInt();
            if(id == 0) {
                parado = true;
            } else {
                Optional<Unidade> unidade = this.unidadeRepository.findById(id);
                funcionario.adicionaUnidade(unidade.get());
            }
        }

        this.repository.save(funcionario);

        System.out.format("Funcionário %s cadastrado com sucesso %n", funcionario.getNome());
        
        System.out.println("Pressione qualquer tecla seguida de [Enter] para continuar");
        scanner.next();
    }

    private void atualizar(Scanner scanner) {
        boolean parado = false;
        imprimeFuncionarios();

        System.out.println("Informe o ID do funcionário que você quer atualizar");
        int id = scanner.nextInt();
        Funcionario funcionario = this.repository.findById(id).get();

        funcionario.setDataContratacao(LocalDate.now());
        System.out.println("Informe o novo nome:");

        funcionario.setNome(scanner.next());
        System.out.println("Informe o novo CPF:");

        funcionario.setCPF(scanner.next());
        System.out.println("Informe o novo salário:");

        funcionario.setSalario(new BigDecimal(scanner.next()));
        System.out.println("Informe o novo ID do cargo:");

        funcionario.setCargo(this.cargoRepository.findById(scanner.nextInt()).get());
        
        funcionario.limpaUnidades();
        while(!parado) {
            System.out.println("Digite o ID da Unidade do Funcionário (utilize 0 para finalizar)");
            int idUnidade = scanner.nextInt();
            if(idUnidade == 0) {
                parado = true;
            } else {
                Optional<Unidade> unidade = this.unidadeRepository.findById(idUnidade);
                funcionario.adicionaUnidade(unidade.get());
            }
        }
        
        this.repository.save(funcionario);

        System.out.println("Funcionário " + funcionario.getNome() + " foi salvo. Pressione qualquer tecla + [Enter] para continuar");
        scanner.next();
    }

    private void remover(Scanner scanner) {
        imprimeFuncionarios();
        System.out.println("Informe o ID do funcionário que você quer remover");
        int id = scanner.nextInt();
        this.repository.deleteById(id);
        System.out.println("Funcionário removido com sucesso");
        System.out.println("Pressione qualquer tecla + [Enter] para prosseguir");
        scanner.next();
    }

    private void limpaConsole() {
        System.out.print("\033[H\033[2J");
    }

}
