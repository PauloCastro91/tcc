/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.filter;

/**
 *
 * @author paulo
 */
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
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = ((currentPage.lastIndexOf("login.xhtml")) > -1);
        boolean forgetPasswordPage = ((currentPage.lastIndexOf("esqueciSenha.xhtml")) > -1);
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object currentUser = session.getAttribute("currentUser");

        if (!isLoginPage && !forgetPasswordPage && currentUser == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
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
