/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author paulo.castro
 */
public class HibernateUtil {
    
    private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
    
    public Session openSession() {
        return factory.openSession();
    }
}
