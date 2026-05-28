package com.streamingflix.serraflixgrupo5.controller;

import java.util.List;

import com.streamingflix.serraflixgrupo5.dto.response.OmdbResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.streamingflix.serraflixgrupo5.dto.request.FilmeRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.FilmeResponseDTO;
import com.streamingflix.serraflixgrupo5.service.FilmeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "Endpoints para gerenciamento do catálogo de filmes e integração com OMDb")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Operation(
        summary = "Buscar filme na OMDb",
        description = "Consulta a API externa OMDb pelo título do filme e retorna informações como diretor, gênero, nota IMDb e sinopse"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Filme encontrado na OMDb",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OmdbResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Filme não encontrado na OMDb",
            content = @Content
        )
    })
    @GetMapping("/omdb")
    public OmdbResponseDTO buscarNaOmdb(
            @Parameter(description = "Título do filme a ser pesquisado na OMDb", required = true)
            @RequestParam String titulo) {

        
        return filmeService.buscarNaOmdb(titulo);
    }

    @Operation(
        summary = "Cadastrar filme",
        description = "Cadastra um novo filme no catálogo da SerraFlix com todas as suas informações"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Filme cadastrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = FilmeResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeResponseDTO salvar(
            @Parameter(description = "Dados do filme a ser cadastrado", required = true)
            @RequestBody @Valid FilmeRequestDTO dto) {
        return filmeService.salvar(dto);
    }

    @Operation(
        summary = "Importar filme da OMDb",
        description = "Busca um filme na API OMDb pelo título e o salva automaticamente no banco de dados da SerraFlix"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Filme importado e salvo com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = FilmeResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Filme não encontrado na OMDb",
            content = @Content
        )
    })
    @PostMapping("/importar/{titulo}")
    public FilmeResponseDTO importarFilme(
            @Parameter(description = "Título do filme a ser importado da OMDb", required = true)
            @PathVariable String titulo) {

        return filmeService.importarFilme(titulo);
    }

    @Operation(
        summary = "Listar todos os filmes",
        description = "Retorna a lista completa de filmes cadastrados no catálogo da SerraFlix"
    )
    @GetMapping
    public List<FilmeResponseDTO> listarTodos() {
        return filmeService.listarTodos();
    }

    @Operation(
        summary = "Buscar filme por ID",
        description = "Retorna um filme específico do catálogo pelo seu identificador único"
    )
    @GetMapping("/{id}")
    public FilmeResponseDTO buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id);
    }

    @Operation(
        summary = "Ranking de filmes por nota",
        description = "Retorna todos os filmes ordenados da maior para a menor nota média de avaliações"
    )
    @GetMapping("/ranking")
    public List<FilmeResponseDTO> listarPorMaiorNota() {
        return filmeService.listarPorMaiorNota();
    }

    @Operation(
        summary = "Deletar filme",
        description = "Remove um filme do catálogo pelo seu ID"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        filmeService.deletar(id);
    }

    @Operation(
        summary = "Atualizar filme",
        description = "Atualiza as informações de um filme existente no catálogo pelo seu ID"
    )
    @PutMapping("/{id}")
    public FilmeResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid FilmeRequestDTO dto) {

        return filmeService.atualizar(id, dto);
    }
}