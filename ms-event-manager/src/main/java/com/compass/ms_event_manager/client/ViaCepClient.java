package com.compass.ms_event_manager.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.compass.ms_event_manager.dto.AddressDTO;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    @GetMapping("/{cep}/json/")
    AddressDTO getAddressByCep(@PathVariable("cep") String cep);
}