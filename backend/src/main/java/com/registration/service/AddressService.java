package com.registration.service;

import com.google.gson.Gson;
import com.registration.model.Address;
import com.registration.model.Cep;
import com.registration.repository.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address create(Address address) {
        Address registeredAddress = this.addressRepository.obter(address.getId(), false);
        if (registeredAddress != null) return registeredAddress;
        return this.addressRepository.gravar(address);
    }

    @Transactional
    public Address save(Address address, Long id) {
        address.setId(id);
        return this.addressRepository.gravar(address);
    }

    @Transactional
    public Cep getCep(String cep) {
        String urlGet = "http://viacep.com.br/ws/" + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        Gson gson = new Gson();

        response = restTemplate.getForEntity(urlGet, String.class);

        return gson.fromJson(response.getBody(), Cep.class);
    }
}
