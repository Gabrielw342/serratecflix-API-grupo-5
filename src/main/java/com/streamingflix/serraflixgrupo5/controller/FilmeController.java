package com.streamingflix.serraflixgrupo5.controller;

import java.util.List;

import com.streamingflix.serraflixgrupo5.dto.response.OmdbResponseDTO;
import com.streamingflix.serraflixgrupo5.service.OmdbService;
import io.swagger.v3.oas.annotations.Operation;
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
public class FilmeController {
	
	@Autowired
	private OmdbService omdbService;

    @Autowired
    private FilmeService filmeService;
    
    @Operation(summary = "usando essa API externa voce pode pesquisar filmes fora da nossa biblioteca")
    @GetMapping("/omdb")
    public OmdbResponseDTO buscarNaOmdb(
            @RequestParam String titulo) {

        return omdbService.buscarFilme(titulo);
    }

    @Operation(summary = "usando isso voce cadastra um filme")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmeResponseDTO salvar(@RequestBody @Valid FilmeRequestDTO dto) {

        return filmeService.salvar(dto);
    }
    
    @PostMapping("/importar/{titulo}")
    @Operation(summary = "Importa filme da OMDb e salva no banco")
    public FilmeResponseDTO importarFilme(
            @PathVariable String titulo) {

        return filmeService.importarFilme(titulo);
    }

    @Operation(summary = "voce busca todos os filmes")
    @GetMapping
    public List<FilmeResponseDTO> listarTodos() {

        return filmeService.listarTodos();
    }

    @Operation(summary = "voce busca um filme pela sua id")
    @GetMapping("/{id}")
    public FilmeResponseDTO buscarPorId(@PathVariable Long id) {

        return filmeService.buscarPorId(id);
    }
    
    @GetMapping("/ranking")
    @Operation(summary = "Lista filmes da maior nota para menor bem maneiro e silmples")
    public List<FilmeResponseDTO> listarPorMaiorNota() {

    	
        return filmeService.listarPorMaiorNota();
    }

    @Operation(summary = "deleta um filme pela sua id")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        filmeService.deletar(id);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "atualiza dados de um filme pela sua id")
    public FilmeResponseDTO atualizar(
    		@PathVariable Long id,
    		@RequestBody @Valid FilmeRequestDTO dto) {
    	return filmeService.atualizar(id,dto);
    }
    
}
