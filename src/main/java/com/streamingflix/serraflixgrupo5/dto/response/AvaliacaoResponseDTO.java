package com.streamingflix_avaliacao.dto.response;

import com.streamingflix_avaliacao.entity.AvaliacaoFilme;
import com.streamingflix_avaliacao.entity.AvaliacaoSerie;
import java.time.LocalDate;

public class AvaliacaoResponseDTO {

    private Long id;
    
    
    private Double nota;
    
    private String comentario;
    private LocalDate dataAvaliacao;
    private String nomeUsuario;
    private String tituloConteudo; 

    public AvaliacaoResponseDTO() {}

    
    public AvaliacaoResponseDTO(AvaliacaoFilme entity) {
        this.id = entity.getId();
        this.nota = entity.getNota();
        this.comentario = entity.getComentario();
        this.dataAvaliacao = entity.getDataAvaliacao();
        
        if(entity.getUsuario() != null) {
            this.nomeUsuario = entity.getUsuario().getNome();
        }
        if(entity.getFilme() != null) {
            this.tituloConteudo = entity.getFilme().getTitulo();
        }
    }

    
    public AvaliacaoResponseDTO(AvaliacaoSerie entity) {
        this.id = entity.getId();
        this.nota = entity.getNota();
        this.comentario = entity.getComentario();
        this.dataAvaliacao = entity.getDataAvaliacao();
        
        if(entity.getUsuario() != null) {
            this.nomeUsuario = entity.getUsuario().getNome();
        }
        if(entity.getSerie() != null) {
            this.tituloConteudo = entity.getSerie().getTitulo();
        }
    }

    
    public Long getId() { return id; }
    
    
    public Double getNota() { return nota; }
    
    public String getComentario() { return comentario; }
    public LocalDate getDataAvaliacao() { return dataAvaliacao; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getTituloConteudo() { return tituloConteudo; }
}