package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.UsuarioRequestDTO;
import com.streamingflix.serraflixgrupo5.dto.response.UsuarioResponseDTO;
import com.streamingflix.serraflixgrupo5.entity.Foto;
import com.streamingflix.serraflixgrupo5.service.FotoService;
import com.streamingflix.serraflixgrupo5.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e fotos de perfil")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FotoService fotoService;

    @Operation(
        summary = "Criar novo usuário",
        description = "Cadastra um novo usuário na plataforma SerraFlix. O email e o username devem ser únicos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuário criado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na requisição (email inválido, senha curta, campos obrigatórios ausentes)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email ou username já cadastrado",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(
            @Parameter(description = "Dados do novo usuário", required = true)
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dto));
    }

    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna a lista de todos os usuários cadastrados na plataforma"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de usuários retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os dados de um usuário específico pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados cadastrais de um usuário existente pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class))
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
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @Parameter(description = "ID do usuário a ser atualizado", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do usuário", required = true)
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @Operation(
        summary = "Deletar usuário",
        description = "Remove um usuário da plataforma pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Usuário deletado com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Fazer upload da foto de perfil",
        description = "Faz o upload de uma imagem como foto de perfil do usuário. Formatos aceitos: JPEG, PNG."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Foto enviada com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Arquivo inválido ou formato não suportado",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content = @Content
        )
    })
    @PostMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFoto(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id,
            @Parameter(description = "Arquivo de imagem para foto de perfil", required = true)
            @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        fotoService.salvarFoto(id, arquivo);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Buscar foto de perfil",
        description = "Retorna a imagem da foto de perfil do usuário em seu formato original"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Foto retornada com sucesso",
            content = @Content(mediaType = "image/*")
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário ou foto não encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> buscarFoto(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        Foto foto = fotoService.buscarFoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + foto.getNome() + "\"")
                .contentType(MediaType.parseMediaType(foto.getTipo()))
                .body(foto.getDados());
    }
    
    @GetMapping("/verificar")
    public ResponseEntity<String> verificarEmail(@RequestParam("token") String token) {
        usuarioService.verificarEmail(token);
        return ResponseEntity.ok("E-mail verificado com sucesso!");
    }

    @Operation(
        summary = "Deletar foto de perfil",
        description = "Remove a foto de perfil do usuário pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Foto deletada com sucesso",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário ou foto não encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/{id}/foto")
    public ResponseEntity<Void> deletarFoto(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable Long id) {
        fotoService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }
}