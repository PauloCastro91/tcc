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
import javax.persistence.Transient;

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "pdt_produto")
public class PdtProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdt_id")
    private Integer pdtId;
    @Column(name = "pdt_descricao")
    private String pdtDescricao;
    @Column(name = "pdt_nome")
    private String pdtNome;
    @Column(name = "pdt_valor")
    private Double pdtValor;
    @Column(name = "pdt_ativo")
    private boolean pdtAtivo;
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
    @ManyToOne(optional = false)
    private CatCategoria cat;
    @Transient
    private int quantidade;

    public PdtProduto() {
    }

    public Integer getPdtId() {
        return pdtId;
    }

    public void setPdtId(Integer pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtDescricao() {
        return pdtDescricao;
    }

    public void setPdtDescricao(String pdtDescricao) {
        this.pdtDescricao = pdtDescricao;
    }

    public String getPdtNome() {
        return pdtNome;
    }

    public void setPdtNome(String pdtNome) {
        this.pdtNome = pdtNome;
    }

    public Double getPdtValor() {
        return pdtValor;
    }

    public void setPdtValor(Double pdtValor) {
        this.pdtValor = pdtValor;
    }

    public boolean isPdtAtivo() {
        return pdtAtivo;
    }

    public void setPdtAtivo(boolean pdtAtivo) {
        this.pdtAtivo = pdtAtivo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.pdtId != null ? this.pdtId.hashCode() : 0);
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
        final PdtProduto other = (PdtProduto) obj;
        if (this.pdtId != other.pdtId && (this.pdtId == null || !this.pdtId.equals(other.pdtId))) {
            return false;
        }
        return true;
    }

    public Boolean getPdtAtivo() {
        return pdtAtivo;
    }

    public void setPdtAtivo(Boolean pdtAtivo) {
        this.pdtAtivo = pdtAtivo;
    }

    public CatCategoria getCat() {
        return cat;
    }

    public void setCat(CatCategoria cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return pdtNome;
    }

    public String toStringCompleto() {
        String catId = "";
        if (cat != null && cat.getCatId() != null) {
            catId = String.valueOf(cat.getCatId());
        }
        return "PdtProduto{" + "pdtId=" + pdtId + ", pdtDescricao=" + pdtDescricao + ", pdtNome=" + pdtNome + ", cat=" + catId + ", pdtValor=" + pdtValor + ", pdtAtivo=" + pdtAtivo + '}';
    }
}
