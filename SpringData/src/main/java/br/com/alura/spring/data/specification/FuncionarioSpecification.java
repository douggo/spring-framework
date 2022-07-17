package br.com.alura.spring.data.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.spring.data.orm.Funcionario;

public class FuncionarioSpecification {
    
    public static Specification<Funcionario> nome(String nome) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        };
    }

    public static Specification<Funcionario> cpf(String CPF) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("CPF"), CPF);
        };
    }

    public static Specification<Funcionario> salario(BigDecimal salario) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("salario"), salario);
        };
    }

    public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("dataContratacao"), dataContratacao);
        };
    }

}
