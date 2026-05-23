package com.streamingflix.serraflixgrupo5.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ListaFavoritosRequestDTO {

    @NotBlank(message = "O nome da lista não pode estar em branco")
    private String nomeLista;

    @NotNull(message = "A visibilidade da lista deve ser informada")
    private Boolean privada;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    public String getNomeLista() { return nomeLista; }
    public void setNomeLista(String nomeLista) { this.nomeLista = nomeLista; }
    public Boolean getPrivada() { return privada; }
    public void setPrivada(Boolean privada) { this.privada = privada; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}