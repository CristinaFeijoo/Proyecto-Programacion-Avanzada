package Logica;

import Clases.Cliente;
import Clases.Direccion;
import Logica.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DireccionJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente direccCliente = direccion.getDireccCliente();
            if (direccCliente != null) {
                direccCliente = em.getReference(direccCliente.getClass(), direccCliente.getIdcliente());
                direccion.setDireccCliente(direccCliente);
            }
            em.persist(direccion);
            if (direccCliente != null) {
                direccCliente.getDireccionList().add(direccion); // Modificado aquí
                direccCliente = em.merge(direccCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getIddireccion());
            Cliente direccClienteOld = persistentDireccion.getDireccCliente();
            Cliente direccClienteNew = direccion.getDireccCliente();
            if (direccClienteNew != null) {
                direccClienteNew = em.getReference(direccClienteNew.getClass(), direccClienteNew.getIdcliente());
                direccion.setDireccCliente(direccClienteNew);
            }
            direccion = em.merge(direccion);
            if (direccClienteOld != null && !direccClienteOld.equals(direccClienteNew)) {
                direccClienteOld.getDireccionList().remove(direccion); // Modificado aquí
                direccClienteOld = em.merge(direccClienteOld);
            }
            if (direccClienteNew != null && !direccClienteNew.equals(direccClienteOld)) {
                direccClienteNew.getDireccionList().add(direccion); // Modificado aquí
                direccClienteNew = em.merge(direccClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = direccion.getIddireccion();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getIddireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            Cliente direccCliente = direccion.getDireccCliente();
            if (direccCliente != null) {
                direccCliente.getDireccionList().remove(direccion); // Modificado aquí
                direccCliente = em.merge(direccCliente);
            }
            em.remove(direccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Direccion findDireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
