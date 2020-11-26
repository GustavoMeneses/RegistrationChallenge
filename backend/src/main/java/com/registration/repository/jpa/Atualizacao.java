package com.registration.repository.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

public class Atualizacao<E> {
    protected final EntityManager em;
    protected final Class<E> tipo;


    public Atualizacao(EntityManager em, Class<E> tipo) {
        this.em = em;
        this.tipo = tipo;
    }

    private CriteriaUpdate<E> criarUpdateQuery(Specification<E> spec, UpdateSetterFunction<E> updateSetterFunction) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        if (builder == null) return null;
        CriteriaUpdate<E> update = builder.createCriteriaUpdate(this.tipo);
        CriteriaQuery<E> query = builder.createQuery(this.tipo);
        Root<E> root = update.from(this.tipo);
        Predicate predicate = spec.toPredicate(root, query, builder);
        update.where(predicate);
        updateSetterFunction.set(update);
        return update;
    }

    public int atualizar(Specification<E> spec, UpdateSetterFunction<E> updateSetterFunction) {
        CriteriaUpdate<E> update = this.criarUpdateQuery(spec, updateSetterFunction);
        return em.createQuery(update).executeUpdate();
    }

    @FunctionalInterface
    public interface UpdateSetterFunction<E> {
        void set(CriteriaUpdate<E> update);
    }
}
