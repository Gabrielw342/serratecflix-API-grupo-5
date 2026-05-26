package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoSerieRequest;
import com.streamingflix.serraflixgrupo5.dto.request.SerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoSerieResponse;
import com.streamingflix.serraflixgrupo5.dto.response.SerieResponse;
import com.streamingflix.serraflixgrupo5.service.AvaliacaoSerieService;
import com.streamingflix.serraflixgrupo5.service.SerieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SerieController {

    private final SerieService serieService;
    private final AvaliacaoSerieService avaliacaoSerieService;

    public SerieController(SerieService serieService, AvaliacaoSerieService avaliacaoSerieService) {
        this.serieService = serieService;
        this.avaliacaoSerieService = avaliacaoSerieService;
    }

    @GetMapping
    public ResponseEntity<List<SerieResponse>> listarTodos() {
        return ResponseEntity.ok(serieService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SerieResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serieService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SerieResponse> criar(@Valid @RequestBody SerieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serieService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SerieResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SerieRequest request) {
        return ResponseEntity.ok(serieService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        serieService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{serieId}/avaliacoes")
    public ResponseEntity<List<AvaliacaoSerieResponse>> listarAvaliacoes(@PathVariable Long serieId) {
        return ResponseEntity.ok(avaliacaoSerieService.listarPorSerie(serieId));
    }

    @PostMapping("/{serieId}/avaliacoes")
    public ResponseEntity<AvaliacaoSerieResponse> avaliar(
            @PathVariable Long serieId,
            @RequestParam Long usuarioId,
            @Valid @RequestBody AvaliacaoSerieRequest request) {
        request.setSerieId(serieId);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSerieService.avaliar(usuarioId, request));
    }

    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long avaliacaoId) {
        avaliacaoSerieService.deletar(avaliacaoId);
        return ResponseEntity.noContent().build();
    }
}