/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.UsuarioBo;
import com.tcc.model.UsrUsuario;
import com.tcc.util.Encripta;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author paulo
 */
@ManagedBean
@ViewScoped
public class LoginMb extends BaseMb implements Serializable {

    private String login;
    private String senha;
    private UsuarioBo usuarioBo = new UsuarioBo();
    private int tentativas = 0;
    private UsrUsuario usr;

    public LoginMb() {
    }

    public void validarLogin() throws Exception {
        usr = new UsrUsuario();
        if (login == null || login.trim().isEmpty()) {
            throw new Exception("Favor informar um login!!");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new Exception("Favor informar uma senha!!");
        }
        usr = usuarioBo.pesquisarPorLogin(login);
        if (usr == null || usr.getUsrId() == null) {
            throw new Exception("Usuário Incorreto");
        }
        if (usr.isUsrSenhaBloqueada()) {
            throw new Exception("Senha Bloqueada, favor entrar em contato com o Administrador");
        }
        if (!usr.getUsrSenha().equals(Encripta.criptografaSenha(senha))) {
            tentativas++;
            throw new Exception("Senha Incorreta");
        }
        if (tentativas >= 3) {
            throw new Exception("Senha Bloqueada, favor entrar em contato com o Administrador");
        }
    }

    public String logar() {
        try {
            validarLogin();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("currentUser", usr);
            return "index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
        return "";
    }

    public String resetarSenha() {
        try {
            return "esqueciSenha.xhtml?faces-redirect=true";
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
        return "";
    }

    public String deslogar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();
        return "login.xhtml?faces-redirect=true";
    }

    public String resetPassword() {
        try {
            usr = new UsrUsuario();
            usr = usuarioBo.pesquisarPorLogin(login);
            if (usr == null || usr.getUsrId() == null) {
                throw new Exception("Usuário Incorreto");
            } else {
                usr.setUsrSenha(Encripta.criptografaSenha("12345678910"));
                usr.setUsrSenhaBloqueada(Boolean.FALSE);
                usuarioBo.alterar(usr);
            }
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
        return "";
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public UsrUsuario getUsr() {
        if (usr == null || usr.getUsrId() == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            usr = (UsrUsuario) session.getAttribute("currentUser");
            if (usr == null || usr.getUsrId() == null) {
                usr = new UsrUsuario();
            }
        }
        return usr;
    }

    public void setUsr(UsrUsuario usr) {
        this.usr = usr;
    }
}
