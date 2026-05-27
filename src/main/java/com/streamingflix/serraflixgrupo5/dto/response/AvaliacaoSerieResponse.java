package com.streamingflix.serraflixgrupo5.dto.response;

public class AvaliacaoSerieResponse {

    private Long id;
    private Integer nota;
    private String comentario;
    private Long serieId;
    private Long usuarioId;

    public AvaliacaoSerieResponse() {
    }

    public AvaliacaoSerieResponse(Long id, Integer nota, String comentario, Long serieId, Long usuarioId) {
        this.id = id;
        this.nota = nota;
        this.comentario = comentario;
        this.serieId = serieId;
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

    public Long getSerieId() {
        return serieId;
    }

    public void setSerieId(Long serieId) {
        this.serieId = serieId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}