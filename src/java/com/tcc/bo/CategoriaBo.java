/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.CatCategoria;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class CategoriaBo {

    /**
     * Insere um obj do tipo CatCategoria no banco de dados
     *
     * @param categoria
     * @return
     */
    public CatCategoria inserir(CatCategoria categoria) {
        toUpperCase(categoria);
        categoria.setCatAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.persist(categoria);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return categoria;
    }

    /**
     * Exclui um obj do tipo CatCategoria no banco de dados
     *
     * @param categoria
     * @return ToString do categoria excluido
     */
    public String excluir(CatCategoria categoria) {
        Session session = new HibernateUtil().openSession();
        String c = categoria.toString();
        try {
            session.getTransaction().begin();
            session.delete(categoria);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return c;
    }

    /**
     * Altera um obj do tipo CatCategoria no banco de dados
     *
     * @param categoria
     * @return
     */
    public CatCategoria alterar(CatCategoria categoria) {
        toUpperCase(categoria);
        categoria.setCatAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(categoria);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return categoria;
    }

    /**
     * Inativa um obj do tipo CatCategoria no banco de dados
     *
     * @param categoria
     * @return
     */
    public CatCategoria inativar(CatCategoria categoria) {
        categoria.setCatAtivo(false);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(categoria);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return categoria;
    }

    /**
     * Ativa um obj do tipo CatCategoria no banco de dados
     *
     * @param categoria
     * @return
     */
    public CatCategoria ativar(CatCategoria categoria) {
        categoria.setCatAtivo(true);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(categoria);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return categoria;
    }

    public void toUpperCase(CatCategoria categoria) {
        if (categoria.getCatDescricao() != null && !categoria.getCatDescricao().isEmpty()) {
            categoria.setCatDescricao(categoria.getCatDescricao().toUpperCase());
        }
    }

    /**
     * Carrega um obj do tipo CatCategoria no banco de dados por um ID
     *
     * @param categoria
     * @return
     */
    public CatCategoria carrgar(Integer catId) {
        Session session = new HibernateUtil().openSession();
        CatCategoria categoria = new CatCategoria();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CatCategoria c where c.catId = :catId");
            q.setParameter("catId", catId);
            List<CatCategoria> list = q.list();
            session.getTransaction().commit();
            if (list != null && !list.isEmpty()) {
                categoria = list.get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return categoria;
    }

    /**
     * Carrega um obj do tipo CatCategoria no banco de dados por sua descricao
     * (cat.catDescricao)
     *
     * @param categoria
     * @return
     */
    public List<CatCategoria> carrgar(String descricao) {
        List<CatCategoria> crgList = new ArrayList<CatCategoria>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CatCategoria c where c.catDescricao like :catDescricao");
            q.setParameter("catDescricao", "%" + descricao + "%");
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
     * Carrega todos os obj do tipo CatCategoria no banco de dados
     *
     * @param
     * @return
     */
    public List<CatCategoria> listarTodos() {
        List<CatCategoria> crgList = new ArrayList<CatCategoria>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CatCategoria c");
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
     * Carrega todos os obj do tipo CatCategoria ativos no banco de dados
     *
     * @param
     * @return
     */
    public List<CatCategoria> listarAtivos() {
        List<CatCategoria> crgList = new ArrayList<CatCategoria>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select c from CatCategoria c where c.catAtivo = true");
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
