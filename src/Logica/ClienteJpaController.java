/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Clases.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Clases.Persona;
import Clases.Direccion;
import java.util.ArrayList;
import java.util.Collection;
import Clases.Entrega;
import Logica.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DELL
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getDireccionCollection() == null) {
            cliente.setDireccionCollection(new ArrayList<Direccion>());
        }
        if (cliente.getEntregaCollection() == null) {
            cliente.setEntregaCollection(new ArrayList<Entrega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona cliePersona = cliente.getCliePersona();
            if (cliePersona != null) {
                cliePersona = em.getReference(cliePersona.getClass(), cliePersona.getIdpersona());
                cliente.setCliePersona(cliePersona);
            }
            Collection<Direccion> attachedDireccionCollection = new ArrayList<Direccion>();
            for (Direccion direccionCollectionDireccionToAttach : cliente.getDireccionCollection()) {
                direccionCollectionDireccionToAttach = em.getReference(direccionCollectionDireccionToAttach.getClass(), direccionCollectionDireccionToAttach.getIddireccion());
                attachedDireccionCollection.add(direccionCollectionDireccionToAttach);
            }
            cliente.setDireccionCollection(attachedDireccionCollection);
            Collection<Entrega> attachedEntregaCollection = new ArrayList<Entrega>();
            for (Entrega entregaCollectionEntregaToAttach : cliente.getEntregaCollection()) {
                entregaCollectionEntregaToAttach = em.getReference(entregaCollectionEntregaToAttach.getClass(), entregaCollectionEntregaToAttach.getIdentrega());
                attachedEntregaCollection.add(entregaCollectionEntregaToAttach);
            }
            cliente.setEntregaCollection(attachedEntregaCollection);
            em.persist(cliente);
            if (cliePersona != null) {
                cliePersona.getClienteCollection().add(cliente);
                cliePersona = em.merge(cliePersona);
            }
            for (Direccion direccionCollectionDireccion : cliente.getDireccionCollection()) {
                Cliente oldDireccClienteOfDireccionCollectionDireccion = direccionCollectionDireccion.getDireccCliente();
                direccionCollectionDireccion.setDireccCliente(cliente);
                direccionCollectionDireccion = em.merge(direccionCollectionDireccion);
                if (oldDireccClienteOfDireccionCollectionDireccion != null) {
                    oldDireccClienteOfDireccionCollectionDireccion.getDireccionCollection().remove(direccionCollectionDireccion);
                    oldDireccClienteOfDireccionCollectionDireccion = em.merge(oldDireccClienteOfDireccionCollectionDireccion);
                }
            }
            for (Entrega entregaCollectionEntrega : cliente.getEntregaCollection()) {
                Cliente oldEntreClienteOfEntregaCollectionEntrega = entregaCollectionEntrega.getEntreCliente();
                entregaCollectionEntrega.setEntreCliente(cliente);
                entregaCollectionEntrega = em.merge(entregaCollectionEntrega);
                if (oldEntreClienteOfEntregaCollectionEntrega != null) {
                    oldEntreClienteOfEntregaCollectionEntrega.getEntregaCollection().remove(entregaCollectionEntrega);
                    oldEntreClienteOfEntregaCollectionEntrega = em.merge(oldEntreClienteOfEntregaCollectionEntrega);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            Persona cliePersonaOld = persistentCliente.getCliePersona();
            Persona cliePersonaNew = cliente.getCliePersona();
            Collection<Direccion> direccionCollectionOld = persistentCliente.getDireccionCollection();
            Collection<Direccion> direccionCollectionNew = cliente.getDireccionCollection();
            Collection<Entrega> entregaCollectionOld = persistentCliente.getEntregaCollection();
            Collection<Entrega> entregaCollectionNew = cliente.getEntregaCollection();
            if (cliePersonaNew != null) {
                cliePersonaNew = em.getReference(cliePersonaNew.getClass(), cliePersonaNew.getIdpersona());
                cliente.setCliePersona(cliePersonaNew);
            }
            Collection<Direccion> attachedDireccionCollectionNew = new ArrayList<Direccion>();
            for (Direccion direccionCollectionNewDireccionToAttach : direccionCollectionNew) {
                direccionCollectionNewDireccionToAttach = em.getReference(direccionCollectionNewDireccionToAttach.getClass(), direccionCollectionNewDireccionToAttach.getIddireccion());
                attachedDireccionCollectionNew.add(direccionCollectionNewDireccionToAttach);
            }
            direccionCollectionNew = attachedDireccionCollectionNew;
            cliente.setDireccionCollection(direccionCollectionNew);
            Collection<Entrega> attachedEntregaCollectionNew = new ArrayList<Entrega>();
            for (Entrega entregaCollectionNewEntregaToAttach : entregaCollectionNew) {
                entregaCollectionNewEntregaToAttach = em.getReference(entregaCollectionNewEntregaToAttach.getClass(), entregaCollectionNewEntregaToAttach.getIdentrega());
                attachedEntregaCollectionNew.add(entregaCollectionNewEntregaToAttach);
            }
            entregaCollectionNew = attachedEntregaCollectionNew;
            cliente.setEntregaCollection(entregaCollectionNew);
            cliente = em.merge(cliente);
            if (cliePersonaOld != null && !cliePersonaOld.equals(cliePersonaNew)) {
                cliePersonaOld.getClienteCollection().remove(cliente);
                cliePersonaOld = em.merge(cliePersonaOld);
            }
            if (cliePersonaNew != null && !cliePersonaNew.equals(cliePersonaOld)) {
                cliePersonaNew.getClienteCollection().add(cliente);
                cliePersonaNew = em.merge(cliePersonaNew);
            }
            for (Direccion direccionCollectionOldDireccion : direccionCollectionOld) {
                if (!direccionCollectionNew.contains(direccionCollectionOldDireccion)) {
                    direccionCollectionOldDireccion.setDireccCliente(null);
                    direccionCollectionOldDireccion = em.merge(direccionCollectionOldDireccion);
                }
            }
            for (Direccion direccionCollectionNewDireccion : direccionCollectionNew) {
                if (!direccionCollectionOld.contains(direccionCollectionNewDireccion)) {
                    Cliente oldDireccClienteOfDireccionCollectionNewDireccion = direccionCollectionNewDireccion.getDireccCliente();
                    direccionCollectionNewDireccion.setDireccCliente(cliente);
                    direccionCollectionNewDireccion = em.merge(direccionCollectionNewDireccion);
                    if (oldDireccClienteOfDireccionCollectionNewDireccion != null && !oldDireccClienteOfDireccionCollectionNewDireccion.equals(cliente)) {
                        oldDireccClienteOfDireccionCollectionNewDireccion.getDireccionCollection().remove(direccionCollectionNewDireccion);
                        oldDireccClienteOfDireccionCollectionNewDireccion = em.merge(oldDireccClienteOfDireccionCollectionNewDireccion);
                    }
                }
            }
            for (Entrega entregaCollectionOldEntrega : entregaCollectionOld) {
                if (!entregaCollectionNew.contains(entregaCollectionOldEntrega)) {
                    entregaCollectionOldEntrega.setEntreCliente(null);
                    entregaCollectionOldEntrega = em.merge(entregaCollectionOldEntrega);
                }
            }
            for (Entrega entregaCollectionNewEntrega : entregaCollectionNew) {
                if (!entregaCollectionOld.contains(entregaCollectionNewEntrega)) {
                    Cliente oldEntreClienteOfEntregaCollectionNewEntrega = entregaCollectionNewEntrega.getEntreCliente();
                    entregaCollectionNewEntrega.setEntreCliente(cliente);
                    entregaCollectionNewEntrega = em.merge(entregaCollectionNewEntrega);
                    if (oldEntreClienteOfEntregaCollectionNewEntrega != null && !oldEntreClienteOfEntregaCollectionNewEntrega.equals(cliente)) {
                        oldEntreClienteOfEntregaCollectionNewEntrega.getEntregaCollection().remove(entregaCollectionNewEntrega);
                        oldEntreClienteOfEntregaCollectionNewEntrega = em.merge(oldEntreClienteOfEntregaCollectionNewEntrega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Persona cliePersona = cliente.getCliePersona();
            if (cliePersona != null) {
                cliePersona.getClienteCollection().remove(cliente);
                cliePersona = em.merge(cliePersona);
            }
            Collection<Direccion> direccionCollection = cliente.getDireccionCollection();
            for (Direccion direccionCollectionDireccion : direccionCollection) {
                direccionCollectionDireccion.setDireccCliente(null);
                direccionCollectionDireccion = em.merge(direccionCollectionDireccion);
            }
            Collection<Entrega> entregaCollection = cliente.getEntregaCollection();
            for (Entrega entregaCollectionEntrega : entregaCollection) {
                entregaCollectionEntrega.setEntreCliente(null);
                entregaCollectionEntrega = em.merge(entregaCollectionEntrega);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
