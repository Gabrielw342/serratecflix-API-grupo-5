package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.ListaFavoritosRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.ListaFavoritosResponseDTO;
import com.streamingflix.serraflixgrupo5.service.ListaFavoritosService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
@Tag(name = "Listas de Favoritos", description = "Endpoints para gerenciamento de listas de favoritos dos usuários")
public class ListaFavoritosController {

    @Autowired
    private ListaFavoritosService service;

    @Operation(
        summary = "Criar lista de favoritos",
        description = "Cria uma nova lista de favoritos para um usuário. A lista pode ser pública ou privada."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Lista criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ListaFavoritosResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ListaFavoritosResponseDTO> criar(
            @Parameter(description = "Dados da nova lista de favoritos", required = true)
            @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        ListaFavoritosResponseDTO novaLista = service.criarLista(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
    }

    @Operation(
        summary = "Listar todas as listas de favoritos",
        description = "Retorna todas as listas de favoritos cadastradas no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listas retornadas com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ListaFavoritosResponseDTO.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<ListaFavoritosResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(
        summary = "Buscar lista de favoritos por ID",
        description = "Retorna uma lista de favoritos específica pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista encontrada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ListaFavoritosResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista não encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> buscarPorId(
            @Parameter(description = "ID da lista de favoritos", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Atualizar lista de favoritos",
        description = "Atualiza os dados de uma lista de favoritos existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ListaFavoritosResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista não encontrada",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ListaFavoritosResponseDTO> atualizar(
            @Parameter(description = "ID da lista de favoritos", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da lista", required = true)
            @Valid @RequestBody ListaFavoritosRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(
        summary = "Deletar lista de favoritos",
        description = "Remove uma lista de favoritos pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Lista deletada com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista não encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da lista de favoritos", required = true)
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Copiar lista de favoritos para outro usuário",
        description = "Cria uma cópia de uma lista de favoritos existente e a atribui a outro usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Lista copiada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ListaFavoritosResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Lista de origem ou usuário de destino não encontrado",
            content = @Content
        )
    })
    @PostMapping("/{listaId}/copiar/para/{usuarioId}")
    public ResponseEntity<ListaFavoritosResponseDTO> copiarLista(
            @Parameter(description = "ID da lista a ser copiada", required = true)
            @PathVariable Long listaId,
            @Parameter(description = "ID do usuário que receberá a cópia", required = true)
            @PathVariable Long usuarioId) {
        ListaFavoritosResponseDTO copia = service.copiarLista(listaId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }
}