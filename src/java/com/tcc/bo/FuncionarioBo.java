/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.CrgCargo;
import com.tcc.model.FunFuncionario;
import com.tcc.model.PssPessoa;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

public class FuncionarioBo {

    /**
     * Lista um funcionario (ativo) por seu pssId
     *
     * @author paulo.castro
     */
    public FunFuncionario carregarFuncionarioPorPessoa(PssPessoa pss) throws Exception {
        List<FunFuncionario> list = new ArrayList<FunFuncionario>();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select f from FunFuncionario f where f.pss.pssId = :pssId and f.funAtivo is true");
            q.setParameter("pssId", pss.getPssId());
            list = q.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return new FunFuncionario();
        }
    }

    /**
     * Insere um obj do tipo FunFuncionario no banco de dados
     *
     * @param funcionario
     * @return
     */
    public FunFuncionario inserir(FunFuncionario funcionario) throws Exception {
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.save(funcionario);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return funcionario;
    }

    /**
     * Altera um obj do tipo FunFuncionario no banco de dados
     *
     * @param funcionario
     * @return
     */
    public FunFuncionario alterar(FunFuncionario funcionario) throws Exception {
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(funcionario);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return funcionario;
    }

    public List<FunFuncionario> listarFuncionariosAtivos() throws Exception {
        List<FunFuncionario> list = new ArrayList<FunFuncionario>();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select f from FunFuncionario f where f.funAtivo is true");
            if (q.list() != null && !q.list().isEmpty()) {
                list = q.list();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return list;
    }
}
