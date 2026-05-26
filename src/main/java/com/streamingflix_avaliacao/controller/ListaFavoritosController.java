package com.streamingflix_avaliacao.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamingflix_avaliacao.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix_avaliacao.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix_avaliacao.service.ListaFavoritosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/favoritos")
public class ListaFavoritosController {

    private final ListaFavoritosService service;

    public ListaFavoritosController(ListaFavoritosService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<ListaFavoritosResponseDTO> salvar(@Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }
}