package com.literatura.app.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class transformarDatos implements transfromaDatos {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T getDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
