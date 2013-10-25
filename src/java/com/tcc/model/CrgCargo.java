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
@Table(name = "crg_cargo")
public class CrgCargo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crg_id")
    private Integer crgId;
    @Column(name = "crg_descricao")
    private String crgDescricao;

    public CrgCargo() {
    }

    public CrgCargo(Integer crgId) {
        this.crgId = crgId;
    }

    public Integer getCrgId() {
        return crgId;
    }

    public void setCrgId(Integer crgId) {
        this.crgId = crgId;
    }

    public String getCrgDescricao() {
        return crgDescricao;
    }

    public void setCrgDescricao(String crgDescricao) {
        this.crgDescricao = crgDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.crgId != null ? this.crgId.hashCode() : 0);
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
        final CrgCargo other = (CrgCargo) obj;
        if (this.crgId != other.crgId && (this.crgId == null || !this.crgId.equals(other.crgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CrgCargo{" + "crgId=" + crgId + ", crgDescricao=" + crgDescricao + '}';
    }
}
