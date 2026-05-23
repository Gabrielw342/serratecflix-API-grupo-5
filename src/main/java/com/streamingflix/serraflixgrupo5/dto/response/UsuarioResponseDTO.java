package com.streamingflix.serraflixgrupo5.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioResponseDTO {
	
	private Long id;
    private String nome;
    private String email;
    private String username;
    private LocalDate dataCriacao;
    
    private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private String getNome() {
		return nome;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	private String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
