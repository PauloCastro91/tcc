/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.TpaTipoAcesso;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo
 */
public class TipoAcessoBo {

    /**
     * Lista todos os tipos de acesso do banco de dados
     *
     * @return
     */
    public List<TpaTipoAcesso> listarTodos() {
        List<TpaTipoAcesso> lista = new ArrayList<TpaTipoAcesso>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select t from TpaTipoAcesso t order by t.tpaDescricao");
            lista = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return lista;
    }
}
