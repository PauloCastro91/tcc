/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.filter;

/**
 *
 * @author paulo
 */
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();
        String paginaAtual = facesContext.getViewRoot().getViewId();
        List<String> pagPermitidas = new ArrayList<String>();
        pagPermitidas.add("login.xhtml");
        pagPermitidas.add("esqueciSenha.xhtml");
        pagPermitidas.add("mtdo.xhtml");
        pagPermitidas.add("cssLayoutCliente.css");
        boolean permitidoAcesso = false;
        for (String telaPermitida : pagPermitidas) {
            if (paginaAtual.endsWith(telaPermitida)) {
                permitidoAcesso = true;
                break;
            }
        }
         if (!permitidoAcesso) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            Object currentUser = session.getAttribute("currentUser");
            if (currentUser == null) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
