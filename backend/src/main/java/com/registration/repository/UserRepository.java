package com.registration.repository;

import com.registration.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepository extends Repositorio<Login> {

    @Autowired
    public UserRepository(EntityManager em) {
        super(em, Login.class);
    }

//    public static Specification<Elo> porStAtivo(SituacaoEnum situacao) {
//        return (Root<Elo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
//                .equal(root.get("stAtivo"), situacao.equals(SituacaoEnum.ativo));
//    }

    public static Specification<Login> porLogin(String login) {
        return (Root<Login> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("login"), login);
    }

    public static Specification<Login> porSenha(String senha) {
        return (Root<Login> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> cb
                .equal(root.get("password"), senha);
    }
}
