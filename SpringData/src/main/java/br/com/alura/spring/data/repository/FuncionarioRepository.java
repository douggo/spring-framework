package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
    
    List<Funcionario> findByNome(String nome);

    @Query("SELECT f FROM funcionarios f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
    List<Funcionario> findByNomeSalarioDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);

}