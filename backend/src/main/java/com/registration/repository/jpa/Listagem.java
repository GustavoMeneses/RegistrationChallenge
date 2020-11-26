package com.registration.repository.jpa;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Filtragem que gera ula mistagem de resultados.
 * 
 * @param <E>
 *            O tipo de entidade que essa listagem filtra.
 */
public class Listagem<E> extends Filtragem<E> {
	
	public Listagem(EntityManager em, Class<E> doTipo) {
		super(em, doTipo);
	}
	
	public List<E> por(Specification<E> especificacao) {
		TypedQuery<E> query = this.criarQuery(especificacao);
		if (query == null) return new ArrayList<>();
		return this.criarQuery(especificacao).getResultList();
	}

	public List<E> por(Specification<E> especificacao, PageRequest paginacao) {
		return this.criarQuery(especificacao, paginacao).getResultList();
	}

	public Long count(Specification<E> especificacao) {
		return this.countQuery(especificacao).getSingleResult();
	}

}
