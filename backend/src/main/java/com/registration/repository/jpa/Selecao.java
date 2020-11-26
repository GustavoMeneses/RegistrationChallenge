package com.registration.repository.jpa;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

/**
 * Seletor de uma entidade de um dado tipo na base de dados.
 * 
 * @param <E>
 */
public class Selecao<E> extends Filtragem<E> {

	public Selecao(EntityManager em, Class<E> tipo) {
		super(em, tipo);
	}
	
	public Optional<E> por(Serializable id) {
		return Optional.ofNullable(this.em.find(this.tipo, id));
	}

}
