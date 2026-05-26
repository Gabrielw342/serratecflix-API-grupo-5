package com.streamingflix_avaliacao.dto.response;

import com.streamingflix_avaliacao.entity.Categoria;

public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;

    public CategoriaResponseDTO() {}

    public CategoriaResponseDTO(Categoria entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
}