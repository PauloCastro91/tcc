/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.PssPessoa;
import com.tcc.model.UsrUsuario;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo
 */
public class UsuarioBo {

    /**
     * Lista um usuario (ativo) por seu pssId
     *
     * @author paulo.castro
     */
    public UsrUsuario carregarPorPessoa(PssPessoa pss) throws Exception {
        List<UsrUsuario> list = new ArrayList<UsrUsuario>();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select u from UsrUsuario u where u.pss.pssId = :pssId and u.usrAtivo = true");
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
            return new UsrUsuario();
        }
    }

    /**
     * Lista usuarios que começam com o login ... ex u.usrLogin like :login%
     *
     * @author paulo.castro
     */
    public List<UsrUsuario> carregarPorLogin(String login) throws Exception {
        List<UsrUsuario> list = new ArrayList<UsrUsuario>();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select u from UsrUsuario u where u.usrLogin like :login");
            q.setParameter("login", login.toUpperCase() + "%");
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

    /**
     * Insere um usuario
     *
     * @author paulo.castro
     */
    public UsrUsuario inserir(UsrUsuario usr) throws Exception {
        Session session = new HibernateUtil().openSession();
        try {
            usr.setUsrAtivo(Boolean.TRUE);
            usr.setUsrDataAlteracao(new Date());
            usr.setUsrDataCadastro(new Date());
            session.getTransaction().begin();
            session.saveOrUpdate(usr);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return usr;
    }

    /**
     * Altera um usuario
     *
     * @author paulo.castro
     */
    public UsrUsuario alterar(UsrUsuario usr) throws Exception {
        Session session = new HibernateUtil().openSession();
        try {
            usr.setUsrDataAlteracao(new Date());
            session.getTransaction().begin();
            session.saveOrUpdate(usr);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return usr;
    }

    /**
     * Carrega um usuario por seu login (u.usrLogin like :login)
     *
     * @author paulo.castro
     */
    public UsrUsuario pesquisarPorLogin(String login) throws Exception {
        UsrUsuario usr = new UsrUsuario();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select u from UsrUsuario u where u.usrLogin like :login");
            q.setParameter("login", login);
            if (q.list() != null && !q.list().isEmpty()) {
                usr = (UsrUsuario) q.list().get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return usr;
    }

    /**
     * Carrega um usuario por seu login (u.usrLogin like :login)
     *
     * @author paulo.castro
     */
    public UsrUsuario pesquisarPorLoginSenha(String login, String senha) throws Exception {
        UsrUsuario usr = new UsrUsuario();
        Session session = new HibernateUtil().openSession();
        try {
            Query q = session.createQuery("Select u from UsrUsuario u where u.usrLogin like :login and u.usrSenha like :senha");
            q.setParameter("login", login);
            q.setParameter("senha", senha);
            if (q.list() != null && !q.list().isEmpty()) {
                usr = (UsrUsuario) q.list().get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return usr;
    }

    /**
     * Bloqueia um usuario
     *
     * @author paulo.castro
     */
    public UsrUsuario bloquearUsuario(UsrUsuario usr) throws Exception {
        Session session = new HibernateUtil().openSession();
        try {
            usr.setUsrDataAlteracao(new Date());
            //  usr.setUsrSenhaBloqueada(Boolean.TRUE);
            session.save(usr);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        } finally {
            session.close();
        }
        return usr;
    }
}
