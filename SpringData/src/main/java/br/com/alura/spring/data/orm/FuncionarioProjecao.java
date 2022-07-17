package br.com.alura.spring.data.orm;

import java.math.BigDecimal;

public interface FuncionarioProjecao {
    
    public Integer getId();
    public String getNome();
    public BigDecimal getSalario();
    
}
