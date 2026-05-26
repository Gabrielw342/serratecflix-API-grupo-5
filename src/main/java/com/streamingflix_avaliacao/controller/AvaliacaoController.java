package com.streamingflix_avaliacao.controller;

import com.streamingflix_avaliacao.dto.request.AvaliacaoRequestDTO;
import com.streamingflix_avaliacao.dto.response.AvaliacaoResponseDTO;
import com.streamingflix_avaliacao.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping("/filme")
    public ResponseEntity<AvaliacaoResponseDTO> avaliarFilme(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.avaliarFilme(dto));
    }

    @PostMapping("/serie")
    public ResponseEntity<AvaliacaoResponseDTO> avaliarSerie(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.avaliarSerie(dto));
    }
}