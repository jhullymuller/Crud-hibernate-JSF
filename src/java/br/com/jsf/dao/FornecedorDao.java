/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsf.dao;

import br.com.jsf.entidade.Fornecedor;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public interface FornecedorDao extends BaseDao<Fornecedor, Long>{
   
    List<Fornecedor> pesquisarPorNome(String nome, Session sessao) throws HibernateException;

}
