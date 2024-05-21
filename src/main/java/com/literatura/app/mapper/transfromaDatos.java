package com.literatura.app.mapper;

public interface transfromaDatos {//interfaz
    <T> T getDatos(String json, Class<T> clase);
}
