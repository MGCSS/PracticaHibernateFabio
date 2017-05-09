/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicahibernate;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Fabio
 */
public class HibernateUtil {
    
    private static final SessionFactory session_Factory;    
    static
    {
        try
        {
            session_Factory =new Configuration().configure().buildSessionFactory();
        }
        catch(HibernateException ex)
        {
           System.err.println(ex);
            
            throw (new ExceptionInInitializerError(ex));
        }
    }
    
    public static SessionFactory getSessionFactory()
    {
        return session_Factory;
    }
}
