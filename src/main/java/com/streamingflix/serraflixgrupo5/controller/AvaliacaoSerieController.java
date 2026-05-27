package com.streamingflix.serraflixgrupo5.controller;

import com.streamingflix.serraflixgrupo5.dto.request.AvaliacaoSerieRequest;
import com.streamingflix.serraflixgrupo5.dto.response.AvaliacaoSerieResponse;
import com.streamingflix.serraflixgrupo5.service.AvaliacaoSerieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes-series")
public class AvaliacaoSerieController {

    @Autowired
    private AvaliacaoSerieService avaliacaoSerieService;

    @PostMapping
    public ResponseEntity<AvaliacaoSerieResponse> salvar(@Valid @RequestBody AvaliacaoSerieRequest request) {
        
        AvaliacaoSerieResponse response = avaliacaoSerieService.salvar(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}