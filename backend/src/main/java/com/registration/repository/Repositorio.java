package com.registration.repository;

import com.registration.exceptions.NegocioException;
import com.registration.exceptions.RecursoNaoEncontradoException;
import com.registration.repository.jpa.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class Repositorio<T> {
    protected final Listagem<T> listagem;
    private final Selecao<T> selecao;
    private final Gravacao<T> gravacao;
    private final Delecao<T> delecao;
    private final Atualizacao<T> atualizacao;

    public Repositorio(EntityManager em, Class<T> tipo) {
        this.listagem = new Listagem<>(em, tipo);
        this.selecao = new Selecao<>(em, tipo);
        this.gravacao = new Gravacao<>(em);
        this.delecao = new Delecao<>(em);
        this.atualizacao = new Atualizacao<>(em, tipo);
    }

    public List<T> listagem(Specification<T> specification, PageRequest pageRequest) {
        return this.listagem.por(specification, pageRequest);
    }

    public List<T> listagem(Specification<T> specification) {
        return this.listagem.por(specification, PageRequest.of(0, Integer.MAX_VALUE));
    }

    public Page<T> listar(Specification<T> specification, PageRequest pageRequest) {
        List<T> resultados = this.listagem.por(specification, pageRequest);
        Long count = this.listagem.count(specification);
        return new PageImpl<>(resultados, pageRequest, count);
    }

    public Page<T> listar(Specification<T> specification) {
        return this.listar(specification, PageRequest.of(0, Integer.MAX_VALUE));
    }


    public T obter(Specification<T> specification) {
        List<T> lista = this.listagem.por(specification);
        if (lista.isEmpty()) {
            return null;
        }
        return this.listagem.por(specification).get(0);
    }

    public T obter(Long id) {
        if (id == null) {
            return null;
        }
        return this.obter(id, false);
    }

    public T obter(Long id, Boolean throwing) {
        if (id == null) {
            return null;
        }
        Optional<T> resultado = this.selecao.por(id);
        if (!throwing) return resultado.orElse(null);
        else return resultado.orElseThrow(RecursoNaoEncontradoException::new);
    }

    public T obter(String id, Boolean throwing) {
        Optional<T> resultado = this.selecao.por(id);
        if (!throwing) return resultado.orElse(null);
        else return resultado.orElseThrow(RecursoNaoEncontradoException::new);
    }

    public T gravar(T entidade) {
        return this.gravacao.gravar(entidade);
    }

    public void gravarColecao(List<T> list) {
        this.gravacao.gravarColecao(list);
    }

    public T remover(Long id, String message) {
        T entidade = this.obter(id);
        if (entidade == null) throw new NegocioException(message);
        return this.deletar(entidade);
    }

    public T deletar(T entidade) {
        return this.delecao.deletar(entidade);
    }

    public int atualizar(Specification<T> specification, Atualizacao.UpdateSetterFunction<T> setterFunction) {
        return this.atualizacao.atualizar(specification, setterFunction);
    }
}
