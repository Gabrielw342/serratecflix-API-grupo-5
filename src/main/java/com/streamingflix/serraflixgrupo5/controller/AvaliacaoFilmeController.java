package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoFilmeRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoFilmeResponse;
import com.streamingflix.serraflixgrupo5.service.AvaliacaoFilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-filmes")
@Tag(name = "Avaliações de Filmes", description = "Endpoints para registrar avaliações de filmes")
public class AvaliacaoFilmeController {

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Operation(
        summary = "Avaliar um filme",
        description = "Registra a avaliação (nota e comentário) de um usuário para um filme. A nota deve estar entre 0 e 10."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Avaliação registrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AvaliacaoFilmeResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição (nota fora do intervalo, campos obrigatórios ausentes)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Filme ou usuário não encontrado",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<AvaliacaoFilmeResponse> salvar(
            @Parameter(description = "Dados da avaliação do filme", required = true)
            @Valid @RequestBody AvaliacaoFilmeRequest request) {

        AvaliacaoFilmeResponse response = avaliacaoFilmeService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}