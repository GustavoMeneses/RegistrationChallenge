package com.registration.repository.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

abstract class Filtragem<E> {
	
	protected final EntityManager em;
	protected final Class<E> tipo;
	
	protected Filtragem(EntityManager em, Class<E> tipo) {
		this.em = em;
		this.tipo = tipo;
	}

	protected TypedQuery<E> criarQuery(Specification<E> spec) {
		return criarQuery(spec, PageRequest.of(0, Integer.MAX_VALUE));
//		return criarQuery(spec);
	}

	protected TypedQuery<E> criarQuery(Specification<E> spec, PageRequest pageRequest) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		if (builder == null) return null;
		CriteriaQuery<E> query = builder.createQuery(this.tipo);
		Sort sort = null;
		if (pageRequest != null) sort = pageRequest.getSort();
		
		Root<E> root = this.aplicarEspecificacao(spec, query);
		query.select(root);
		
		if(sort != null) { //por hora não tem fluxo que preencha esse sort
			query.orderBy(toOrders(sort, root, builder));
		}

		TypedQuery<E> typedQuery = em.createQuery(query);
		if (pageRequest != null && typedQuery != null) {
			Integer pageNumber = pageRequest.getPageNumber();
			Integer pageSize = pageRequest.getPageSize();
			typedQuery.setFirstResult(pageNumber * pageSize);
			typedQuery.setMaxResults(pageSize);
		}
		return typedQuery;
	}

    protected TypedQuery<Long> countQuery(Specification<E> spec) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        if (builder == null) return null;
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<E> root = this.aplicarEspecificacao(spec, countQuery);
        countQuery.select(builder.count(root));
        return em.createQuery(countQuery);
    }

	private <S> Root<E> aplicarEspecificacao(Specification<E> spec, CriteriaQuery<S> query) {
		Root<E> root = query.from(this.tipo);

        if(spec == null) {
			return root;
		}

        CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

        if(predicate != null) {
			query.where(predicate);
		}

        return root;
	}
}
