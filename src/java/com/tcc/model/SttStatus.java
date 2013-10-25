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
@Table(name = "stt_status")
public class SttStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt_id")
    private Integer sttId;
    @Column(name = "stt_descricao")
    private String sttDescricao;
    @Column(name = "stt_ativo")
    private boolean sttAtivo;

    public SttStatus() {
    }

    public SttStatus(Integer sttId) {
        this.sttId = sttId;
    }

    public Integer getSttId() {
        return sttId;
    }

    public void setSttId(Integer sttId) {
        this.sttId = sttId;
    }

    public String getSttDescricao() {
        return sttDescricao;
    }

    public void setSttDescricao(String sttDescricao) {
        this.sttDescricao = sttDescricao;
    }

    public boolean getSttAtivo() {
        return sttAtivo;
    }

    public void setSttAtivo(boolean sttAtivo) {
        this.sttAtivo = sttAtivo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.sttId != null ? this.sttId.hashCode() : 0);
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
        final SttStatus other = (SttStatus) obj;
        if (this.sttId != other.sttId && (this.sttId == null || !this.sttId.equals(other.sttId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SttStatus{" + "sttId=" + sttId + ", sttDescricao=" + sttDescricao + ",sttAtivo=" + sttAtivo + '}';
    }
}
