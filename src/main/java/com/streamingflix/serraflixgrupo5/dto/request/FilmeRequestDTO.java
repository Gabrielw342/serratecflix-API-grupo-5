package com.streamingflix.serraflixgrupo5.dto.request;

import java.time.LocalDate;

import java.util.List;

import com.streamingflix.serraflixgrupo5enum.ClassificacaoIndicativa;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FilmeRequestDTO {

    @NotBlank
    @Size(min= 1, max= 60)
    private String titulo;

    @NotBlank
    @Size(min= 1, max= 60)
    private String descricao;

    @NotNull
    private Integer duracao;

    @NotNull
    private LocalDate dataLancamento;

    @NotNull
    private ClassificacaoIndicativa classificacaoIndicativa;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double notaMedia;
    
    private List<Long> categoriasIds;

    public FilmeRequestDTO() {
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

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public ClassificacaoIndicativa getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(ClassificacaoIndicativa classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }
    
    public List<Long> getCategoriasIds() {
        return categoriasIds;
    }

    public void setCategoriasIds(List<Long> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }
}