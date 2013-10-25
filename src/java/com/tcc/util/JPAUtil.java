/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author paulo.castro
 */
public class JPAUtil {

    public static final String INSERIDO_COM_SUCESSO = "Registro inserido com sucesso!!";
    public static final String ALTERADO_COM_SUCESSO = "Registro alterado com sucesso!!";
    public static final String EXCLUIDO_COM_SUCESSO = "Registro excluído com sucesso!!";
    public static final String PESQUISA_SEM_RESULTADOS = "A pesquisa retornou vazia!!";

    public static void addMensagemErro(String mensagem) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL,mensagem,"");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void addMensagemSucesso(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensagem));
    }
}
