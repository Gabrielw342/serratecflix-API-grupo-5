package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoFilmeRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoFilmeResponse;
import com.streamingflix.serraflixgrupo5.service.AvaliacaoFilmeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-filmes")
public class AvaliacaoFilmeController {

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @PostMapping
    public ResponseEntity<AvaliacaoFilmeResponse> salvar(@Valid @RequestBody AvaliacaoFilmeRequest request) {
        
        
        AvaliacaoFilmeResponse response = avaliacaoFilmeService.salvar(request);
        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}