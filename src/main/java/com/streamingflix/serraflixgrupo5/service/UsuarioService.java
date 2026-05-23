package com.streamingflix.serraflixgrupo5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamingflix.serraflixgrupo5.dto.request.UsuarioRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.UsuarioResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.exception.ConflictException;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
		if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new ConflictException("Username já está em uso");
        }
		if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email já está cadastrado");
        }
		
		Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(dto.getSenha());
       
        usuario = usuarioRepository.save(usuario);
        return toResponseDTO(usuario);
	}
	
	private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
	    UsuarioResponseDTO dto = new UsuarioResponseDTO();
	    dto.setId(usuario.getId());
	    dto.setNome(usuario.getNome());
	    dto.setEmail(usuario.getEmail());
	    dto.setUsername(usuario.getUsername());
	    dto.setDataCriacao(usuario.getDataCriacao());
	    return dto;
	}
}
