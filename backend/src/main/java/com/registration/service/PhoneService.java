package com.registration.service;

import com.registration.model.PersonEmail;
import com.registration.model.PersonPhone;
import com.registration.model.Phone;
import com.registration.repository.PersonEmailRepository;
import com.registration.repository.PersonPhoneRepository;
import com.registration.repository.PhoneRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final PersonPhoneRepository personPhoneRepository;

    public PhoneService(
            PhoneRepository phoneRepository,
            PersonPhoneRepository personPhoneRepository
    ) {
        this.phoneRepository = phoneRepository;
        this.personPhoneRepository = personPhoneRepository;
    }

    @Transactional
    public Phone criar(Phone phone) {
        Phone registeredPhone = this.phoneRepository.obter(phone.getId(), false);
        if (registeredPhone != null) return registeredPhone;
        return this.phoneRepository.gravar(phone);
    }

    @Transactional
    public Phone save(Phone phone, Long id) {
        phone.setId(id);
        return this.phoneRepository.gravar(phone);
    }

    @Transactional
    public Phone delete(Long id) {
        Phone phone = this.phoneRepository.obter(id);

        Specification<PersonPhone> spec = Specification.where(PersonPhoneRepository.byPhoneId(phone.getId()));
        PersonPhone personPhone = this.personPhoneRepository.obter(spec);
        this.personPhoneRepository.remover(personPhone.getId(), "Person phone not found");
        this.phoneRepository.remover(phone.getId(), "Phone not found");

        return phone;
    }
}
