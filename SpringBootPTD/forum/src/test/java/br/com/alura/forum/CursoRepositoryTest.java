package br.com.alura.forum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.repository.CursoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE) -> para bancos que sejam diferente de H2
//@ActiveProfiles("test")
public class CursoRepositoryTest {
    
    @Autowired
    private CursoRepository repository;

    // @Autowired
    // private TestEntityManager em;

    @Test
    public void shouldLoadCourseWhenFindingItByName() {
        String nome = "HTML 5";

        // Curso h5 = new Curso();
        // h5.setNome(nome);
        // h5.setCategoria("programação");
        // em.persist(h5);


        Curso curso = this.repository.findByNome(nome);
        Assert.assertNotNull(curso);
        Assert.assertEquals(nome, curso.getNome());
    }

    @Test
    public void shouldNotLoadCourseWhenItDoesntExists() {
        String nome = "JPA";
        Curso curso = this.repository.findByNome(nome);
        Assert.assertNull(curso);
    }

}
