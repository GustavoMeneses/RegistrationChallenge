package com.registration.service;

import com.registration.model.PersonPhone;
import com.registration.repository.PersonPhoneRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonPhoneService {

    private final PersonPhoneRepository personPhoneRepository;

    public PersonPhoneService(PersonPhoneRepository personPhoneRepository) {
        this.personPhoneRepository = personPhoneRepository;
    }

    @Transactional
    public PersonPhone criar(PersonPhone personPhone) {
        PersonPhone registeredPersonPhone = this.personPhoneRepository.obter(personPhone.getId(), false);
        if (registeredPersonPhone != null) return registeredPersonPhone;
        return this.personPhoneRepository.gravar(personPhone);
    }
}
