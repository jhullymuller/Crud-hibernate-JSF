/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsf.dao;

import br.com.jsf.entidade.Categoria;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class CategoriaDaoImpl extends BaseDaoImpl<Categoria, Long>
                                implements CategoriaDao, Serializable{

    @Override
    public Categoria pesquisarPorId(Long id, Session sessao) 
                                            throws HibernateException {
        return (Categoria)sessao.get(Categoria.class, id);
    }

    @Override
    public List listarTodo(Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Categoria");
        return consulta.list();
    }

    @Override
    public List<Categoria> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Categoria where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
