/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author paulo.castro
 */
public class BaseMb {

    public boolean getHasErros() {
        Iterator<FacesMessage> it = FacesContext.getCurrentInstance().getMessages();
        while (it.hasNext()) {
            FacesMessage message = (FacesMessage) it.next();
            if (message.getSeverity().equals(FacesMessage.SEVERITY_ERROR) || message.getSeverity().equals(FacesMessage.SEVERITY_FATAL)) {
                return true;
            }
        }
        return false;
    }
}
