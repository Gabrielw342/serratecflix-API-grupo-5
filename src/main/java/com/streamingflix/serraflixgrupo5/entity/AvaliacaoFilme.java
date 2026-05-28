package com.streamingflix.serraflixgrupo5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Entity
@Table(name = "avaliacoes_filmes")
public class AvaliacaoFilme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer nota;

    @Column(length = 500)
    private String comentario;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;
    
    
    

	public AvaliacaoFilme() {
		super();
		
	}

	
	public AvaliacaoFilme(Long id, Integer nota, String comentario, LocalDate dataAvaliacao, Usuario usuario,
			Filme filme) {
		super();
		this.id = id;
		this.nota = nota;
		this.comentario = comentario;
		this.dataAvaliacao = dataAvaliacao;
		this.usuario = usuario;
		this.filme = filme;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(LocalDate dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

}