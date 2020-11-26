package com.registration.repository.jpa;

import javax.persistence.EntityManager;
import java.util.List;

public class Gravacao<E> {
	
	private final EntityManager em;
	
	public Gravacao(EntityManager em) {
		this.em = em;		
	}
	
	public E gravar(E entidade) {
		return em.merge(entidade);
	}

	public void gravarColecao(List<E> list) {
		for (int i = 0; i < list.stream().count(); i++) {
			if (i > 0 && i % 20 == 0) {
				this.em.flush();
				this.em.clear();
			}
			this.em.persist(list.get(i));
		}
	}
}
