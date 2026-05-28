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

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getSenha());
        authenticationManager.authenticate(authToken);

        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername()).orElseThrow();
        return ResponseEntity.ok(Map.of("token", tokenService.gerarToken(usuario)));
    }
}