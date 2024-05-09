/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Clases.Bodeguero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Empleado;
import Logica.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class BodegueroJpaController implements Serializable {

    public BodegueroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bodeguero bodeguero) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado bodeEmpleado = bodeguero.getBodeEmpleado();
            if (bodeEmpleado != null) {
                bodeEmpleado = em.getReference(bodeEmpleado.getClass(), bodeEmpleado.getIdempleado());
                bodeguero.setBodeEmpleado(bodeEmpleado);
            }
            em.persist(bodeguero);
            if (bodeEmpleado != null) {
                bodeEmpleado.getBodegueroCollection().add(bodeguero);
                bodeEmpleado = em.merge(bodeEmpleado);
            }
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
            Bodeguero persistentBodeguero = em.find(Bodeguero.class, bodeguero.getIdbodeguero());
            Empleado bodeEmpleadoOld = persistentBodeguero.getBodeEmpleado();
            Empleado bodeEmpleadoNew = bodeguero.getBodeEmpleado();
            if (bodeEmpleadoNew != null) {
                bodeEmpleadoNew = em.getReference(bodeEmpleadoNew.getClass(), bodeEmpleadoNew.getIdempleado());
                bodeguero.setBodeEmpleado(bodeEmpleadoNew);
            }
            bodeguero = em.merge(bodeguero);
            if (bodeEmpleadoOld != null && !bodeEmpleadoOld.equals(bodeEmpleadoNew)) {
                bodeEmpleadoOld.getBodegueroCollection().remove(bodeguero);
                bodeEmpleadoOld = em.merge(bodeEmpleadoOld);
            }
            if (bodeEmpleadoNew != null && !bodeEmpleadoNew.equals(bodeEmpleadoOld)) {
                bodeEmpleadoNew.getBodegueroCollection().add(bodeguero);
                bodeEmpleadoNew = em.merge(bodeEmpleadoNew);
            }
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
            Empleado bodeEmpleado = bodeguero.getBodeEmpleado();
            if (bodeEmpleado != null) {
                bodeEmpleado.getBodegueroCollection().remove(bodeguero);
                bodeEmpleado = em.merge(bodeEmpleado);
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
