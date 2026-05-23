package com.streamingflix.serraflixgrupo5.service;

import com.streamingflix.serraflixgrupo5.entity.Foto;
import com.streamingflix.serraflixgrupo5.entity.Usuario;
import com.streamingflix.serraflixgrupo5.exception.ResourceNotFoundException;
import com.streamingflix.serraflixgrupo5.repository.FotoRepository;
import com.streamingflix.serraflixgrupo5.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;
 
@Service
public class FotoService {
 
    @Autowired
    private FotoRepository fotoRepository;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    @Transactional
    public Foto salvarFoto(Long usuarioId, MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio");
        }
 
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + usuarioId));
 
        Foto foto = fotoRepository.findByUsuarioId(usuarioId)
                .orElse(new Foto());
 
        foto.setDados(arquivo.getBytes());
        foto.setTipo(arquivo.getContentType());
        foto.setNome(arquivo.getOriginalFilename());
        foto.setUsuario(usuario);
 
        foto = fotoRepository.save(foto);
        usuario.setFotoPerfil(foto);
        usuarioRepository.save(usuario);
 
        return foto;
    }
 
    public Foto buscarFoto(Long usuarioId) {
        return fotoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Foto não encontrada para o usuário: " + usuarioId));
    }
    
    @Transactional
    public void deletarFoto(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + usuarioId));
 
        Foto foto = fotoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Foto não encontrada para o usuário: " + usuarioId));
 
        usuario.setFotoPerfil(null);
        usuarioRepository.save(usuario);
        fotoRepository.delete(foto);
    }
}
