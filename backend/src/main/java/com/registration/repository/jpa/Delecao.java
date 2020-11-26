package com.registration.repository.jpa;

import javax.persistence.EntityManager;

public class Delecao<E> {

    private final EntityManager em;

    public Delecao(EntityManager em) {
        this.em = em;
    }

    public E deletar(E entidade) {
        em.remove(entidade);
        return entidade;
    }
}
