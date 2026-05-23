package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.UsuarioRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.UsuarioResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Foto;
import com.streamingflix.serraflixgrupo5.service.FotoService;
import com.streamingflix.serraflixgrupo5.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;
import java.util.List;
 
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
 
    @Autowired
    private UsuarioService usuarioService;
 
    @Autowired
    private FotoService fotoService;
 
    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dto));
    }
 
    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
 
    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }
 
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
 
    @PostMapping("/{id}/foto")
    @Operation(summary = "Fazer upload da foto de perfil")
    public ResponseEntity<Void> uploadFoto(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        fotoService.salvarFoto(id, arquivo);
        return ResponseEntity.ok().build();
    }
 
    @GetMapping("/{id}/foto")
    @Operation(summary = "Buscar foto de perfil")
    public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
        Foto foto = fotoService.buscarFoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + foto.getNome() + "\"")
                .contentType(MediaType.parseMediaType(foto.getTipo()))
                .body(foto.getDados());
    }
    
    @DeleteMapping("/{id}/foto")
    @Operation(summary = "Deletar foto de perfil")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
        fotoService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }
}
