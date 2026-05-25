package com.streamingflix.serraflixgrupo5.dto.response;

import com.streamingflix.serraflixgrupo5.entity.Categoria;

public class CategoriaDTOResponse {
    private Long id;
    private String nome;
    private String descricao;

    public CategoriaDTOResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }

    public CategoriaDTOResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}