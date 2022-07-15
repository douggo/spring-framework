package br.com.alura.spring.data.orm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "funcionarios")
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String CPF;
    private BigDecimal salario;
    private LocalDate dataContratacao;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "unidade_funcionario", 
        joinColumns = {@JoinColumn(name="fk_funcionario")},
        inverseJoinColumns = {@JoinColumn(name="fk_unidade")}
    )
    private List<Unidade> unidades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Unidade> getUnidades() {
        return Collections.unmodifiableList(unidades);
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public void adicionaUnidade(Unidade unidade) {
        if(this.unidades == null) {
            this.unidades = new ArrayList<Unidade>();
        }
        this.unidades.add(unidade);
    }

    public void limpaUnidades() {
        this.unidades.clear();
    }

    @Override
    public String toString() {
        return "Funcionário " + this.id + ": [ " 
            + this.nome + " | " + this.CPF + " | R$ " + this.salario + " | " + this.cargo + " | "
            + this.getUnidades() + " | " + this.dataContratacao + " ]";
    }

}
