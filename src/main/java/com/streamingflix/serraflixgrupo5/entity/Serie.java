package com.streamingflix.serraflixgrupo5.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Integer temporadas;

    private Integer episodios;

    private LocalDate dataLancamento;

    @ManyToMany
    @JoinTable(
        name = "serie_categoria",
        joinColumns = @JoinColumn(name = "serie_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<AvaliacaoSerie> avaliacoes = new ArrayList<>();

    @ManyToMany(mappedBy = "series")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    public Serie() {
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

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<AvaliacaoSerie> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoSerie> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<ListaFavoritos> getListasFavoritos() {
        return listasFavoritos;
    }

    public void setListasFavoritos(List<ListaFavoritos> listasFavoritos) {
        this.listasFavoritos = listasFavoritos;
    }
}