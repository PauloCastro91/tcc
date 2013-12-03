/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.CatCategoria;
import com.tcc.model.PdtProduto;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class ProdutoBo {

    public void toUpperCase(PdtProduto produto) {
        if (produto.getPdtDescricao() != null && !produto.getPdtDescricao().isEmpty()) {
            produto.setPdtDescricao(produto.getPdtDescricao().toUpperCase());
        }
        if (produto.getPdtNome() != null && !produto.getPdtNome().isEmpty()) {
            produto.setPdtNome(produto.getPdtNome().toUpperCase());
        }
    }

    /**
     * Insere um obj do tipo PdtProduto no banco de dados
     *
     * @param produto
     * @return
     */
    public PdtProduto inserir(PdtProduto produto) {
        toUpperCase(produto);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.persist(produto);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return produto;
    }

    /**
     * Exclui um obj do tipo PdtProduto no banco de dados
     *
     * @param produto
     * @return ToString do produto excluido
     */
    public String excluir(PdtProduto produto) {
        Session session = new HibernateUtil().openSession();
        String c = produto.toString();
        try {
            session.getTransaction().begin();
            session.delete(produto);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return c;
    }

    /**
     * Altera um obj do tipo PdtProduto no banco de dados
     *
     * @param produto
     * @return
     */
    public PdtProduto alterar(PdtProduto produto) {
        toUpperCase(produto);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(produto);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return produto;
    }

    /**
     * Carrega um obj do tipo PdtProduto no banco de dados por um ID
     *
     * @param produto
     * @return
     */
    public PdtProduto carrgar(Integer crgId) {
        Session session = new HibernateUtil().openSession();
        PdtProduto produto = new PdtProduto();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PdtProduto p where p.pdtId = :crgId");
            q.setParameter("crgId", crgId);
            List<PdtProduto> list = q.list();
            session.getTransaction().commit();
            if (list != null && !list.isEmpty()) {
                produto = list.get(0);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return produto;
    }

    /**
     * Carrega um obj do tipo PdtProduto no banco de dados por seu nome
     *
     * @param produto
     * @return
     */
    public List<PdtProduto> carrgarPorNome(String nome) {
        Session session = new HibernateUtil().openSession();
        List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PdtProduto p where p.pdtNome like :nome");
            q.setParameter("nome", "%" + nome + "%");
            pdtList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pdtList;
    }

    /**
     * Carrega um obj do tipo PdtProduto no banco de dados por seu nome
     *
     * @param produto
     * @return
     */
    public PdtProduto carrgarPorNomeCompleto(String nome) {
        Session session = new HibernateUtil().openSession();
        List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PdtProduto p where p.pdtNome like :nome");
            q.setParameter("nome", nome);
            pdtList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pdtList != null && !pdtList.isEmpty()) {
            return pdtList.get(0);
        } else {
            return new PdtProduto();
        }
    }

    /**
     * retorna todos os produtos ativos do banco
     *
     * @param nome
     * @return
     */
    public List<PdtProduto> listarTods() {
        Session session = new HibernateUtil().openSession();
        List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PdtProduto p where p.pdtAtivo = 1");
            pdtList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pdtList;
    }

    /**
     * retorna todos os produtos por categoria
     *
     * @param nome
     * @return
     */
    public List<PdtProduto> listarPorCategoria(CatCategoria cat) {
        Session session = new HibernateUtil().openSession();
        List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PdtProduto p where p.pdtAtivo is true and  p.cat.catId = " + cat.getCatId());
            pdtList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pdtList;
    }
}
