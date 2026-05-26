package com.streamingflix_avaliacao.controller;

import com.streamingflix_avaliacao.dto.request.SerieRequestDTO;
import com.streamingflix_avaliacao.dto.response.SerieResponseDTO;
import com.streamingflix_avaliacao.service.SerieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieService service;

    public SerieController(SerieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SerieResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<SerieResponseDTO> salvar(@Valid @RequestBody SerieRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }
}