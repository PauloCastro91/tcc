/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

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
@Table(name = "fun_funcionario")
public class FunFuncionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fun_id")
    private Integer funId;
    @Column(name = "fun_matricula")
    private String funMatricula;
    @Column(name = "fun_dataInicio")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date funDataInicio;
    @Column(name = "fun_dataFim")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date funDataFim;
    @Column(name = "fun_ativo")
    private boolean funAtivo;
    @JoinColumn(name = "pss_id", referencedColumnName = "pss_id")
    @ManyToOne(optional = false)
    private PssPessoa pss;
    @JoinColumn(name = "crg_id", referencedColumnName = "crg_id")
    @ManyToOne(optional = false)
    private CrgCargo crg;
    @Column(name = "fun_cpf")
    private String funCPF;
    @Column(name = "fun_rg")
    private String funRG;
    @Column(name = "fun_dataNasc")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date funDataNasc;

    public FunFuncionario() {
        this.funAtivo = true;
    }

    public FunFuncionario(Integer funId) {
        this.funId = funId;
        this.funAtivo = true;
    }

    public Integer getFunId() {
        return funId;
    }

    public void setFunId(Integer funId) {
        this.funId = funId;
    }

    public String getFunMatricula() {
        return funMatricula;
    }

    public void setFunMatricula(String funMatricula) {
        this.funMatricula = funMatricula;
    }

    public Date getFunDataInicio() {
        return funDataInicio;
    }

    public void setFunDataInicio(Date funDataInicio) {
        this.funDataInicio = funDataInicio;
    }

    public Date getFunDataFim() {
        return funDataFim;
    }

    public void setFunDataFim(Date funDataFim) {
        this.funDataFim = funDataFim;
    }

    public boolean isFunAtivo() {
        return funAtivo;
    }

    public void setFunAtivo(boolean funAtivo) {
        this.funAtivo = funAtivo;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    public CrgCargo getCrg() {
        return crg;
    }

    public void setCrg(CrgCargo crg) {
        this.crg = crg;
    }

    public String getFunCPF() {
        return funCPF;
    }

    public void setFunCPF(String funCPF) {
        this.funCPF = funCPF;
    }

    public String getFunRG() {
        return funRG;
    }

    public void setFunRG(String funRG) {
        this.funRG = funRG;
    }

    public Date getFunDataNasc() {
        return funDataNasc;
    }

    public void setFunDataNasc(Date funDataNasc) {
        this.funDataNasc = funDataNasc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.funId != null ? this.funId.hashCode() : 0);
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
        final FunFuncionario other = (FunFuncionario) obj;
        if (this.funId != other.funId && (this.funId == null || !this.funId.equals(other.funId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String pssId = "null";
        if (pss != null && pss.getPssId() != null) {
            pssId = String.valueOf(pss.getPssId());
        }
        String crgId = "null";
        if (crg != null && crg.getCrgId() != null) {
            crgId = String.valueOf(crg.getCrgId());
        }

        return "FunFuncionario{" + "funId=" + funId + ", pss=" + pssId + ", funMatricula=" + funMatricula + ", funDataInicio=" + funDataInicio + ", funDataFim=" + funDataFim + ", funAtivo=" + funAtivo + ", crg=" + crgId + ", rg=" + funRG + ", cpf=" + funCPF + ", fufDataNasc=" + funDataNasc + '}';
    }
}
