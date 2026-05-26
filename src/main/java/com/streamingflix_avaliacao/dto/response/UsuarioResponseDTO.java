package com.streamingflix_avaliacao.dto.response;

import com.streamingflix_avaliacao.entity.Usuario;
import java.time.LocalDate;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String username;
    private LocalDate dataCriacao;
    private String logradouro;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Usuario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.username = entity.getUsername();
        this.dataCriacao = entity.getDataCriacao();
        this.logradouro = entity.getLogradouro();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
}