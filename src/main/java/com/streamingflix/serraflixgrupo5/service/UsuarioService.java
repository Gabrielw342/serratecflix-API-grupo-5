package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.config.MailConfig;
import com.streamingflix.serraflixgrupo5.dto.request.UsuarioRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.UsuarioResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.exception.ConflictException;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MailConfig mailConfig;

    @Transactional
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
        usuario.setSenha(encoder.encode(dto.getSenha()));

        usuario = usuarioRepository.save(usuario);

        try {
            String mensagem = "Olá, " + usuario.getNome() + "!\n\n"
                    + "Seu cadastro foi realizado com sucesso na plataforma Serraflix.\n"
                    + "Username: " + usuario.getUsername() + "\n"
                    + "Email: " + usuario.getEmail();
            mailConfig.sendEmail(
                    usuario.getEmail(),
                    "Bem-vindo ao Serraflix!",
                    mensagem
            );
        } catch (Exception e) {
            System.err.println("Falha ao enviar email de boas-vindas: " + e.getMessage());
        }

        return toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        return toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        if (!usuario.getUsername().equals(dto.getUsername()) &&
                usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new ConflictException("Username já está em uso");
        }
        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email já está cadastrado");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setUsername(dto.getUsername());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(dto.getSenha());
        }

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setUsername(usuario.getUsername());
        dto.setDataCriacao(usuario.getDataCriacao());
        if (usuario.getFotoPerfil() != null) {
            dto.setFotoPerfil(usuario.getFotoPerfil().getNome());
        }
        return dto;
    }
}