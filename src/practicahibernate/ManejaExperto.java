package practicahibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import java.util.List;
import modelo.*; 

/**
 *
 * @author Fabio
 */
public class ManejaExperto {

    private Session sesion;
    private Transaction tx;

    public ManejaExperto() {
    }

    public void iniciaOperacion() throws HibernateException {
        System.out.println("Comenzando con Hibernate");
        sesion = HibernateUtil.getSessionFactory().openSession(); //iniciamos una sesion hibernate
        tx = sesion.beginTransaction(); // comienza la transaccion
    }

    public void finalizaOperacion() throws HibernateException {
        System.out.println("Finalizando con Hibernate");
        tx.commit();
        sesion.close();
    }

    public void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        System.out.println("Ocurrió un error en la capa de acceso a datos " + he.getMessage());
        System.exit(0);
    }

    public void guardaExperto(Experto experto) {
        try {
            iniciaOperacion();
            sesion.save(experto);
            System.out.println("Experto insertado correctamente");
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            finalizaOperacion();
        }
    }

    public void eliminaExperto(Experto experto) {
        try {
            iniciaOperacion();
            sesion.delete(experto);
            System.out.println("Experto eliminado correctamente.");
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            finalizaOperacion();
        }
    }

    public void actualizaExperto(Experto experto) {
        try {
            iniciaOperacion();
            sesion.update(experto);
            System.out.println("Experto actualizado correctamente.");
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            finalizaOperacion();
        }
    }

    public Experto obtenExperto(String idExperto) {
        this.iniciaOperacion();        
        Query query = sesion.createQuery("SELECT e FROM Experto e WHERE codExperto=:idExperto");
        query.setParameter("idExperto", idExperto);
        List<Experto> expertos = query.list();
        this.finalizaOperacion();

        return expertos.get(0);
    }

    
     public void obtenNombreYEspecialidad()
    {
        this.iniciaOperacion();
        
        Query query = sesion.createQuery("SELECT e.nombre, e.especialidad FROM Experto as e");
        List<Experto> listExpertos = query.list();
        
        for(Experto e: listExpertos)
            System.out.println("Nombre: " + e.getNombre() + " Especialidad: " + e.getEspecialidad());
        
        this.finalizaOperacion();
    }
    
    public void listaConParametro(String keyword)
    {
        this.iniciaOperacion();
        
        Query query = sesion.createQuery("FROM Experto e WHERE e.especialidad=:esp");
        query.setParameter("esp", keyword);
        List<Experto> expertos = query.list();
        
        for(Experto e: expertos)
            System.out.println("Nombre: " + e.getNombre());
        
        this.finalizaOperacion();
    }
  
     public void obtenCasos()
    {
        this.iniciaOperacion();
        
        String queryText = "SELECT DISTINCT e.nombre, cp.nombre FROM Experto as e, CasoPolicial as cp "
                        + "INNER JOIN e.colaboras inner join cp.colaboras";
        Query query = sesion.createQuery(queryText);
        List<Object[]> result = query.list();
        
        for(int i = 0; i < result.size(); i++)
            System.out.println(result.get(i)[0] + " " + result.get(i)[1]);
        
        this.finalizaOperacion();
    }

}
