package Logica;

import Clases.Bodeguero;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.exceptions.NonexistentEntityException;
import java.util.List;

public class BodegueroJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public BodegueroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodeguero bodeguero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bodeguero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bodeguero bodeguero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bodeguero = em.merge(bodeguero);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bodeguero.getIdbodeguero();
                if (findBodeguero(id) == null) {
                    throw new NonexistentEntityException("The bodeguero with id " + id + " no longer exists.");
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
            Bodeguero bodeguero;
            try {
                bodeguero = em.getReference(Bodeguero.class, id);
                bodeguero.getIdbodeguero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bodeguero with id " + id + " no longer exists.", enfe);
            }
            em.remove(bodeguero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bodeguero> findBodegueroEntities() {
        return findBodegueroEntities(true, -1, -1);
    }

    public List<Bodeguero> findBodegueroEntities(int maxResults, int firstResult) {
        return findBodegueroEntities(false, maxResults, firstResult);
    }

    private List<Bodeguero> findBodegueroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bodeguero.class));
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

    public Bodeguero findBodeguero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bodeguero.class, id);
        } finally {
            em.close();
        }
    }

    public int getBodegueroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bodeguero> rt = cq.from(Bodeguero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
