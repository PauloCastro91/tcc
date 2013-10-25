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
@Table(name = "end_endereco")
public class EndEndereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private Integer endId;
    @Column(name = "end_numero")
    private String endNumero;
    @Column(name = "end_complemento")
    private String endComplemento;
    @Column(name = "end_referencia")
    private String endReferencia;
    @JoinColumn(name = "lgr_id", referencedColumnName = "lgr_id")
    @ManyToOne(optional = false)
    private LgrLogradouro lgr;
    @JoinColumn(name = "pss_id", referencedColumnName = "pss_id")
    @ManyToOne(optional = false)
    private PssPessoa pss;
    @Transient
    private boolean selecionado;

    public EndEndereco() {
        lgr = new LgrLogradouro();
    }

    public EndEndereco(Integer endId) {
        this.endId = endId;
    }

    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    public String getEndNumero() {
        return endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndComplemento() {
        return endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndReferencia() {
        return endReferencia;
    }

    public void setEndReferencia(String endReferencia) {
        this.endReferencia = endReferencia;
    }

    public LgrLogradouro getLgr() {
        return lgr;
    }

    public void setLgr(LgrLogradouro lgr) {
        this.lgr = lgr;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.endId != null ? this.endId.hashCode() : 0);
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
        final EndEndereco other = (EndEndereco) obj;
        if (this.endId != other.endId && (this.endId == null || !this.endId.equals(other.endId))) {
            return false;
        }
        return true;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    @Override
    public String toString() {
        String pssId = "null";
        if (pss != null && pss.getPssId() != null) {
            pssId = String.valueOf(pss.getPssId());
        }
        return "EndEndereco{" + "endId=" + endId + ", endNumero=" + endNumero + ", endComplemento=" + endComplemento + ", endReferencia=" + endReferencia + ", lgr=" + lgr + ", pss=" + pssId + '}';
    }
}
