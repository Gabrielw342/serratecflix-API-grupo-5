package com.streamingflix_avaliacao.service;

import com.streamingflix_avaliacao.dto.request.UsuarioRequestDTO;
import com.streamingflix_avaliacao.dto.response.UsuarioResponseDTO;
import com.streamingflix_avaliacao.entity.Usuario;
import com.streamingflix_avaliacao.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ViaCepService viaCepService;

    public UsuarioService(UsuarioRepository repository, ViaCepService viaCepService) {
        this.repository = repository;
        this.viaCepService = viaCepService;
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setUsername(dto.getUsername());
        
        
        usuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha())); 
        
        usuario.setFotoPerfil(dto.getFotoPerfil());
        usuario.setCep(dto.getCep());
        usuario.setLogradouro(viaCepService.buscarLogradouroPorCep(dto.getCep()));

        usuario = repository.save(usuario);
        return new UsuarioResponseDTO(usuario);
    }
}