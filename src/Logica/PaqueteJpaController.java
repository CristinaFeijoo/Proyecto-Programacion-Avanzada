/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Entrega;
import Clases.Estado;
import Clases.Paquete;
import Logica.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class PaqueteJpaController implements Serializable {

    public PaqueteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paquete paquete) {
        if (paquete.getEstadoCollection() == null) {
            paquete.setEstadoCollection(new ArrayList<Estado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrega paqEntrega = paquete.getPaqEntrega();
            if (paqEntrega != null) {
                paqEntrega = em.getReference(paqEntrega.getClass(), paqEntrega.getIdentrega());
                paquete.setPaqEntrega(paqEntrega);
            }
            Collection<Estado> attachedEstadoCollection = new ArrayList<Estado>();
            for (Estado estadoCollectionEstadoToAttach : paquete.getEstadoCollection()) {
                estadoCollectionEstadoToAttach = em.getReference(estadoCollectionEstadoToAttach.getClass(), estadoCollectionEstadoToAttach.getIdestado());
                attachedEstadoCollection.add(estadoCollectionEstadoToAttach);
            }
            paquete.setEstadoCollection(attachedEstadoCollection);
            em.persist(paquete);
            if (paqEntrega != null) {
                paqEntrega.getPaqueteCollection().add(paquete);
                paqEntrega = em.merge(paqEntrega);
            }
            for (Estado estadoCollectionEstado : paquete.getEstadoCollection()) {
                Paquete oldEstPaqueteOfEstadoCollectionEstado = estadoCollectionEstado.getEstPaquete();
                estadoCollectionEstado.setEstPaquete(paquete);
                estadoCollectionEstado = em.merge(estadoCollectionEstado);
                if (oldEstPaqueteOfEstadoCollectionEstado != null) {
                    oldEstPaqueteOfEstadoCollectionEstado.getEstadoCollection().remove(estadoCollectionEstado);
                    oldEstPaqueteOfEstadoCollectionEstado = em.merge(oldEstPaqueteOfEstadoCollectionEstado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paquete paquete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paquete persistentPaquete = em.find(Paquete.class, paquete.getIdpaquete());
            Entrega paqEntregaOld = persistentPaquete.getPaqEntrega();
            Entrega paqEntregaNew = paquete.getPaqEntrega();
            Collection<Estado> estadoCollectionOld = persistentPaquete.getEstadoCollection();
            Collection<Estado> estadoCollectionNew = paquete.getEstadoCollection();
            if (paqEntregaNew != null) {
                paqEntregaNew = em.getReference(paqEntregaNew.getClass(), paqEntregaNew.getIdentrega());
                paquete.setPaqEntrega(paqEntregaNew);
            }
            Collection<Estado> attachedEstadoCollectionNew = new ArrayList<Estado>();
            for (Estado estadoCollectionNewEstadoToAttach : estadoCollectionNew) {
                estadoCollectionNewEstadoToAttach = em.getReference(estadoCollectionNewEstadoToAttach.getClass(), estadoCollectionNewEstadoToAttach.getIdestado());
                attachedEstadoCollectionNew.add(estadoCollectionNewEstadoToAttach);
            }
            estadoCollectionNew = attachedEstadoCollectionNew;
            paquete.setEstadoCollection(estadoCollectionNew);
            paquete = em.merge(paquete);
            if (paqEntregaOld != null && !paqEntregaOld.equals(paqEntregaNew)) {
                paqEntregaOld.getPaqueteCollection().remove(paquete);
                paqEntregaOld = em.merge(paqEntregaOld);
            }
            if (paqEntregaNew != null && !paqEntregaNew.equals(paqEntregaOld)) {
                paqEntregaNew.getPaqueteCollection().add(paquete);
                paqEntregaNew = em.merge(paqEntregaNew);
            }
            for (Estado estadoCollectionOldEstado : estadoCollectionOld) {
                if (!estadoCollectionNew.contains(estadoCollectionOldEstado)) {
                    estadoCollectionOldEstado.setEstPaquete(null);
                    estadoCollectionOldEstado = em.merge(estadoCollectionOldEstado);
                }
            }
            for (Estado estadoCollectionNewEstado : estadoCollectionNew) {
                if (!estadoCollectionOld.contains(estadoCollectionNewEstado)) {
                    Paquete oldEstPaqueteOfEstadoCollectionNewEstado = estadoCollectionNewEstado.getEstPaquete();
                    estadoCollectionNewEstado.setEstPaquete(paquete);
                    estadoCollectionNewEstado = em.merge(estadoCollectionNewEstado);
                    if (oldEstPaqueteOfEstadoCollectionNewEstado != null && !oldEstPaqueteOfEstadoCollectionNewEstado.equals(paquete)) {
                        oldEstPaqueteOfEstadoCollectionNewEstado.getEstadoCollection().remove(estadoCollectionNewEstado);
                        oldEstPaqueteOfEstadoCollectionNewEstado = em.merge(oldEstPaqueteOfEstadoCollectionNewEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paquete.getIdpaquete();
                if (findPaquete(id) == null) {
                    throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.");
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
            Paquete paquete;
            try {
                paquete = em.getReference(Paquete.class, id);
                paquete.getIdpaquete();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paquete with id " + id + " no longer exists.", enfe);
            }
            Entrega paqEntrega = paquete.getPaqEntrega();
            if (paqEntrega != null) {
                paqEntrega.getPaqueteCollection().remove(paquete);
                paqEntrega = em.merge(paqEntrega);
            }
            Collection<Estado> estadoCollection = paquete.getEstadoCollection();
            for (Estado estadoCollectionEstado : estadoCollection) {
                estadoCollectionEstado.setEstPaquete(null);
                estadoCollectionEstado = em.merge(estadoCollectionEstado);
            }
            em.remove(paquete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paquete> findPaqueteEntities() {
        return findPaqueteEntities(true, -1, -1);
    }

    public List<Paquete> findPaqueteEntities(int maxResults, int firstResult) {
        return findPaqueteEntities(false, maxResults, firstResult);
    }

    private List<Paquete> findPaqueteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paquete.class));
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

    public Paquete findPaquete(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paquete.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaqueteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paquete> rt = cq.from(Paquete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
