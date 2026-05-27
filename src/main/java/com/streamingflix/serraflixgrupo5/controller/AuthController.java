package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.LoginRequestDTO;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import com.streamingflix.serraflixgrupo5.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/login") 
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public AuthController(AuthenticationManager manager, TokenService tokenService, UsuarioRepository repository) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> efetuarLogin(@Valid @RequestBody LoginRequestDTO dto) {
        var authParam = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getSenha());
        manager.authenticate(authParam); 
        Usuario usuario = repository.findByUsername(dto.getUsername()).get();
        return ResponseEntity.ok(tokenService.gerarToken(usuario));
    }
}