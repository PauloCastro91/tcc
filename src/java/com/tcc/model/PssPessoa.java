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
 * @author paulo.castro
 */
@Entity
@Table(name = "pss_pessoa")
public class PssPessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pss_id")
    private Integer pssId;
    
    @Column(name = "pss_nome")
    private String pssNome;
    
    @Column(name = "pss_sobrenome")
    private String pssSobrenome;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pss")
    private Collection<CntContato> contatos;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pss")
    private Collection<EndEndereco> enderecos;
    
    @Transient
    private String contatosString;
    
    @Transient
    private String enderecosString;
    public PssPessoa() {
    }

    public PssPessoa(Integer pssId) {
        this.pssId = pssId;
    }

    public Integer getPssId() {
        return pssId;
    }

    public void setPssId(Integer pssId) {
        this.pssId = pssId;
    }

    public String getPssNome() {
        return pssNome;
    }

    public void setPssNome(String pssNome) {
        this.pssNome = pssNome;
    }

    public String getPssSobrenome() {
        return pssSobrenome;
    }

    public void setPssSobrenome(String pssSobrenome) {
        this.pssSobrenome = pssSobrenome;
    }

    public Collection<CntContato> getContatos() {
        return contatos;
    }

    public void setContatos(Collection<CntContato> contatos) {
        this.contatos = contatos;
    }

    public Collection<EndEndereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Collection<EndEndereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.pssId != null ? this.pssId.hashCode() : 0);
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
        final PssPessoa other = (PssPessoa) obj;
        if (this.pssId != other.pssId && (this.pssId == null || !this.pssId.equals(other.pssId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PssPessoa{" + "pssId=" + pssId + ", pssNome=" + pssNome + ", pssSobrenome=" + pssSobrenome + '}';
    }

    public String getContatosString() {
        return contatosString;
    }

    public void setContatosString(String contatosString) {
        this.contatosString = contatosString;
    }

    public String getEnderecosString() {
        return enderecosString;
    }

    public void setEnderecosString(String enderecosString) {
        this.enderecosString = enderecosString;
    }
}
