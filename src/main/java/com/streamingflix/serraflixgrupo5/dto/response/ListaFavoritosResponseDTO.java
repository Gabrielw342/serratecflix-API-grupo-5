package com.streamingflix.serraflixgrupo5.dto.response;

import java.time.LocalDate;

public class ListaFavoritosResponseDTO {

    private Long id;
    private String nomeLista;
    private Boolean privada;
    private LocalDate dataCriacao;
    private Long usuarioId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeLista() { return nomeLista; }
    public void setNomeLista(String nomeLista) { this.nomeLista = nomeLista; }
    public Boolean getPrivada() { return privada; }
    public void setPrivada(Boolean privada) { this.privada = privada; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}