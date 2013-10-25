/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.EndEndereco;
import com.tcc.model.PedPedido;
import com.tcc.model.PpdProdutoPedido;
import com.tcc.model.SttStatus;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class PedidoBo {

    /**
     * Insere um obj do tipo PedPedido no banco de dados
     *
     * @param pedido
     * @return
     */
    public PedPedido inserir(PedPedido pedido) {
        pedido.setPedDataCadastro(new Date());
        pedido.setPedDataAlteracao(new Date());
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.save(pedido);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pedido;
    }

    /**
     * Altera um obj do tipo PedPedido no banco de dados (status)
     *
     * @param pedido
     * @return
     */
    public PedPedido alterar(PedPedido pedido) {
        pedido.setPedDataAlteracao(new Date());
        Session session = new HibernateUtil().openSession();
        try {
            session.getTransaction().begin();
            session.merge(pedido);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return pedido;
    }

    public void salvarProdutoPedidoEmLote(List<PpdProdutoPedido> ppdList) {
        int x = 0;
        Session session = new HibernateUtil().openSession();
        try {
            for (PpdProdutoPedido ppd : ppdList) {
                session.save(ppd);
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

    public List<PedPedido> listarPorStatus(SttStatus stt) {
        Session session = new HibernateUtil().openSession();
        List<PedPedido> lista = new ArrayList<PedPedido>();
        try {
            Query q = session.createQuery("Select p from PedPedido p where p.stt.sttId = :id");
            q.setParameter("id", stt.getSttId());
            lista = q.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lista != null && !lista.isEmpty()) {
            return lista;
        } else {
            return new ArrayList<PedPedido>();
        }
    }

    public List<PedPedido> listarPorStatusDataHora(SttStatus stt, Date inicio, Date fim) {
        Session session = new HibernateUtil().openSession();
        List<PedPedido> lista = new ArrayList<PedPedido>();
        try {
            Query q = session.createQuery("Select p from PedPedido p where p.stt.sttId = :id and p.pedDataCadastro between :inicio and :fim");
            q.setParameter("id", stt.getSttId());
            q.setParameter("inicio", inicio);
            q.setParameter("fim", fim);
            lista = q.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lista != null && !lista.isEmpty()) {
            return lista;
        } else {
            return new ArrayList<PedPedido>();
        }
    }
}
