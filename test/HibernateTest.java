/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paulo.castro
 */
public class HibernateTest {
    
    @Test
    public void test1() {
        
        SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = factory.openSession();
        
        List list = session.createQuery("select p from PssPessoa p").list();
        
        session.close();
        factory.close();
        
        
    }
}