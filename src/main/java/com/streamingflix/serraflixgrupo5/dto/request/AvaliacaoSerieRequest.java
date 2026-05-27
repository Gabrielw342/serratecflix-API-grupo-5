package com.streamingflix.serraflixgrupo5.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoSerieRequest {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 0, message = "A nota não pode ser menor que 0.")
    @Max(value = 10, message = "A nota não pode ser maior que 10.")
    private Integer nota;

    @Size(max = 500, message = "O comentário não pode passar de 500 caracteres.")
    private String comentario;

    @NotNull(message = "O ID da série é obrigatório.")
    private Long serieId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    public AvaliacaoSerieRequest() {
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}