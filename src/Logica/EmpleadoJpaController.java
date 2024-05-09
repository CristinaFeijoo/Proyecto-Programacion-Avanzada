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
import Clases.Persona;
import Clases.Repartidor;
import java.util.ArrayList;
import java.util.Collection;
import Clases.Bodeguero;
import Clases.Empleado;
import Clases.Entrega;
import Logica.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getRepartidorCollection() == null) {
            empleado.setRepartidorCollection(new ArrayList<Repartidor>());
        }
        if (empleado.getBodegueroCollection() == null) {
            empleado.setBodegueroCollection(new ArrayList<Bodeguero>());
        }
        if (empleado.getEntregaCollection() == null) {
            empleado.setEntregaCollection(new ArrayList<Entrega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona emplePersona = empleado.getEmplePersona();
            if (emplePersona != null) {
                emplePersona = em.getReference(emplePersona.getClass(), emplePersona.getIdpersona());
                empleado.setEmplePersona(emplePersona);
            }
            Collection<Repartidor> attachedRepartidorCollection = new ArrayList<Repartidor>();
            for (Repartidor repartidorCollectionRepartidorToAttach : empleado.getRepartidorCollection()) {
                repartidorCollectionRepartidorToAttach = em.getReference(repartidorCollectionRepartidorToAttach.getClass(), repartidorCollectionRepartidorToAttach.getIdrepartidor());
                attachedRepartidorCollection.add(repartidorCollectionRepartidorToAttach);
            }
            empleado.setRepartidorCollection(attachedRepartidorCollection);
            Collection<Bodeguero> attachedBodegueroCollection = new ArrayList<Bodeguero>();
            for (Bodeguero bodegueroCollectionBodegueroToAttach : empleado.getBodegueroCollection()) {
                bodegueroCollectionBodegueroToAttach = em.getReference(bodegueroCollectionBodegueroToAttach.getClass(), bodegueroCollectionBodegueroToAttach.getIdbodeguero());
                attachedBodegueroCollection.add(bodegueroCollectionBodegueroToAttach);
            }
            empleado.setBodegueroCollection(attachedBodegueroCollection);
            Collection<Entrega> attachedEntregaCollection = new ArrayList<Entrega>();
            for (Entrega entregaCollectionEntregaToAttach : empleado.getEntregaCollection()) {
                entregaCollectionEntregaToAttach = em.getReference(entregaCollectionEntregaToAttach.getClass(), entregaCollectionEntregaToAttach.getIdentrega());
                attachedEntregaCollection.add(entregaCollectionEntregaToAttach);
            }
            empleado.setEntregaCollection(attachedEntregaCollection);
            em.persist(empleado);
            if (emplePersona != null) {
                emplePersona.getEmpleadoCollection().add(empleado);
                emplePersona = em.merge(emplePersona);
            }
            for (Repartidor repartidorCollectionRepartidor : empleado.getRepartidorCollection()) {
                Empleado oldReparEmpleadoOfRepartidorCollectionRepartidor = repartidorCollectionRepartidor.getReparEmpleado();
                repartidorCollectionRepartidor.setReparEmpleado(empleado);
                repartidorCollectionRepartidor = em.merge(repartidorCollectionRepartidor);
                if (oldReparEmpleadoOfRepartidorCollectionRepartidor != null) {
                    oldReparEmpleadoOfRepartidorCollectionRepartidor.getRepartidorCollection().remove(repartidorCollectionRepartidor);
                    oldReparEmpleadoOfRepartidorCollectionRepartidor = em.merge(oldReparEmpleadoOfRepartidorCollectionRepartidor);
                }
            }
            for (Bodeguero bodegueroCollectionBodeguero : empleado.getBodegueroCollection()) {
                Empleado oldBodeEmpleadoOfBodegueroCollectionBodeguero = bodegueroCollectionBodeguero.getBodeEmpleado();
                bodegueroCollectionBodeguero.setBodeEmpleado(empleado);
                bodegueroCollectionBodeguero = em.merge(bodegueroCollectionBodeguero);
                if (oldBodeEmpleadoOfBodegueroCollectionBodeguero != null) {
                    oldBodeEmpleadoOfBodegueroCollectionBodeguero.getBodegueroCollection().remove(bodegueroCollectionBodeguero);
                    oldBodeEmpleadoOfBodegueroCollectionBodeguero = em.merge(oldBodeEmpleadoOfBodegueroCollectionBodeguero);
                }
            }
            for (Entrega entregaCollectionEntrega : empleado.getEntregaCollection()) {
                Empleado oldEntreEmpleadoOfEntregaCollectionEntrega = entregaCollectionEntrega.getEntreEmpleado();
                entregaCollectionEntrega.setEntreEmpleado(empleado);
                entregaCollectionEntrega = em.merge(entregaCollectionEntrega);
                if (oldEntreEmpleadoOfEntregaCollectionEntrega != null) {
                    oldEntreEmpleadoOfEntregaCollectionEntrega.getEntregaCollection().remove(entregaCollectionEntrega);
                    oldEntreEmpleadoOfEntregaCollectionEntrega = em.merge(oldEntreEmpleadoOfEntregaCollectionEntrega);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdempleado());
            Persona emplePersonaOld = persistentEmpleado.getEmplePersona();
            Persona emplePersonaNew = empleado.getEmplePersona();
            Collection<Repartidor> repartidorCollectionOld = persistentEmpleado.getRepartidorCollection();
            Collection<Repartidor> repartidorCollectionNew = empleado.getRepartidorCollection();
            Collection<Bodeguero> bodegueroCollectionOld = persistentEmpleado.getBodegueroCollection();
            Collection<Bodeguero> bodegueroCollectionNew = empleado.getBodegueroCollection();
            Collection<Entrega> entregaCollectionOld = persistentEmpleado.getEntregaCollection();
            Collection<Entrega> entregaCollectionNew = empleado.getEntregaCollection();
            if (emplePersonaNew != null) {
                emplePersonaNew = em.getReference(emplePersonaNew.getClass(), emplePersonaNew.getIdpersona());
                empleado.setEmplePersona(emplePersonaNew);
            }
            Collection<Repartidor> attachedRepartidorCollectionNew = new ArrayList<Repartidor>();
            for (Repartidor repartidorCollectionNewRepartidorToAttach : repartidorCollectionNew) {
                repartidorCollectionNewRepartidorToAttach = em.getReference(repartidorCollectionNewRepartidorToAttach.getClass(), repartidorCollectionNewRepartidorToAttach.getIdrepartidor());
                attachedRepartidorCollectionNew.add(repartidorCollectionNewRepartidorToAttach);
            }
            repartidorCollectionNew = attachedRepartidorCollectionNew;
            empleado.setRepartidorCollection(repartidorCollectionNew);
            Collection<Bodeguero> attachedBodegueroCollectionNew = new ArrayList<Bodeguero>();
            for (Bodeguero bodegueroCollectionNewBodegueroToAttach : bodegueroCollectionNew) {
                bodegueroCollectionNewBodegueroToAttach = em.getReference(bodegueroCollectionNewBodegueroToAttach.getClass(), bodegueroCollectionNewBodegueroToAttach.getIdbodeguero());
                attachedBodegueroCollectionNew.add(bodegueroCollectionNewBodegueroToAttach);
            }
            bodegueroCollectionNew = attachedBodegueroCollectionNew;
            empleado.setBodegueroCollection(bodegueroCollectionNew);
            Collection<Entrega> attachedEntregaCollectionNew = new ArrayList<Entrega>();
            for (Entrega entregaCollectionNewEntregaToAttach : entregaCollectionNew) {
                entregaCollectionNewEntregaToAttach = em.getReference(entregaCollectionNewEntregaToAttach.getClass(), entregaCollectionNewEntregaToAttach.getIdentrega());
                attachedEntregaCollectionNew.add(entregaCollectionNewEntregaToAttach);
            }
            entregaCollectionNew = attachedEntregaCollectionNew;
            empleado.setEntregaCollection(entregaCollectionNew);
            empleado = em.merge(empleado);
            if (emplePersonaOld != null && !emplePersonaOld.equals(emplePersonaNew)) {
                emplePersonaOld.getEmpleadoCollection().remove(empleado);
                emplePersonaOld = em.merge(emplePersonaOld);
            }
            if (emplePersonaNew != null && !emplePersonaNew.equals(emplePersonaOld)) {
                emplePersonaNew.getEmpleadoCollection().add(empleado);
                emplePersonaNew = em.merge(emplePersonaNew);
            }
            for (Repartidor repartidorCollectionOldRepartidor : repartidorCollectionOld) {
                if (!repartidorCollectionNew.contains(repartidorCollectionOldRepartidor)) {
                    repartidorCollectionOldRepartidor.setReparEmpleado(null);
                    repartidorCollectionOldRepartidor = em.merge(repartidorCollectionOldRepartidor);
                }
            }
            for (Repartidor repartidorCollectionNewRepartidor : repartidorCollectionNew) {
                if (!repartidorCollectionOld.contains(repartidorCollectionNewRepartidor)) {
                    Empleado oldReparEmpleadoOfRepartidorCollectionNewRepartidor = repartidorCollectionNewRepartidor.getReparEmpleado();
                    repartidorCollectionNewRepartidor.setReparEmpleado(empleado);
                    repartidorCollectionNewRepartidor = em.merge(repartidorCollectionNewRepartidor);
                    if (oldReparEmpleadoOfRepartidorCollectionNewRepartidor != null && !oldReparEmpleadoOfRepartidorCollectionNewRepartidor.equals(empleado)) {
                        oldReparEmpleadoOfRepartidorCollectionNewRepartidor.getRepartidorCollection().remove(repartidorCollectionNewRepartidor);
                        oldReparEmpleadoOfRepartidorCollectionNewRepartidor = em.merge(oldReparEmpleadoOfRepartidorCollectionNewRepartidor);
                    }
                }
            }
            for (Bodeguero bodegueroCollectionOldBodeguero : bodegueroCollectionOld) {
                if (!bodegueroCollectionNew.contains(bodegueroCollectionOldBodeguero)) {
                    bodegueroCollectionOldBodeguero.setBodeEmpleado(null);
                    bodegueroCollectionOldBodeguero = em.merge(bodegueroCollectionOldBodeguero);
                }
            }
            for (Bodeguero bodegueroCollectionNewBodeguero : bodegueroCollectionNew) {
                if (!bodegueroCollectionOld.contains(bodegueroCollectionNewBodeguero)) {
                    Empleado oldBodeEmpleadoOfBodegueroCollectionNewBodeguero = bodegueroCollectionNewBodeguero.getBodeEmpleado();
                    bodegueroCollectionNewBodeguero.setBodeEmpleado(empleado);
                    bodegueroCollectionNewBodeguero = em.merge(bodegueroCollectionNewBodeguero);
                    if (oldBodeEmpleadoOfBodegueroCollectionNewBodeguero != null && !oldBodeEmpleadoOfBodegueroCollectionNewBodeguero.equals(empleado)) {
                        oldBodeEmpleadoOfBodegueroCollectionNewBodeguero.getBodegueroCollection().remove(bodegueroCollectionNewBodeguero);
                        oldBodeEmpleadoOfBodegueroCollectionNewBodeguero = em.merge(oldBodeEmpleadoOfBodegueroCollectionNewBodeguero);
                    }
                }
            }
            for (Entrega entregaCollectionOldEntrega : entregaCollectionOld) {
                if (!entregaCollectionNew.contains(entregaCollectionOldEntrega)) {
                    entregaCollectionOldEntrega.setEntreEmpleado(null);
                    entregaCollectionOldEntrega = em.merge(entregaCollectionOldEntrega);
                }
            }
            for (Entrega entregaCollectionNewEntrega : entregaCollectionNew) {
                if (!entregaCollectionOld.contains(entregaCollectionNewEntrega)) {
                    Empleado oldEntreEmpleadoOfEntregaCollectionNewEntrega = entregaCollectionNewEntrega.getEntreEmpleado();
                    entregaCollectionNewEntrega.setEntreEmpleado(empleado);
                    entregaCollectionNewEntrega = em.merge(entregaCollectionNewEntrega);
                    if (oldEntreEmpleadoOfEntregaCollectionNewEntrega != null && !oldEntreEmpleadoOfEntregaCollectionNewEntrega.equals(empleado)) {
                        oldEntreEmpleadoOfEntregaCollectionNewEntrega.getEntregaCollection().remove(entregaCollectionNewEntrega);
                        oldEntreEmpleadoOfEntregaCollectionNewEntrega = em.merge(oldEntreEmpleadoOfEntregaCollectionNewEntrega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getIdempleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdempleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Persona emplePersona = empleado.getEmplePersona();
            if (emplePersona != null) {
                emplePersona.getEmpleadoCollection().remove(empleado);
                emplePersona = em.merge(emplePersona);
            }
            Collection<Repartidor> repartidorCollection = empleado.getRepartidorCollection();
            for (Repartidor repartidorCollectionRepartidor : repartidorCollection) {
                repartidorCollectionRepartidor.setReparEmpleado(null);
                repartidorCollectionRepartidor = em.merge(repartidorCollectionRepartidor);
            }
            Collection<Bodeguero> bodegueroCollection = empleado.getBodegueroCollection();
            for (Bodeguero bodegueroCollectionBodeguero : bodegueroCollection) {
                bodegueroCollectionBodeguero.setBodeEmpleado(null);
                bodegueroCollectionBodeguero = em.merge(bodegueroCollectionBodeguero);
            }
            Collection<Entrega> entregaCollection = empleado.getEntregaCollection();
            for (Entrega entregaCollectionEntrega : entregaCollection) {
                entregaCollectionEntrega.setEntreEmpleado(null);
                entregaCollectionEntrega = em.merge(entregaCollectionEntrega);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
