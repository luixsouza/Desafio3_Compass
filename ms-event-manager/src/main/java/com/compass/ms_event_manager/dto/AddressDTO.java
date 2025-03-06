package com.compass.ms_event_manager.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}

