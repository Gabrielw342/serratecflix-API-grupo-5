package com.streamingflix_avaliacao.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class ListaFavoritosRequestDTO {
    
    @NotBlank(message = "O nome da lista é obrigatório")
    private String nomeLista;
    
    @NotNull(message = "É obrigatório definir se a lista é privada ou não")
    private Boolean privada;
    
    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;
    
    private List<Long> filmesIds;
    
    private List<Long> seriesIds;

    
    public String getNomeLista() { return nomeLista; }
    public void setNomeLista(String nomeLista) { this.nomeLista = nomeLista; }
    
    public Boolean getPrivada() { return privada; }
    public void setPrivada(Boolean privada) { this.privada = privada; }
    
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    
    public List<Long> getFilmesIds() { return filmesIds; }
    public void setFilmesIds(List<Long> filmesIds) { this.filmesIds = filmesIds; }
    
    public List<Long> getSeriesIds() { return seriesIds; }
    public void setSeriesIds(List<Long> seriesIds) { this.seriesIds = seriesIds; }
}