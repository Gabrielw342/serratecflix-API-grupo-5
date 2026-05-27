package com.streamingflix.serraflixgrupo5.config;

import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario u1 = new Usuario();
            u1.setNome("Bruno Freitas");
            u1.setEmail("bruno@email.com");
            u1.setUsername("brunofreitas");
            u1.setSenha("senha123456");

            Usuario u2 = new Usuario();
            u2.setNome("Enzo Costa");
            u2.setEmail("enzo@email.com");
            u2.setUsername("enzocosta");
            u2.setSenha("senha123456");

            Usuario u3 = new Usuario();
            u3.setNome("Gabriel Ecard");
            u3.setEmail("gabriel@email.com");
            u3.setUsername("gabrielecard");
            u3.setSenha("senha123456");
            
            Usuario u4 = new Usuario();
            u4.setNome("João Vitor Clemente");
            u4.setEmail("joao@email.com");
            u4.setUsername("joãovitorclemente");
            u4.setSenha("senha123456");

            Usuario u5 = new Usuario();
            u5.setNome("Matheus Lahr");
            u5.setEmail("matheus@email.com");
            u5.setUsername("matheuslahr");
            u5.setSenha("senha123456");
            
            Usuario u6 = new Usuario();
            u6.setNome("Breno");
            u6.setEmail("breno@email.com");
            u6.setUsername("breno");
            u6.setSenha("senha123456");

            usuarioRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));
        }
    }
}
