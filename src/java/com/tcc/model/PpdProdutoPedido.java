/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "ppd_produtoPedido")
public class PpdProdutoPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppd_id")
    private Integer ppdId;
    @JoinColumn(name = "ped_id", referencedColumnName = "ped_id")
    @ManyToOne(optional = false)
    private PedPedido ped;
    @JoinColumn(name = "pdt_id", referencedColumnName = "pdt_id")
    @ManyToOne(optional = false)
    private PdtProduto pdt;
    @Column(name = "ppd_quantidade")
    private Integer ppdQuantidade;

    public PpdProdutoPedido() {
    }

    public PpdProdutoPedido(Integer ppdId) {
        this.ppdId = ppdId;
    }

    public PpdProdutoPedido(Integer ppdId, PedPedido ped, PdtProduto pdt, Integer ppdQuantidade) {
        this.ppdId = ppdId;
        this.ped = ped;
        this.pdt = pdt;
        this.ppdQuantidade = ppdQuantidade;
    }

    public Integer getPpdId() {
        return ppdId;
    }

    public void setPpdId(Integer ppdId) {
        this.ppdId = ppdId;
    }

    public PedPedido getPed() {
        return ped;
    }

    public void setPed(PedPedido ped) {
        this.ped = ped;
    }

    public PdtProduto getPdt() {
        return pdt;
    }

    public void setPdt(PdtProduto pdt) {
        this.pdt = pdt;
    }

    public Integer getPpdQuantidade() {
        return ppdQuantidade;
    }

    public void setPpdQuantidade(Integer ppdQuantidade) {
        this.ppdQuantidade = ppdQuantidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.ppdId != null ? this.ppdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PpdProdutoPedido other = (PpdProdutoPedido) obj;
        if (this.ppdId != other.ppdId && (this.ppdId == null || !this.ppdId.equals(other.ppdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String pdtString = "";
        if (pdt != null && pdt.getPdtId() != null) {
            pdtString = String.valueOf(pdt.getPdtId());
        }
        String pedString = "";
        if (ped != null && ped.getPedId() != null) {
            pedString = String.valueOf(ped.getPedId());
        }

        return "ProdutoxPedido{" + "ppdId=" + ppdId + ", ped=" + pedString + ", pdt=" + pdtString + ", ppdQuantidade=" + ppdQuantidade + '}';
    }
}
