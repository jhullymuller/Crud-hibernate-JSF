/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsf.dao;

import br.com.jsf.entidade.Categoria;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public interface CategoriaDao extends BaseDao<Categoria, Long>{
   
    List<Categoria> pesquisarPorNome(String nome, Session sessao) throws HibernateException;

}
