/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.CrgCargo;
import com.tcc.model.FunFuncionario;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class CargoBo {

    /**
     * Insere um obj do tipo CrgCargo no banco de dados
     *
     * @param cargo
     * @return
     */
    public CrgCargo inserir(CrgCargo cargo) {
        toUpperCase(cargo);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.persist(cargo);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return cargo;
    }

    /**
     * Exclui um obj do tipo CrgCargo no banco de dados
     *
     * @param cargo
     * @return ToString do cargo excluido
     */
    public String excluir(CrgCargo cargo) {
        Session session = new HibernateUtil().openSession();
        String c = cargo.toString();
        try {
            session.getTransaction().begin();
            session.delete(cargo);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return c;
    }

    /**
     * Altera um obj do tipo CrgCargo no banco de dados
     *
     * @param cargo
     * @return
     */
    public CrgCargo alterar(CrgCargo cargo) {
        toUpperCase(cargo);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(cargo);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return cargo;
    }
    
    public void toUpperCase(CrgCargo cargo) {
        if (cargo.getCrgDescricao() != null && !cargo.getCrgDescricao().isEmpty()) {
            cargo.setCrgDescricao(cargo.getCrgDescricao().toUpperCase());
        }
    }

    /**
     * Carrega um obj do tipo CrgCargo no banco de dados por um ID
     *
     * @param cargo
     * @return
     */
    public CrgCargo carrgar(Integer crgId) {
        Session session = new HibernateUtil().openSession();
        CrgCargo cargo = new CrgCargo();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CrgCargo c where c.crgId = :crgId");
            q.setParameter("crgId", crgId);
            List<CrgCargo> list = q.list();
            session.getTransaction().commit();
            if (list != null && !list.isEmpty()) {
                cargo = list.get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return cargo;
    }

    /**
     * Carrega um obj do tipo CrgCargo no banco de dados por sua descricao
     * (crg.crgDescricao)
     *
     * @param cargo
     * @return
     */
    public List<CrgCargo> carrgar(String descricao) {
        List<CrgCargo> crgList = new ArrayList<CrgCargo>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CrgCargo c where c.crgDescricao like :crgDescricao");
            q.setParameter("crgDescricao", "%" + descricao + "%");
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
     * Carrega todos os obj do tipo CrgCargo no banco de dados
     *
     * @param
     * @return
     */
    public List<CrgCargo> listarTodos() {
        List<CrgCargo> crgList = new ArrayList<CrgCargo>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CrgCargo c");
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
     * Verifica se um cargo está sendo usado
     *
     * @param
     * @return true/false
     */
    public boolean estaSendoUsado(CrgCargo crg) {
        boolean usado = false;
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select f from FunFuncionario f where f.crg.crgId = :crgId");
            q.setParameter("crgId", crg.getCrgId());
            List<FunFuncionario> funList = q.list();
            if (funList != null && !funList.isEmpty()) {
                usado = true;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return usado;
    }
}
