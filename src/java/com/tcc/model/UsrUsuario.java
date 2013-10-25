/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.TemporalType;

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "usr_usuario")
public class UsrUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer usrId;
    @JoinColumn(name = "pss_id", referencedColumnName = "pss_id")
    @ManyToOne(optional = false)
    private PssPessoa pss;
    @Column(name = "usr_login")
    private String usrLogin;
    @Column(name = "usr_senha")
    private String usrSenha;
    @Column(name = "usr_dataCadastro")
    @Temporal(TemporalType.DATE)
    private Date usrDataCadastro;
    @Column(name = "usr_dataAlteracao")
    @Temporal(TemporalType.DATE)
    private Date usrDataAlteracao;
    @ManyToOne(optional = false)
    @JoinColumn(name = "tpa_id", referencedColumnName = "tpa_id")
    private TpaTipoAcesso tpa;
    @Column(name = "usr_senhaBloqueada")
    private boolean usrSenhaBloqueada;
    @Column(name = "usr_email")
    private String usrEmail;
    @Column(name = "usr_ativo")
    private boolean usrAtivo;

    public UsrUsuario() {
    }

    public UsrUsuario(Integer usrId) {
        this.usrId = usrId;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    public String getUsrLogin() {
        return usrLogin;
    }

    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }

    public String getUsrSenha() {
        return usrSenha;
    }

    public void setUsrSenha(String usrSenha) {
        this.usrSenha = usrSenha;
    }

    public Date getUsrDataCadastro() {
        return usrDataCadastro;
    }

    public void setUsrDataCadastro(Date usrDataCadastro) {
        this.usrDataCadastro = usrDataCadastro;
    }

    public Date getUsrDataAlteracao() {
        return usrDataAlteracao;
    }

    public void setUsrDataAlteracao(Date usrDataAlteracao) {
        this.usrDataAlteracao = usrDataAlteracao;
    }

    public boolean isUsrSenhaBloqueada() {
        return usrSenhaBloqueada;
    }

    public void setUsrSenhaBloqueada(boolean usrSenhaBloqueada) {
        this.usrSenhaBloqueada = usrSenhaBloqueada;
    }

    public boolean isUsrAtivo() {
        return usrAtivo;
    }

    public void setUsrAtivo(boolean usrAtivo) {
        this.usrAtivo = usrAtivo;
    }
    public TpaTipoAcesso getTpa() {
        return tpa;
    }

    public void setTpa(TpaTipoAcesso tpa) {
        this.tpa = tpa;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.usrId != null ? this.usrId.hashCode() : 0);
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
        final UsrUsuario other = (UsrUsuario) obj;
        if (this.usrId != other.usrId && (this.usrId == null || !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String pssId = "";
        if (pss != null && pss.getPssId() != null) {
            pssId = String.valueOf(pss.getPssId());
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String cadastro = "";
        if (usrDataCadastro != null) {
            cadastro = df.format(usrDataCadastro);
        }
        String alteracao = "";
        if (usrDataAlteracao != null) {
            alteracao = df.format(usrDataAlteracao);
        }
        String ativo = "true";
        if (!usrAtivo) {
            ativo = "false";
        }
        String senhaBloc = "false";
        if (usrSenhaBloqueada) {
            senhaBloc = "true";
        }
        String tpaId = "";
        if (tpa != null && tpa.getTpaId() != null) {
            tpaId = String.valueOf(tpa.getTpaId());
        }
        return "UsrUsuario{" + "usrId=" + usrId + ", pss=" + pssId + ", usrLogin=" + usrLogin + ", tpa=" + tpaId + ",usrSenhaBloqueada=" + senhaBloc + ",usrAtivo=" + ativo + ", usrDataCadastro=" + cadastro + ",email=" + usrEmail + ", usrDataAlteracao=" + alteracao + '}';
    }

    public String getUsrDataCadastroString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (usrDataCadastro != null) {
            return df.format(usrDataCadastro);
        }
        return "";
    }

    public String getUsrDataAlteracaoString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (usrDataAlteracao != null) {
            return df.format(usrDataAlteracao);
        }
        return "";
    }
}
