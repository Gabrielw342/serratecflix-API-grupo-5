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
import java.util.List;
 
@Service
public class FotoService {
 
    @Autowired
    private FotoRepository fotoRepository;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private static final List<String> TIPOS_PERMITIDOS = List.of(
    	    "image/jpeg", "image/png", "image/gif", "image/webp"
    	);
 
    @Transactional
    public Foto salvarFoto(Long usuarioId, MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio");
        }
        
        if (!TIPOS_PERMITIDOS.contains(arquivo.getContentType())) {
            throw new IllegalArgumentException(
                "Tipo de arquivo não permitido. Use: JPEG, PNG, GIF ou WEBP"
            );
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
