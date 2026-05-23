package com.streamingflix.serraflixgrupo5.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "usuarios")
public class Usuario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String nome;
 
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true, nullable = false)
    private String email;
 
    @NotBlank(message = "Username é obrigatório")
    @Column(unique = true, nullable = false)
    private String username;
 
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Column(nullable = false)
    private String senha;
 
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Foto fotoPerfil;
 
    @Column(nullable = false)
    private LocalDate dataCriacao;
 
    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDate.now();
    }
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
 
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
 
    public Foto getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(Foto fotoPerfil) { this.fotoPerfil = fotoPerfil; }
 
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
}
