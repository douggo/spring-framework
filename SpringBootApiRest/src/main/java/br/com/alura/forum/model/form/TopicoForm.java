package br.com.alura.forum.model.form;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.builder.TopicoBuilder;
import br.com.alura.forum.repository.CursoRepository;

public class TopicoForm {

    private String titulo;
    private String mensagem;
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