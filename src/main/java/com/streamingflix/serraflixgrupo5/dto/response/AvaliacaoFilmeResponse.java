package com.streamingflix.serraflixgrupo5.dto.response;

public class AvaliacaoFilmeResponse {

    private Long id;
    private Integer nota;
    private String comentario;
    private Long filmeId;
    private Long usuarioId;

    public AvaliacaoFilmeResponse() {
    }

    public AvaliacaoFilmeResponse(Long id, Integer nota, String comentario, Long filmeId, Long usuarioId) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.filmeId = filmeId;
        this.usuarioId = usuarioId;
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

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}