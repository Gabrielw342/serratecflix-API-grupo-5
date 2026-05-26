package com.streamingflix_avaliacao.dto.response;

import com.streamingflix_avaliacao.entity.ListaFavoritos;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListaFavoritosResponseDTO {

    private Long id;
    private String nomeLista;
    private Boolean privada;
    private LocalDate dataCriacao;
    private String nomeUsuario;
    private List<String> filmes;
    private List<String> series;

    public ListaFavoritosResponseDTO() {}

    public ListaFavoritosResponseDTO(ListaFavoritos entity) {
        this.id = entity.getId();
        this.nomeLista = entity.getNomeLista();
        this.privada = entity.getPrivada();
        this.dataCriacao = entity.getDataCriacao();
        
        if(entity.getUsuario() != null) {
            this.nomeUsuario = entity.getUsuario().getNome();
        }
        if(entity.getFilmes() != null) {
            this.filmes = entity.getFilmes().stream()
                .map(f -> f.getTitulo())
                .collect(Collectors.toList());
        }
        if(entity.getSeries() != null) {
            this.series = entity.getSeries().stream()
                .map(s -> s.getTitulo())
                .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public String getNomeLista() { return nomeLista; }
    public Boolean getPrivada() { return privada; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public String getNomeUsuario() { return nomeUsuario; }
    public List<String> getFilmes() { return filmes; }
    public List<String> getSeries() { return series; }
}