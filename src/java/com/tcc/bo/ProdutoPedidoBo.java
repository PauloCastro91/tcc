/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bo;

import com.tcc.model.PedPedido;
import com.tcc.model.PpdProdutoPedido;
import com.tcc.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author paulo.castro
 */
public class ProdutoPedidoBo {
    /**
     * Lista todos os ProdutosPedido de um Pedido
     * @param ped
     * @return 
     */
    public List<PpdProdutoPedido> listarPorPedido(PedPedido ped) {
        Session session = new HibernateUtil().openSession();
        List<PpdProdutoPedido> lista = new ArrayList<PpdProdutoPedido>();
        try {
            Query q = session.createQuery("Select p from PpdProdutoPedido p where p.ped.pedId = :id");
            q.setParameter("id", ped.getPedId());
            lista = q.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        if (lista != null && !lista.isEmpty()) {
            return lista;
        } else {
            return new ArrayList<PpdProdutoPedido>();
        }
    }
}
