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
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "cat_categoria")
public class CatCategoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer catId;
    
    @Column(name = "cat_descricao")
    private String catDescricao;
    
    @Column(name = "cat_ativo")
    private boolean catAtivo;

    public CatCategoria() {
    }

    public CatCategoria(Integer catId) {
        this.catId = catId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatDescricao() {
        return catDescricao;
    }

    public void setCatDescricao(String catDescricao) {
        this.catDescricao = catDescricao;
    }

    public boolean getCatAtivo() {
        return catAtivo;
    }

    public void setCatAtivo(boolean catAtivo) {
        this.catAtivo = catAtivo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.catId != null ? this.catId.hashCode() : 0);
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
        final CatCategoria other = (CatCategoria) obj;
        if (this.catId != other.catId && (this.catId == null || !this.catId.equals(other.catId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CatCategoria{" + "catId=" + catId + ", catDescricao=" + catDescricao + ",catAtivo=" + catAtivo + '}';
    }
}
