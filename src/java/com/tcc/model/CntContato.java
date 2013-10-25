/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

/**
 *
 * @author paulo.castro
 */
import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "cnt_contato")
public class CntContato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnt_id")
    private Integer cntId;
    @Column(name = "cnt_descricao")
    private String cntDescricao;
    @JoinColumn(name = "pss_id", referencedColumnName = "pss_id")
    @ManyToOne(optional = false)
    private PssPessoa pss;

    public CntContato() {
    }

    public CntContato(Integer cntId) {
        this.cntId = cntId;
    }

    public Integer getCntId() {
        return cntId;
    }

    public void setCntId(Integer cntId) {
        this.cntId = cntId;
    }

    public String getCntDescricao() {
        return cntDescricao;
    }

    public void setCntDescricao(String cntDescricao) {
        this.cntDescricao = cntDescricao;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.cntId != null ? this.cntId.hashCode() : 0);
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
        final CntContato other = (CntContato) obj;
        if (this.cntId != other.cntId && (this.cntId == null || !this.cntId.equals(other.cntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CntContato{" + "cntId=" + cntId + ", cntDescricao=" + cntDescricao + ", pss=" + pss + '}';
    }
}
