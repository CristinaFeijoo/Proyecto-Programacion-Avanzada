/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "empleado")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdempleado", query = "SELECT e FROM Empleado e WHERE e.idempleado = :idempleado"),
    @NamedQuery(name = "Empleado.findByCiudad", query = "SELECT e FROM Empleado e WHERE e.ciudad = :ciudad")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idempleado")
    private Integer idempleado;
    @Column(name = "ciudad")
    private String ciudad;
    @OneToMany(mappedBy = "reparEmpleado")
    private Collection<Repartidor> repartidorCollection;
    @JoinColumn(name = "emple_persona", referencedColumnName = "idpersona")
    @ManyToOne
    private Persona emplePersona;
    @OneToMany(mappedBy = "bodeEmpleado")
    private Collection<Bodeguero> bodegueroCollection;
    @OneToMany(mappedBy = "entreEmpleado")
    private Collection<Entrega> entregaCollection;

    public Empleado() {
    }

    public Empleado(Integer idempleado) {
        this.idempleado = idempleado;
    }

    public Integer getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Integer idempleado) {
        this.idempleado = idempleado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Collection<Repartidor> getRepartidorCollection() {
        return repartidorCollection;
    }

    public void setRepartidorCollection(Collection<Repartidor> repartidorCollection) {
        this.repartidorCollection = repartidorCollection;
    }

    public Persona getEmplePersona() {
        return emplePersona;
    }

    public void setEmplePersona(Persona emplePersona) {
        this.emplePersona = emplePersona;
    }

    public Collection<Bodeguero> getBodegueroCollection() {
        return bodegueroCollection;
    }

    public void setBodegueroCollection(Collection<Bodeguero> bodegueroCollection) {
        this.bodegueroCollection = bodegueroCollection;
    }

    public Collection<Entrega> getEntregaCollection() {
        return entregaCollection;
    }

    public void setEntregaCollection(Collection<Entrega> entregaCollection) {
        this.entregaCollection = entregaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempleado != null ? idempleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idempleado == null && other.idempleado != null) || (this.idempleado != null && !this.idempleado.equals(other.idempleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Empleado[ idempleado=" + idempleado + " ]";
    }
    
}
