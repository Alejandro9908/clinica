package com.grupo2.clinica.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluye campos nulos del JSON
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private Map<String, String> errorFields; // Lista de errores de validación

    public ApiResponse(int status, String message, T data, Map<String, String> errorFields) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorFields = errorFields;
    }

    public ApiResponse(int status, String message, T data) {
        this(status, message, data, null);
    }

    public ApiResponse(int status, String message, Map<String, String> errorFields) {
        this(status, message, null, errorFields);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, String> errorFields) {
        this.errorFields = errorFields;
    }
}
