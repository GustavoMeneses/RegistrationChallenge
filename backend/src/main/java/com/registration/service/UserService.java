package com.registration.service;

import com.registration.model.Login;
import com.registration.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Login salvar(Login login, Long id) {
        if (id != null) {
            login.setId(id);
            return this.userRepository.gravar(login);
        }
        return this.userRepository.gravar(login);
    }

    public Login recuperar(String login, String senha) {
        return this.userRepository.obter(UserRepository.porLogin(login).and(UserRepository.porSenha(senha)));
    }
}
