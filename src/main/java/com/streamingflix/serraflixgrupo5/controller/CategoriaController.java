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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamingflix.serraflixgrupo5.dto.request.CategoriaDTORequest;
import com.streamingflix.serraflixgrupo5.dto.response.CategoriaDTOResponse;
import com.streamingflix.serraflixgrupo5.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Cadastrar uma categoria")
    @PostMapping
    public ResponseEntity<CategoriaDTOResponse> inserir(@Valid @RequestBody CategoriaDTORequest categoria){
        CategoriaDTOResponse criado = categoriaService.inserir(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas")
    @GetMapping
    public ResponseEntity<List<CategoriaDTOResponse>> listar(){
        return ResponseEntity.ok(categoriaService.listar());
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse> buscar(@PathVariable Long id){
        CategoriaDTOResponse categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse> atualizar(
            @Valid @RequestBody CategoriaDTORequest categoria,
            @PathVariable Long id) {

        CategoriaDTOResponse atualizado = categoriaService.atualizar(id, categoria);

        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Remover categoria", description = "Remove uma categoria pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        boolean removido = categoriaService.deletar(id);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

