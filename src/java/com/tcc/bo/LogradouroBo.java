/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.LgrLogradouro;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class LogradouroBo {

    /**
     * Insere um obj do tipo LgrLogradouro no banco de dados
     *
     * @param logradouro
     * @return
     */
    public LgrLogradouro inserir(LgrLogradouro logradouro) {
        logradouro.setLgrCidade("São José dos Campos");
        logradouro.setLgrEstado("São Paulo");
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.save(logradouro);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return logradouro;
    }
    
    
    /**
     * Carrega uma lista do tipo LgrLogradouro no banco de dados filtrado por
     * cep
     *
     * @param cep
     * @return
     */
    public List<LgrLogradouro> carregarLogradourosPorCep(String cep) {
        Session session = new HibernateUtil().openSession();
        List<LgrLogradouro> lgrList = new ArrayList<LgrLogradouro>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select l from LgrLogradouro l where l.lgrCep like= :lgrCep");
            q.setParameter("lgrCep", cep);
            lgrList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lgrList != null && !lgrList.isEmpty()) {
            return lgrList;
        } else {
            return new ArrayList<LgrLogradouro>();
        }
    }

    /**
     * Carrega uma lista do tipo LgrLogradouro no banco de dados filtrado por
     * cep
     *
     * @param cep
     * @return
     */
    public LgrLogradouro carregarLogradouroPorDescricao(String descricao) {
        Session session = new HibernateUtil().openSession();
        List<LgrLogradouro> lgrList = new ArrayList<LgrLogradouro>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select l from LgrLogradouro l where l.lgrDescricao like :lgrDescricao");
            q.setParameter("lgrDescricao", "%" + descricao + "%");
            lgrList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lgrList != null && !lgrList.isEmpty()) {
            return lgrList.get(0);
        } else {
            return new LgrLogradouro();
        }
    }
    
    /**
     * Carrega uma lista do tipo LgrLogradouro no banco de dados filtrado por
     * cep
     *
     * @param cep
     * @return
     */
    public LgrLogradouro carregarLogradouroPorDescricaoECep(String descricao,String cep) {
        Session session = new HibernateUtil().openSession();
        List<LgrLogradouro> lgrList = new ArrayList<LgrLogradouro>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select l from LgrLogradouro l where l.lgrDescricao like :lgrDescricao and l.lgrCep like :lgrCep");
            q.setParameter("lgrDescricao", "%" + descricao + "%");
            q.setParameter("lgrCep", cep);
            lgrList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lgrList != null && !lgrList.isEmpty()) {
            return lgrList.get(0);
        } else {
            return new LgrLogradouro();
        }
    }
}
