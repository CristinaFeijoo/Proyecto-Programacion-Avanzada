/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "entrega")
@NamedQueries({
    @NamedQuery(name = "Entrega.findAll", query = "SELECT e FROM Entrega e"),
    @NamedQuery(name = "Entrega.findByIdentrega", query = "SELECT e FROM Entrega e WHERE e.identrega = :identrega"),
    @NamedQuery(name = "Entrega.findByCodigo", query = "SELECT e FROM Entrega e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Entrega.findByFecha", query = "SELECT e FROM Entrega e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "Entrega.findByObservacion", query = "SELECT e FROM Entrega e WHERE e.observacion = :observacion")})
public class Entrega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identrega")
    private Integer identrega;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "entre_cliente", referencedColumnName = "idcliente")
    @ManyToOne
    private Cliente entreCliente;
    @JoinColumn(name = "entre_empleado", referencedColumnName = "idempleado")
    @ManyToOne
    private Empleado entreEmpleado;
    @OneToMany(mappedBy = "paqEntrega")
    private Collection<Paquete> paqueteCollection;

    public Entrega() {
    }

    public Entrega(Integer identrega) {
        this.identrega = identrega;
    }

    public Integer getIdentrega() {
        return identrega;
    }

    public void setIdentrega(Integer identrega) {
        this.identrega = identrega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Cliente getEntreCliente() {
        return entreCliente;
    }

    public void setEntreCliente(Cliente entreCliente) {
        this.entreCliente = entreCliente;
    }

    public Empleado getEntreEmpleado() {
        return entreEmpleado;
    }

    public void setEntreEmpleado(Empleado entreEmpleado) {
        this.entreEmpleado = entreEmpleado;
    }

    public Collection<Paquete> getPaqueteCollection() {
        return paqueteCollection;
    }

    public void setPaqueteCollection(Collection<Paquete> paqueteCollection) {
        this.paqueteCollection = paqueteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identrega != null ? identrega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrega)) {
            return false;
        }
        Entrega other = (Entrega) object;
        if ((this.identrega == null && other.identrega != null) || (this.identrega != null && !this.identrega.equals(other.identrega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Entrega[ identrega=" + identrega + " ]";
    }
    
}
