package com.streamingflix.serraflixgrupo5.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class SerieRequest {
    
    @NotBlank(message = "O título da série é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "A data de lançamento é obrigatória")
    private LocalDate dataLancamento;  

    @NotNull(message = "O número de temporadas é obrigatório")
    @Min(value = 1, message = "A série deve ter pelo menos 1 temporada")
    private Integer temporadas;

    @NotNull(message = "O número de episódios é obrigatório")
    @Min(value = 1, message = "A série deve ter pelo menos 1 episódio")
    private Integer episodios;

    private List<Long> categoriasIds;
    
    public SerieRequest() {
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getTemporadas() {
        return temporadas;
    }
    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    public Integer getEpisodios() {
        return episodios;
    }
    public void setEpisodios(Integer episodios) {
        this.episodios = episodios;
    }

    public List<Long> getCategoriasIds() {
        return categoriasIds;
    }
    public void setCategoriasIds(List<Long> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }
}
