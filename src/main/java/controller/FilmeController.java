package controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import dto.request.FilmeRequestDTO;
import dto.response.FilmeResponseDTO;
import jakarta.validation.Valid;
import service.FilmeService;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Operation(summary = "usando isso voce cadastra um filme")
    @PostMapping
    public FilmeResponseDTO salvar(@RequestBody @Valid FilmeRequestDTO dto) {

        return filmeService.salvar(dto);
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

    @Operation(summary = "deleta um filme pela sua id")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        filmeService.deletar(id);
    }
}