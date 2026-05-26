package com.streamingflix_avaliacao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ViaCepService {

    public String buscarLogradouroPorCep(String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String uri = "https://viacep.com.br/ws/" + cep + "/json/";
            
            @SuppressWarnings("unchecked")
            Map<String, String> response = restTemplate.getForObject(uri, Map.class);
            
            
            if (response != null && response.containsKey("logradouro")) {
                return response.get("logradouro");
            }
            
            
            throw new IllegalArgumentException("CEP não encontrado na base dos Correios.");
            
        } catch (IllegalArgumentException e) {
            throw e; 
        } catch (Exception e) {
            
            throw new RuntimeException("Erro ao consultar o serviço de CEP. Tente novamente mais tarde.");
        }
    }
}