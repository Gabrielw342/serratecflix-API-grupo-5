package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix.serraflixgrupo5.service.ListaFavoritosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaFavoritosController {

    @Autowired
    private ListaFavoritosService service;

    @PostMapping
    public ResponseEntity<ListaFavoritosResponseDTO> criar(@Valid @RequestBody ListaFavoritosRequestDTO dto) {
        ListaFavoritosResponseDTO novaLista = service.criarLista(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
    }

    @GetMapping
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listaId}/copiar/para/{usuarioId}")
    public ResponseEntity<ListaFavoritosResponseDTO> copiarLista(@PathVariable Long listaId, @PathVariable Long usuarioId) {
        ListaFavoritosResponseDTO copia = service.copiarLista(listaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }
}