package com.streamingflix.serraflixgrupo5.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
 
public class UsuarioResponseDTO {
 
    private Long id;
    private String nome;
    private String email;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao;
    private String fotoPerfil;
 
    public UsuarioResponseDTO() {}
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
 
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
 
    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}
