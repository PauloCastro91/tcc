/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author paulo.castro
 */
@FacesConverter(value = "ProdutoConverter")
public class ProdutoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("Value" + value);
        return new Object();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("Obj" + value);
        return "";
    }
}
