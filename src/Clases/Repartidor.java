/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "repartidor")
@NamedQueries({
    @NamedQuery(name = "Repartidor.findAll", query = "SELECT r FROM Repartidor r"),
    @NamedQuery(name = "Repartidor.findByIdrepartidor", query = "SELECT r FROM Repartidor r WHERE r.idrepartidor = :idrepartidor"),
    @NamedQuery(name = "Repartidor.findByZona", query = "SELECT r FROM Repartidor r WHERE r.zona = :zona")})
public class Repartidor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrepartidor")
    private Integer idrepartidor;
    @Column(name = "zona")
    private Integer zona;
    @JoinColumn(name = "repar_empleado", referencedColumnName = "idempleado")
    @ManyToOne
    private Empleado reparEmpleado;

    public Repartidor() {
    }

    public Repartidor(Integer idrepartidor) {
        this.idrepartidor = idrepartidor;
    }

    public Integer getIdrepartidor() {
        return idrepartidor;
    }

    public void setIdrepartidor(Integer idrepartidor) {
        this.idrepartidor = idrepartidor;
    }

    public Integer getZona() {
        return zona;
    }

    public void setZona(Integer zona) {
        this.zona = zona;
    }

    public Empleado getReparEmpleado() {
        return reparEmpleado;
    }

    public void setReparEmpleado(Empleado reparEmpleado) {
        this.reparEmpleado = reparEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrepartidor != null ? idrepartidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repartidor)) {
            return false;
        }
        Repartidor other = (Repartidor) object;
        if ((this.idrepartidor == null && other.idrepartidor != null) || (this.idrepartidor != null && !this.idrepartidor.equals(other.idrepartidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Repartidor[ idrepartidor=" + idrepartidor + " ]";
    }
    
}
