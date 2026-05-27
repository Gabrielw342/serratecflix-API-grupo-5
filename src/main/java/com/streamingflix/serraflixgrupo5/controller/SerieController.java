package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.deletar.*;
import com.streamingflix.serraflixgrupo5.deletar.AvaliacaoSerieResponseDTO;
import com.streamingflix.serraflixgrupo5.deletar.AvaliacaoSerieRequestDTO;

import com.streamingflix.serraflixgrupo5.dto.request.SerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.SerieResponse;
import com.streamingflix.serraflixgrupo5.service.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@Tag(name = "Séries", description = "Endpoints para gerenciamento de séries e suas avaliações")
public class SerieController {

    private final SerieService serieService;
    private final AvaliacaoSerieService avaliacaoSerieService;

    public SerieController(SerieService serieService, AvaliacaoSerieService avaliacaoSerieService) {
        this.serieService = serieService;
        this.avaliacaoSerieService = avaliacaoSerieService;
    }

    @Operation(summary = "Listar todas as séries", description = "Retorna uma lista com todas as séries cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<SerieResponse>> listarTodos() {
        return ResponseEntity.ok(serieService.listarTodos());
    }

    @Operation(summary = "Buscar série por ID", description = "Retorna uma série específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Série encontrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieResponse.class))),
            @ApiResponse(responseCode = "404", description = "Série não encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SerieResponse> buscarPorId(
            @Parameter(description = "ID da série", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(serieService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar nova série", description = "Cria uma nova série no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Série criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<SerieResponse> criar(
            @Parameter(description = "Dados da série a ser cadastrada", required = true)
            @Valid @RequestBody SerieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serieService.criar(request));
    }

    @Operation(summary = "Atualizar série", description = "Atualiza os dados de uma série existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Série atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SerieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Série não encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SerieResponse> atualizar(
            @Parameter(description = "ID da série a ser atualizada", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da série", required = true)
            @Valid @RequestBody SerieRequest request) {
        return ResponseEntity.ok(serieService.atualizar(id, request));
    }

    @Operation(summary = "Deletar série", description = "Remove uma série do sistema pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Série deletada com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Série não encontrada",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da série a ser deletada", required = true)
            @PathVariable Long id) {
        serieService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar avaliações de uma série", description = "Retorna todas as avaliações de uma série específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliações retornadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoSerieResponse.class))),
            @ApiResponse(responseCode = "404", description = "Série não encontrada",
                    content = @Content)
    })
    @GetMapping("/{serieId}/avaliacoes")
    public ResponseEntity<List<AvaliacaoSerieResponse>> listarAvaliacoes(
            @Parameter(description = "ID da série", required = true)
            @PathVariable Long serieId) {
        return ResponseEntity.ok(avaliacaoSerieService.listarPorSerie(serieId));
    }

    @Operation(summary = "Avaliar uma série", description = "Registra a avaliação de um usuário para uma série")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação registrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoSerieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Série ou usuário não encontrado",
                    content = @Content)
    })
    @PostMapping("/{serieId}/avaliacoes")
    public ResponseEntity<AvaliacaoSerieResponse> avaliar(
            @Parameter(description = "ID da série a ser avaliada", required = true)
            @PathVariable Long serieId,
            @Parameter(description = "ID do usuário que está avaliando", required = true)
            @RequestParam Long usuarioId,
            @Parameter(description = "Dados da avaliação", required = true)
            @Valid @RequestBody AvaliacaoSerieRequest request) {
        request.setSerieId(serieId);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSerieService.avaliar(usuarioId, request));
    }

    @Operation(summary = "Deletar avaliação", description = "Remove uma avaliação de série pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada",
                    content = @Content)
    })
    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    public ResponseEntity<Void> deletarAvaliacao(
            @Parameter(description = "ID da avaliação a ser deletada", required = true)
            @PathVariable Long avaliacaoId) {
        avaliacaoSerieService.deletar(avaliacaoId);
        return ResponseEntity.noContent().build();
    }
}