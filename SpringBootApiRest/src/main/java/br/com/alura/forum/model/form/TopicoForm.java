package br.com.alura.forum.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.builder.TopicoBuilder;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String titulo;
    @NotNull @NotEmpty @Length(min = 5)
    private String mensagem;
    @NotNull @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository) {
        TopicoBuilder topicoBuilder = new TopicoBuilder();
        topicoBuilder.titulo(this.titulo);
        topicoBuilder.mensagem(this.mensagem);
        topicoBuilder.curso(cursoRepository.findByNome(this.nomeCurso));
        return topicoBuilder.build();
    }

}