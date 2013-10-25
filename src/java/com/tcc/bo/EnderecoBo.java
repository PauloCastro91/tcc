/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.LgrLogradouro;
import com.tcc.model.PssPessoa;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class EnderecoBo {

    /**
     * Insere um obj do tipo EndEndereco no banco de dados
     *
     * @param endereco
     * @return
     */
    public EndEndereco inserir(EndEndereco endereco) {
        toUpperCase(endereco);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.save(endereco);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return endereco;
    }

    /**
     * Delete um obj do tipo EndEndereco no banco de dados
     *
     * @param endereco
     * @return
     */
    public String excluir(EndEndereco endereco) {
        String toString = endereco.toString();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.delete(endereco);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return toString;
    }

    /**
     * Altera um obj do tipo EndEndereco no banco de dados
     *
     * @param endereco
     * @return
     */
    public EndEndereco alterar(EndEndereco endereco) {
        toUpperCase(endereco);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(endereco);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return endereco;
    }

    public void toUpperCase(EndEndereco endereco) {
        if (endereco.getLgr() != null) {
            if (endereco.getLgr().getLgrDescricao() != null && !endereco.getLgr().getLgrDescricao().isEmpty()) {
                endereco.getLgr().setLgrDescricao(endereco.getLgr().getLgrDescricao().toUpperCase());
            }
            if (endereco.getLgr().getLgrCidade() != null && !endereco.getLgr().getLgrCidade().isEmpty()) {
                endereco.getLgr().setLgrCidade(endereco.getLgr().getLgrCidade().toUpperCase());
            }
            if (endereco.getLgr().getLgrEstado() != null && !endereco.getLgr().getLgrEstado().isEmpty()) {
                endereco.getLgr().setLgrEstado(endereco.getLgr().getLgrEstado().toUpperCase());
            }
        }
        if (endereco.getEndComplemento() != null && !endereco.getEndComplemento().isEmpty()) {
            endereco.setEndComplemento(endereco.getEndComplemento().toUpperCase());
        }
        if (endereco.getEndReferencia() != null && !endereco.getEndReferencia().isEmpty()) {
            endereco.setEndReferencia(endereco.getEndReferencia().toUpperCase());
        }
    }

    /**
     * Carrega um obj do tipo EndEndereco no banco de dados
     *
     * @param endereco
     * @return
     */
    public EndEndereco carregar(Integer endId) {
        Session session = new HibernateUtil().openSession();
        List<EndEndereco> endList = new ArrayList<EndEndereco>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select e from EndEndereco e where e.endId = :endId");
            q.setParameter("endId", endId);
            endList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (endList != null && !endList.isEmpty()) {
            return endList.get(0);
        } else {
            return new EndEndereco();
        }
    }

    /**
     * Lista todos os enderecos de uma pessoa
     *
     * @param pss
     * @return
     */
    public List<EndEndereco> listarPorPessoa(PssPessoa pss) {
        List<EndEndereco> endList = new ArrayList<EndEndereco>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select e from EndEndereco e where e.pss.pssId = :pssId");
            q.setParameter("pssId", pss.getPssId());
            endList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (endList != null && !endList.isEmpty()) {
            return endList;
        } else {
            return new ArrayList<EndEndereco>();
        }
    }

    public void deletarEmLote(List<EndEndereco> endList) {
        int x = 0;
        Session session = new HibernateUtil().openSession();
        try {
            for (EndEndereco end : endList) {
                session.delete(end);
                if (x % 10 == 0) {
                    session.flush();
                }
                x++;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
