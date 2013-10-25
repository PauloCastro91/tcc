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

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "lgr_logradouro")
public class LgrLogradouro implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lgr_id")
    private Integer lgrId;
    
    @Column(name = "lgr_descricao")
    private String lgrDescricao;
    
    @Column(name= "lgr_cep")
    private String lgrCep;
    
    @Column(name= "lgr_cidade")
    private String lgrCidade;
    
    @Column(name= "lgr_estado")
    private String lgrEstado;

    public LgrLogradouro() {
    }

    public LgrLogradouro(Integer lgrId) {
        this.lgrId = lgrId;
    }

    public Integer getLgrId() {
        return lgrId;
    }

    public void setLgrId(Integer lgrId) {
        this.lgrId = lgrId;
    }

    public String getLgrDescricao() {
        return lgrDescricao;
    }

    public void setLgrDescricao(String lgrDescricao) {
        this.lgrDescricao = lgrDescricao;
    }

    public String getLgrCep() {
        return lgrCep;
    }

    public void setLgrCep(String lgrCep) {
        this.lgrCep = lgrCep;
    }

    public String getLgrCidade() {
        return lgrCidade;
    }

    public void setLgrCidade(String lgrCidade) {
        this.lgrCidade = lgrCidade;
    }

    public String getLgrEstado() {
        return lgrEstado;
    }

    public void setLgrEstado(String lgrEstado) {
        this.lgrEstado = lgrEstado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.lgrId != null ? this.lgrId.hashCode() : 0);
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
        final LgrLogradouro other = (LgrLogradouro) obj;
        if (this.lgrId != other.lgrId && (this.lgrId == null || !this.lgrId.equals(other.lgrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LgrLogradouro{" + "lgrId=" + lgrId + ", lgrDescricao=" + lgrDescricao + ", lgrCep=" + lgrCep + ", lgrCidade=" + lgrCidade + ", lgrEstado=" + lgrEstado + '}';
    }
}
