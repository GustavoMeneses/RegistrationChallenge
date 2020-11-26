package com.registration.service;

import com.registration.model.Email;
import com.registration.model.PersonEmail;
import com.registration.repository.EmailRepository;
import com.registration.repository.PersonEmailRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final PersonEmailRepository personEmailRepository;

    public EmailService(
            EmailRepository emailRepository,
            PersonEmailRepository personEmailRepository
    ) {
        this.emailRepository = emailRepository;
        this.personEmailRepository = personEmailRepository;
    }

    @Transactional
    public Email criar(Email email) {
        Email registeredEmail = this.emailRepository.obter(email.getId(), false);
        if (registeredEmail != null) return registeredEmail;
        return this.emailRepository.gravar(email);
    }

    @Transactional
    public Email save(Email email, Long id) {
        email.setId(id);
        return this.emailRepository.gravar(email);
    }

    @Transactional
    public Email delete(Long id) {
        Email email = this.emailRepository.obter(id);

        Specification<PersonEmail> spec = Specification.where(PersonEmailRepository.byEmailId(email.getId()));
        PersonEmail personEmail = this.personEmailRepository.obter(spec);
        this.personEmailRepository.remover(personEmail.getId(), "Person email not found");
        this.emailRepository.remover(email.getId(), "Email not found");

        return email;
    }
}
