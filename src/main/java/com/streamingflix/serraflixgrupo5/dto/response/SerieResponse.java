package com.streamingflix.serraflixgrupo5.dto.response;

import java.time.LocalDate;
import java.util.List;

public class SerieResponse {
    
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataLancamento;  
    private Integer temporadas;
    private Integer episodios;
    private Double notaMedia;
    private List<String> categorias;

    public SerieResponse() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public Double getNotaMedia() {
        return notaMedia;
    }
    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public List<String> getCategorias() {
        return categorias;
    }
    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
}