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
import Clases.Cliente;
import Clases.Empleado;
import Clases.Entrega;
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
public class EntregaJpaController implements Serializable {

    public EntregaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entrega entrega) {
        if (entrega.getPaqueteCollection() == null) {
            entrega.setPaqueteCollection(new ArrayList<Paquete>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente entreCliente = entrega.getEntreCliente();
            if (entreCliente != null) {
                entreCliente = em.getReference(entreCliente.getClass(), entreCliente.getIdcliente());
                entrega.setEntreCliente(entreCliente);
            }
            Empleado entreEmpleado = entrega.getEntreEmpleado();
            if (entreEmpleado != null) {
                entreEmpleado = em.getReference(entreEmpleado.getClass(), entreEmpleado.getIdempleado());
                entrega.setEntreEmpleado(entreEmpleado);
            }
            Collection<Paquete> attachedPaqueteCollection = new ArrayList<Paquete>();
            for (Paquete paqueteCollectionPaqueteToAttach : entrega.getPaqueteCollection()) {
                paqueteCollectionPaqueteToAttach = em.getReference(paqueteCollectionPaqueteToAttach.getClass(), paqueteCollectionPaqueteToAttach.getIdpaquete());
                attachedPaqueteCollection.add(paqueteCollectionPaqueteToAttach);
            }
            entrega.setPaqueteCollection(attachedPaqueteCollection);
            em.persist(entrega);
            if (entreCliente != null) {
                entreCliente.getEntregaCollection().add(entrega);
                entreCliente = em.merge(entreCliente);
            }
            if (entreEmpleado != null) {
                entreEmpleado.getEntregaCollection().add(entrega);
                entreEmpleado = em.merge(entreEmpleado);
            }
            for (Paquete paqueteCollectionPaquete : entrega.getPaqueteCollection()) {
                Entrega oldPaqEntregaOfPaqueteCollectionPaquete = paqueteCollectionPaquete.getPaqEntrega();
                paqueteCollectionPaquete.setPaqEntrega(entrega);
                paqueteCollectionPaquete = em.merge(paqueteCollectionPaquete);
                if (oldPaqEntregaOfPaqueteCollectionPaquete != null) {
                    oldPaqEntregaOfPaqueteCollectionPaquete.getPaqueteCollection().remove(paqueteCollectionPaquete);
                    oldPaqEntregaOfPaqueteCollectionPaquete = em.merge(oldPaqEntregaOfPaqueteCollectionPaquete);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entrega entrega) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entrega persistentEntrega = em.find(Entrega.class, entrega.getIdentrega());
            Cliente entreClienteOld = persistentEntrega.getEntreCliente();
            Cliente entreClienteNew = entrega.getEntreCliente();
            Empleado entreEmpleadoOld = persistentEntrega.getEntreEmpleado();
            Empleado entreEmpleadoNew = entrega.getEntreEmpleado();
            Collection<Paquete> paqueteCollectionOld = persistentEntrega.getPaqueteCollection();
            Collection<Paquete> paqueteCollectionNew = entrega.getPaqueteCollection();
            if (entreClienteNew != null) {
                entreClienteNew = em.getReference(entreClienteNew.getClass(), entreClienteNew.getIdcliente());
                entrega.setEntreCliente(entreClienteNew);
            }
            if (entreEmpleadoNew != null) {
                entreEmpleadoNew = em.getReference(entreEmpleadoNew.getClass(), entreEmpleadoNew.getIdempleado());
                entrega.setEntreEmpleado(entreEmpleadoNew);
            }
            Collection<Paquete> attachedPaqueteCollectionNew = new ArrayList<Paquete>();
            for (Paquete paqueteCollectionNewPaqueteToAttach : paqueteCollectionNew) {
                paqueteCollectionNewPaqueteToAttach = em.getReference(paqueteCollectionNewPaqueteToAttach.getClass(), paqueteCollectionNewPaqueteToAttach.getIdpaquete());
                attachedPaqueteCollectionNew.add(paqueteCollectionNewPaqueteToAttach);
            }
            paqueteCollectionNew = attachedPaqueteCollectionNew;
            entrega.setPaqueteCollection(paqueteCollectionNew);
            entrega = em.merge(entrega);
            if (entreClienteOld != null && !entreClienteOld.equals(entreClienteNew)) {
                entreClienteOld.getEntregaCollection().remove(entrega);
                entreClienteOld = em.merge(entreClienteOld);
            }
            if (entreClienteNew != null && !entreClienteNew.equals(entreClienteOld)) {
                entreClienteNew.getEntregaCollection().add(entrega);
                entreClienteNew = em.merge(entreClienteNew);
            }
            if (entreEmpleadoOld != null && !entreEmpleadoOld.equals(entreEmpleadoNew)) {
                entreEmpleadoOld.getEntregaCollection().remove(entrega);
                entreEmpleadoOld = em.merge(entreEmpleadoOld);
            }
            if (entreEmpleadoNew != null && !entreEmpleadoNew.equals(entreEmpleadoOld)) {
                entreEmpleadoNew.getEntregaCollection().add(entrega);
                entreEmpleadoNew = em.merge(entreEmpleadoNew);
            }
            for (Paquete paqueteCollectionOldPaquete : paqueteCollectionOld) {
                if (!paqueteCollectionNew.contains(paqueteCollectionOldPaquete)) {
                    paqueteCollectionOldPaquete.setPaqEntrega(null);
                    paqueteCollectionOldPaquete = em.merge(paqueteCollectionOldPaquete);
                }
            }
            for (Paquete paqueteCollectionNewPaquete : paqueteCollectionNew) {
                if (!paqueteCollectionOld.contains(paqueteCollectionNewPaquete)) {
                    Entrega oldPaqEntregaOfPaqueteCollectionNewPaquete = paqueteCollectionNewPaquete.getPaqEntrega();
                    paqueteCollectionNewPaquete.setPaqEntrega(entrega);
                    paqueteCollectionNewPaquete = em.merge(paqueteCollectionNewPaquete);
                    if (oldPaqEntregaOfPaqueteCollectionNewPaquete != null && !oldPaqEntregaOfPaqueteCollectionNewPaquete.equals(entrega)) {
                        oldPaqEntregaOfPaqueteCollectionNewPaquete.getPaqueteCollection().remove(paqueteCollectionNewPaquete);
                        oldPaqEntregaOfPaqueteCollectionNewPaquete = em.merge(oldPaqEntregaOfPaqueteCollectionNewPaquete);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entrega.getIdentrega();
                if (findEntrega(id) == null) {
                    throw new NonexistentEntityException("The entrega with id " + id + " no longer exists.");
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
            Entrega entrega;
            try {
                entrega = em.getReference(Entrega.class, id);
                entrega.getIdentrega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entrega with id " + id + " no longer exists.", enfe);
            }
            Cliente entreCliente = entrega.getEntreCliente();
            if (entreCliente != null) {
                entreCliente.getEntregaCollection().remove(entrega);
                entreCliente = em.merge(entreCliente);
            }
            Empleado entreEmpleado = entrega.getEntreEmpleado();
            if (entreEmpleado != null) {
                entreEmpleado.getEntregaCollection().remove(entrega);
                entreEmpleado = em.merge(entreEmpleado);
            }
            Collection<Paquete> paqueteCollection = entrega.getPaqueteCollection();
            for (Paquete paqueteCollectionPaquete : paqueteCollection) {
                paqueteCollectionPaquete.setPaqEntrega(null);
                paqueteCollectionPaquete = em.merge(paqueteCollectionPaquete);
            }
            em.remove(entrega);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entrega> findEntregaEntities() {
        return findEntregaEntities(true, -1, -1);
    }

    public List<Entrega> findEntregaEntities(int maxResults, int firstResult) {
        return findEntregaEntities(false, maxResults, firstResult);
    }

    private List<Entrega> findEntregaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entrega.class));
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

    public Entrega findEntrega(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entrega.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntregaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entrega> rt = cq.from(Entrega.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
