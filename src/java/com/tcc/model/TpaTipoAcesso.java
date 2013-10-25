/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author paulo
 */
@Entity
@Table(name = "tpa_tipoAcesso")
public class TpaTipoAcesso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpa_id")
    private Integer tpaId;
    
    @Column(name = "tpa_descricao")
    private String tpaDescricao;

    public TpaTipoAcesso() {
    }

    public TpaTipoAcesso(Integer tpaId) {
        this.tpaId = tpaId;
    }

    public Integer getTpaId() {
        return tpaId;
    }

    public void setTpaId(Integer tpaId) {
        this.tpaId = tpaId;
    }

    public String getTpaDescricao() {
        return tpaDescricao;
    }

    public void setTpaDescricao(String tpaDescricao) {
        this.tpaDescricao = tpaDescricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.tpaId != null ? this.tpaId.hashCode() : 0);
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
        final TpaTipoAcesso other = (TpaTipoAcesso) obj;
        if (this.tpaId != other.tpaId && (this.tpaId == null || !this.tpaId.equals(other.tpaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TpaTipoAcesso{" + "tpaId=" + tpaId + ", tpaDescricao=" + tpaDescricao + '}';
    }
}
