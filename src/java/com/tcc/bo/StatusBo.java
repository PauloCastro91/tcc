/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.SttStatus;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class StatusBo {

    /**
     * Insere um obj do tipo SttStatus no banco de dados
     *
     * @param status
     * @return
     */
    public SttStatus inserir(SttStatus status) {
        toUpperCase(status);
        status.setSttAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.persist(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return status;
    }

    /**
     * Exclui um obj do tipo SttStatus no banco de dados
     *
     * @param status
     * @return ToString do status excluido
     */
    public String excluir(SttStatus status) {
        Session session = new HibernateUtil().openSession();
        String c = status.toString();
        try {
            session.getTransaction().begin();
            session.delete(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return c;
    }

    /**
     * Altera um obj do tipo SttStatus no banco de dados
     *
     * @param status
     * @return
     */
    public SttStatus alterar(SttStatus status) {
        toUpperCase(status);
        status.setSttAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return status;
    }

    /**
     * Inativa um obj do tipo SttStatus no banco de dados
     *
     * @param status
     * @return
     */
    public SttStatus inativar(SttStatus status) {
        status.setSttAtivo(false);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return status;
    }

    /**
     * Ativa um obj do tipo SttStatus no banco de dados
     *
     * @param status
     * @return
     */
    public SttStatus ativar(SttStatus status) {
        status.setSttAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(status);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return status;
    }

    public void toUpperCase(SttStatus status) {
        if (status.getSttDescricao() != null && !status.getSttDescricao().isEmpty()) {
            status.setSttDescricao(status.getSttDescricao().toUpperCase());
        }
    }

    /**
     * Carrega um obj do tipo SttStatus no banco de dados por um ID
     *
     * @param status
     * @return
     */
    public SttStatus carrgar(Integer sttId) {
        Session session = new HibernateUtil().openSession();
        SttStatus status = new SttStatus();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from SttStatus c where c.sttId = :sttId");
            q.setParameter("sttId", sttId);
            List<SttStatus> list = q.list();
            session.getTransaction().commit();
            if (list != null && !list.isEmpty()) {
                status = list.get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return status;
    }

    /**
     * Carrega um obj do tipo SttStatus no banco de dados por sua descricao
     * (stt.sttDescricao)
     *
     * @param status
     * @return
     */
    public List<SttStatus> carrgar(String descricao) {
        List<SttStatus> crgList = new ArrayList<SttStatus>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from SttStatus c where c.sttDescricao like :sttDescricao");
            q.setParameter("sttDescricao", "%" + descricao + "%");
            crgList = q.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return crgList;
    }

    /**
     * Carrega todos os obj do tipo SttStatus no banco de dados
     *
     * @param
     * @return
     */
    public List<SttStatus> listarTodos() {
        List<SttStatus> crgList = new ArrayList<SttStatus>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from SttStatus c");
            crgList = q.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return crgList;
    }

    /**
     * Carrega todos os obj do tipo SttStatus ativos no banco de dados
     *
     * @param
     * @return
     */
    public List<SttStatus> listarAtivos() {
        List<SttStatus> crgList = new ArrayList<SttStatus>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from SttStatus c where c.sttAtivo = true");
            crgList = q.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return crgList;
    }
}
