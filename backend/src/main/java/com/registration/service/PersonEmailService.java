package com.registration.service;

import com.registration.model.PersonEmail;
import com.registration.repository.PersonEmailRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonEmailService {

    private final PersonEmailRepository personEmailRepository;

    public PersonEmailService(PersonEmailRepository personEmailRepository) {
        this.personEmailRepository = personEmailRepository;
    }

    @Transactional
    public PersonEmail criar(PersonEmail personEmail) {
        PersonEmail registeredPersonEmail = this.personEmailRepository.obter(personEmail.getId(), false);
        if (registeredPersonEmail != null) return registeredPersonEmail;
        return this.personEmailRepository.gravar(personEmail);
    }
}
