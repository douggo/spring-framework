package br.com.alura.forum.model.builder;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Resposta;
import br.com.alura.forum.model.StatusTopico;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.Usuario;

public class TopicoBuilder {

    public Long id;
    public String titulo;
    public String mensagem;
    public LocalDateTime dataCriacao = LocalDateTime.now();
    public StatusTopico status = StatusTopico.NAO_RESPONDIDO;
    public Usuario autor;
    public Curso curso;
    public List<Resposta> respostas;

    public TopicoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public TopicoBuilder titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public TopicoBuilder mensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public TopicoBuilder data(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public TopicoBuilder status(StatusTopico statusTopico) {
        this.status = statusTopico;
        return this;
    }

    public TopicoBuilder autor(Usuario autor) {
        this.autor = autor;
        return this;
    }

    public TopicoBuilder curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public TopicoBuilder respostas(List<Resposta> respostas) {
        this.respostas = respostas;
        return this;
    }

    public Topico build() {
        Topico topico = new Topico(this);
        return topico;
    }

}
