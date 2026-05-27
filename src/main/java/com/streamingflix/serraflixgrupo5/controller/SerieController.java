package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoSerieRequest;
import com.streamingflix.serraflixgrupo5.dto.request.SerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoSerieResponse;
import com.streamingflix.serraflixgrupo5.dto.response.SerieResponse;
import com.streamingflix.serraflixgrupo5.service.AvaliacaoSerieService;
import com.streamingflix.serraflixgrupo5.service.SerieService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
@Tag(name = "Séries", description = "Endpoints para gerenciamento do catálogo de séries e suas avaliações")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @Autowired
    private AvaliacaoSerieService avaliacaoSerieService;

    @Operation(
        summary = "Cadastrar série",
        description = "Cadastra uma nova série no catálogo da SerraFlix com todas as suas informações"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Série cadastrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SerieResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SerieResponse criar(
            @Parameter(description = "Dados da série a ser cadastrada", required = true)
            @RequestBody @Valid SerieRequest request) {

        return serieService.criar(request);
    }

    @Operation(
        summary = "Listar todas as séries",
        description = "Retorna a lista completa de séries cadastradas no catálogo da SerraFlix"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de séries retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SerieResponse.class))
        )
    })
    @GetMapping
    public List<SerieResponse> listarTodos() {

        return serieService.listarTodos();
    }

    @Operation(
        summary = "Buscar série por ID",
        description = "Retorna uma série específica do catálogo pelo seu identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Série encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SerieResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série não encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public SerieResponse buscarPorId(
            @Parameter(description = "ID da série", required = true)
            @PathVariable Long id) {

        return serieService.buscarPorId(id);
    }

    @Operation(
        summary = "Atualizar série",
        description = "Atualiza as informações de uma série existente no catálogo pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Série atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SerieResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série não encontrada",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public SerieResponse atualizar(
            @Parameter(description = "ID da série a ser atualizada", required = true)
            @PathVariable Long id,

            @Parameter(description = "Novos dados da série", required = true)
            @RequestBody @Valid SerieRequest request) {

        return serieService.atualizar(id, request);
    }

    @Operation(
        summary = "Deletar série",
        description = "Remove uma série do catálogo pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Série deletada com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série não encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public void deletar(
            @Parameter(description = "ID da série a ser deletada", required = true)
            @PathVariable Long id) {

        serieService.deletar(id);
    }

    @Operation(
        summary = "Listar avaliações de uma série",
        description = "Retorna todas as avaliações registradas para uma série específica"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Avaliações retornadas com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AvaliacaoSerieResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série não encontrada",
            content = @Content
        )
    })
    @GetMapping("/{serieId}/avaliacoes")
    public List<AvaliacaoSerieResponse> listarAvaliacoes(
            @Parameter(description = "ID da série", required = true)
            @PathVariable Long serieId) {

        return avaliacaoSerieService.listarPorSerie(serieId);
    }

    @Operation(
        summary = "Avaliar uma série",
        description = "Registra a avaliação de um usuário para uma série. A nota deve estar entre 0 e 10."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Avaliação registrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AvaliacaoSerieResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série ou usuário não encontrado",
            content = @Content
        )
    })
    @PostMapping("/{serieId}/avaliacoes")
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoSerieResponse avaliar(
            @Parameter(description = "ID da série a ser avaliada", required = true)
            @PathVariable Long serieId,

            @Parameter(description = "ID do usuário que está avaliando", required = true)
            @RequestParam Long usuarioId,

            @Parameter(description = "Dados da avaliação", required = true)
            @RequestBody @Valid AvaliacaoSerieRequest request) {

        request.setSerieId(serieId);

        return avaliacaoSerieService.avaliar(usuarioId, request);
    }

    @Operation(
        summary = "Deletar avaliação",
        description = "Remove uma avaliação de série pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Avaliação deletada com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Avaliação não encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/avaliacoes/{avaliacaoId}")
    public void deletarAvaliacao(
            @Parameter(description = "ID da avaliação a ser deletada", required = true)
            @PathVariable Long avaliacaoId) {

        avaliacaoSerieService.deletar(avaliacaoId);
    }

    @Operation(
        summary = "Importar série da OMDb",
        description = "Busca uma série na API OMDb pelo título e salva automaticamente no banco de dados da SerraFlix"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Série importada e salva com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SerieResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Série não encontrada na OMDb",
            content = @Content
        )
    })
    @PostMapping("/importar/{titulo}")
    public SerieResponse importarSerie(
            @Parameter(description = "Título da série a ser importada", required = true)
            @PathVariable String titulo) {

        return serieService.importarSerie(titulo);
    }
}