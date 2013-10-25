/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

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
public class PessoaBo {

    /**
     * Insere um obj do tipo PssPessoa no banco de dados
     *
     * @param pessoa
     * @return
     */
    public PssPessoa inserir(PssPessoa pessoa) {
        toUpperCase(pessoa);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.save(pessoa);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pessoa;
    }

    /**
     * Altera um obj do tipo PssPessoa no banco de dados
     *
     * @param pessoa
     * @return
     */
    public PssPessoa alterar(PssPessoa pessoa) {
        toUpperCase(pessoa);
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(pessoa);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pessoa;
    }

    public void toUpperCase(PssPessoa pessoa) {
        if (pessoa.getPssNome() != null && !pessoa.getPssNome().isEmpty()) {
            pessoa.setPssNome(pessoa.getPssNome().toUpperCase());
        }
        if (pessoa.getPssSobrenome() != null && !pessoa.getPssSobrenome().isEmpty()) {
            pessoa.setPssSobrenome(pessoa.getPssSobrenome().toUpperCase());
        }
    }

    /**
     * Pesquisa ojb do tipo PssPessoa atraves do atributo pssNome
     *
     * @param nome
     * @return
     */
    public List<PssPessoa> pesquisarPorNome(String nome) {
        List<PssPessoa> pssList = new ArrayList<PssPessoa>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PssPessoa p where p.pssNome like :nome");
            q.setParameter("nome", "%" + nome + "%");
            pssList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pssList != null && !pssList.isEmpty()) {
            return pssList;
        } else {
            return new ArrayList<PssPessoa>();
        }
    }

    /**
     * Pesquisa ojb do tipo PssPessoa atraves dos atributos pssNome e
     * pssSobreNome
     *
     * @param nome
     * @return
     */
    public List<PssPessoa> pesquisarPorNomeSobreNome(String nomeSobreNome) {
        List<PssPessoa> pssList = new ArrayList<PssPessoa>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PssPessoa p where CONCAT(p.pssNome,' ', p.pssSobrenome) like :nome");
            q.setParameter("nome", "%" + nomeSobreNome + "%");
            pssList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pssList != null && !pssList.isEmpty()) {
            return pssList;
        } else {
            return new ArrayList<PssPessoa>();
        }
    }

    /**
     * Pesquisa ojb do tipo PssPessoa atraves do atributo pssNome e pssSobrenome
     *
     * @param nome
     * @param sobrenome
     * @return
     */
    public List<PssPessoa> pesquisarPorNomeSobrenome(String nome, String sobrenome) {
        List<PssPessoa> pssList = new ArrayList<PssPessoa>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PssPessoa p where p.pssNome like :nome "
                    + " and p.pssSobrenome like :sobrenome order by p.pssNome,p.pssSobrenome");
            q.setParameter("nome", "%" + nome + "%");
            q.setParameter("sobrenome", "%" + sobrenome + "%");
            pssList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pssList != null && !pssList.isEmpty()) {
            return pssList;
        } else {
            return new ArrayList<PssPessoa>();
        }
    }

    /**
     * Pesquisa ojb do tipo PssPessoa atraves do atributo pssNome e pssSobrenome
     * (EXATAMENTE IGUAL)
     *
     * @param nome
     * @param sobrenome
     * @return
     */
    public List<PssPessoa> carregaPorNomeSobrenome(String nome, String sobrenome) {
        List<PssPessoa> pssList = new ArrayList<PssPessoa>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PssPessoa p where p.pssNome like :nome "
                    + " and p.pssSobrenome like :sobrenome order by p.pssNome,p.pssSobrenome");
            q.setParameter("nome", nome);
            q.setParameter("sobrenome", sobrenome);
            pssList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pssList != null && !pssList.isEmpty()) {
            return pssList;
        } else {
            return new ArrayList<PssPessoa>();
        }
    }

    /**
     * Carrega uma pessoa do banco de dados atraves de seu Id.
     *
     * @param id
     * @return
     */
    public PssPessoa carregar(Integer id) {
        List<PssPessoa> pssList = new ArrayList<PssPessoa>();
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("Select p from PssPessoa p where p.pssId = :id");
            q.setParameter("id", id);
            pssList = q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (pssList != null && !pssList.isEmpty()) {
            return pssList.get(0);
        } else {
            return new PssPessoa();
        }
    }
}
