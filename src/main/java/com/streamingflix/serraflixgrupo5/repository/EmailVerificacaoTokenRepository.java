package com.streamingflix.serraflixgrupo5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamingflix.serraflixgrupo5.entity.EmailVerificacaoToken;

public interface EmailVerificacaoTokenRepository extends JpaRepository<EmailVerificacaoToken, Long> {

    Optional<EmailVerificacaoToken> findByToken(String token);

    void deleteAllByUsuario_Id(Long usuarioId);
}