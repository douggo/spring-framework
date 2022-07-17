package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.FuncionarioSpecification;

@Service
public class RelatorioFuncionarioDinamico {
    
    private final FuncionarioRepository repository;

    public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
        this.repository = funcionarioRepository;
    }

    public void startService(Scanner scanner) {
        String nomeScan;
        String cpfScan;
        BigDecimal salarioScan;
        String dataScanner;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite o nome:");
        nomeScan = scanner.next();
        if(nomeScan.equalsIgnoreCase("NULL")) {
            nomeScan = null;
        }

        System.out.println("Digite o CPF:");
        cpfScan = scanner.next();
        if(cpfScan.equalsIgnoreCase("NULL")) {
            cpfScan = null;
        }
        
        System.out.println("Digite o salário:");
        salarioScan = new BigDecimal(scanner.nextDouble());
        if(salarioScan.doubleValue() == 0) {
            salarioScan = null;
        }

        System.out.println("Digite a data de contratação");
        dataScanner = scanner.next();
        LocalDate dataContratacao;
        if(dataScanner.equalsIgnoreCase("NULL")) {
            dataContratacao = null;
        } else {
            dataContratacao = LocalDate.parse(dataScanner, formatter);
        }

        List<Funcionario> funcionarios = this.repository.findAll(
            Specification.where(FuncionarioSpecification.nome(nomeScan))
                         .or(FuncionarioSpecification.cpf(cpfScan))
                         .or(FuncionarioSpecification.salario(salarioScan))
                         .or(FuncionarioSpecification.dataContratacao(dataContratacao))
        );

        funcionarios.forEach(funcionario -> System.out.println(funcionario));

        System.out.println("Pressione uma tecla seguida por [Enter] para continuar...");
        scanner.next();
    }

}
