package com.registration.service;

import com.registration.model.Address;
import com.registration.model.Person;
import com.registration.model.PersonEmail;
import com.registration.model.PersonPhone;
import com.registration.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final PersonEmailRepository personEmailRepository;
    private final PersonPhoneRepository personPhoneRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    public PersonService(
            PersonRepository personRepository,
            AddressRepository addressRepository,
            PersonEmailRepository personEmailRepository,
            PersonPhoneRepository personPhoneRepository,
            EmailRepository emailRepository,
            PhoneRepository phoneRepository
    ) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.personEmailRepository = personEmailRepository;
        this.personPhoneRepository = personPhoneRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public Person criar(Person person) {
        Person registeredPerson = this.personRepository.obter(person.getId(), false);
        if (registeredPerson != null) return registeredPerson;
        return this.personRepository.gravar(person);
    }

    @Transactional
    public Person save(Person person, Long id) {
        person.setId(id);
        return this.personRepository.gravar(person);
    }

    @Transactional
    public Person delete(Long id) {
        Person person = this.personRepository.obter(id);
        Address address = this.addressRepository.obter(person.getAddress().getId());

        Specification<PersonEmail> spec = Specification.where(PersonEmailRepository.byPersonId(person.getId()));
        List<PersonEmail> personEmails = this.personEmailRepository.listagem(spec);
        for (PersonEmail personEmail : personEmails) {
            this.emailRepository.remover(personEmail.getEmail().getId(), "Email not found");
        }

        Specification<PersonPhone> spec2 = Specification.where(PersonPhoneRepository.byPersonId(person.getId()));
        List<PersonPhone> personPhones = this.personPhoneRepository.listagem(spec2);
        for (PersonPhone personPhone : personPhones) {
            this.phoneRepository.remover(personPhone.getPhone().getId(), "Phone not found");
        }

        this.personRepository.remover(person.getId(), "Person not found");
        this.addressRepository.remover(address.getId(), "Address not found");

        return person;
    }
}
