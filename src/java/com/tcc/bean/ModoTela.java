/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

/**
 *
 * @author paulo.castro
 */
public class ModoTela {
    
   public static final Integer VISUALIZACAO = 1;
   public static final Integer INSERCAO = 2;
   public static final Integer ALTERACAO = 3;
   private Integer modoTela;

    public ModoTela() {
        modoTela = VISUALIZACAO;
    }

    public Integer getModoTela() {
        return modoTela;
    }

    public void setModoTela(Integer modoTela) {
        this.modoTela = modoTela;
    }
}
