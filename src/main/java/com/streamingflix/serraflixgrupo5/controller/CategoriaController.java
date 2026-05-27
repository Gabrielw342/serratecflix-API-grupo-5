package com.streamingflix.serraflixgrupo5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamingflix.serraflixgrupo5.dto.request.CategoriaDTORequest;
import com.streamingflix.serraflixgrupo5.dto.response.CategoriaDTOResponse;
import com.streamingflix.serraflixgrupo5.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de filmes e séries")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
        summary = "Cadastrar categoria",
        description = "Cria uma nova categoria que pode ser associada a filmes e séries"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoria cadastrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTOResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<CategoriaDTOResponse> inserir(
            @Parameter(description = "Dados da categoria a ser cadastrada", required = true)
            @Valid @RequestBody CategoriaDTORequest categoria) {
        CategoriaDTOResponse criado = categoriaService.inserir(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(
        summary = "Listar categorias",
        description = "Retorna todas as categorias cadastradas no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de categorias retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTOResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTOResponse>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @Operation(
        summary = "Buscar categoria por ID",
        description = "Retorna uma categoria específica pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTOResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse> buscar(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @Operation(
        summary = "Atualizar categoria",
        description = "Atualiza os dados de uma categoria existente pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTOResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse> atualizar(
            @Parameter(description = "Novos dados da categoria", required = true)
            @Valid @RequestBody CategoriaDTORequest categoria,
            @Parameter(description = "ID da categoria a ser atualizada", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.atualizar(id, categoria));
    }

    @Operation(
        summary = "Remover categoria",
        description = "Remove uma categoria do sistema pelo seu ID. Certifique-se de que a categoria não está em uso."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Categoria removida com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID da categoria a ser removida", required = true)
            @PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}