package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.model.dto.TopicoDetalhadoDto;
import br.com.alura.forum.model.dto.TopicoDto;
import br.com.alura.forum.model.form.TopicoAtualizadoForm;
import br.com.alura.forum.model.form.TopicoForm;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    
    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if(nomeCurso == null) {
            return TopicoDto.converter(this.repository.findAll());
        } 
        return TopicoDto.converter(this.repository.findByCursoNome(nomeCurso));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = this.repository.save(topicoForm.converter(cursoRepository));
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDetalhadoDto>detalhar(@PathVariable Long id) {
        Optional<Topico> topico = this.repository.findById(id);
        if(topico.isPresent()) {
            return ResponseEntity.ok(new TopicoDetalhadoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoAtualizadoForm topicoForm) {
        Optional<Topico> optional = this.repository.findById(id);
        if(optional.isPresent()) {
            Topico topico = topicoForm.atualizar(id, this.repository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = this.repository.findById(id);
        if(optional.isPresent()) {
            this.repository.deleteById(id);   
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}