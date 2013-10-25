/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

/**
 *
 * @author paulo.castro
 */
import com.tcc.model.CntContato;
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
public class ContatoBo {

    /**
     * Insere um obj do tipo CntContato no banco de dados
     *
     * @param contato
     * @return
     */
    public CntContato inserir(CntContato contato) {
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.persist(contato);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return contato;
    }

    /**
     * Exclui um obj do tipo CntContato no banco de dados
     *
     * @param contato
     * @return ToString do contato excluido
     */
    public String excluir(CntContato contato) {
        Session session = new HibernateUtil().openSession();
        String c = contato.toString();
        try {
            session.getTransaction().begin();
            session.delete(contato);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return c;
    }

    /**
     * Altera um obj do tipo CntContato no banco de dados
     *
     * @param contato
     * @return
     */
    public CntContato alterar(CntContato contato) {
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(contato);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return contato;
    }

    /**
     * Lista todos os contatos de uma pessoa
     *
     * @param pss
     * @return
     */
    public List<CntContato> listarPorPessoa(PssPessoa pss) {
        List<CntContato> cntList = new ArrayList<CntContato>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CntContato c where c.pss.pssId = :pssId");
            q.setParameter("pssId", pss.getPssId());
            cntList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (cntList != null && !cntList.isEmpty()) {
            return cntList;
        } else {
            return new ArrayList<CntContato>();
        }
    }

    /**
     * Carrega um cntContato por seu id
     *
     * @param id
     * @return
     */
    public CntContato carregar(Integer id) {
        List<CntContato> cntList = new ArrayList<CntContato>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CntContato c where c.cntId = :id");
            q.setParameter("id", id);
            cntList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (cntList != null && !cntList.isEmpty()) {
            return cntList.get(0);
        } else {
            return new CntContato();
        }
    }
}