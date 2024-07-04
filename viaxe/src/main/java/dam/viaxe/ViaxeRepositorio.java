package dam.viaxe;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ViaxeRepositorio {
    /**
     * @return unha lista con todos os viaxes que haxa na base de datos
     */
    public static List<Viaxe> verViaxes() {
        List<Viaxe> lista = new ArrayList<>();
        try {
            Session se = HibernateUtil.getSessionFactory().openSession();
            Query query = se.createQuery("select v from Viaxe v");
            lista = (List<Viaxe>) query.getResultList();
            se.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    /**
     * @param cad e o parametro de busqueda co lugar que queremos buscar
     * @return unha lista cos viaxes que conteñan no comentario todo ou parte do parametro de busqueda,
     */
    public static List<Viaxe> buscarViaxes(String cad) {
        List<Viaxe> lista = new ArrayList<>();
        try {
            Session se = HibernateUtil.getSessionFactory().openSession();
            String busqueda = "%" + cad + "%";
            Query query = se.createQuery("select v from Viaxe v where v.comentario like :busqueda");
            query.setParameter("busqueda", busqueda);
            lista = (List<Viaxe>) query.getResultList();
            se.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    /**
     * @param v e o viaxe que vamos a engadir a base de datos
     */
    public static void engadir(Viaxe v) {
        Transaction trans = null;
        try {
            Session se = HibernateUtil.getSessionFactory().openSession();
            trans = se.beginTransaction();
            se.persist(v);
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            System.out.println(e.getMessage());
        }
    }
}
