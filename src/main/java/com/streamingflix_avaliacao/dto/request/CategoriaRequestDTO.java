package com.streamingflix_avaliacao.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
    
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;
    
    private String descricao;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}