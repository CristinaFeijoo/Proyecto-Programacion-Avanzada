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

@Entity
@Table(name = "paquete")
@NamedQueries({
    @NamedQuery(name = "Paquete.findAll", query = "SELECT p FROM Paquete p"),
    @NamedQuery(name = "Paquete.findByIdpaquete", query = "SELECT p FROM Paquete p WHERE p.idpaquete = :idpaquete"),
    @NamedQuery(name = "Paquete.findByCodigo", query = "SELECT p FROM Paquete p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Paquete.findByDescripcion", query = "SELECT p FROM Paquete p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Paquete.findByPeso", query = "SELECT p FROM Paquete p WHERE p.peso = :peso"),
    @NamedQuery(name = "Paquete.findByAlto", query = "SELECT p FROM Paquete p WHERE p.alto = :alto")})
public class Paquete implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpaquete")
    private Integer idpaquete;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "peso")
    private Integer peso;
    
    @Column(name = "alto")
    private Integer alto;
    
    @OneToMany(mappedBy = "estPaquete")
    private Collection<Estado> estadoCollection;
    
    @JoinColumn(name = "paq_entrega", referencedColumnName = "identrega")
    @ManyToOne
    private Entrega paqEntrega;

    public Paquete() {
    }

    public Paquete(Integer idpaquete) {
        this.idpaquete = idpaquete;
    }

    public Integer getIdpaquete() {
        return idpaquete;
    }

    public void setIdpaquete(Integer idpaquete) {
        this.idpaquete = idpaquete;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAlto() {
        return alto;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public Collection<Estado> getEstadoCollection() {
        return estadoCollection;
    }

    public void setEstadoCollection(Collection<Estado> estadoCollection) {
        this.estadoCollection = estadoCollection;
    }

    public Entrega getPaqEntrega() {
        return paqEntrega;
    }

    public void setPaqEntrega(Entrega paqEntrega) {
        this.paqEntrega = paqEntrega;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpaquete != null ? idpaquete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paquete)) {
            return false;
        }
        Paquete other = (Paquete) object;
        if ((this.idpaquete == null && other.idpaquete != null) || (this.idpaquete != null && !this.idpaquete.equals(other.idpaquete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Paquete[ idpaquete=" + idpaquete + " ]";
    }
    
}
