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

@Entity
@Table(name = "direccion")
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d"),
    @NamedQuery(name = "Direccion.findByIddireccion", query = "SELECT d FROM Direccion d WHERE d.iddireccion = :iddireccion"),
    @NamedQuery(name = "Direccion.findByCodigo", query = "SELECT d FROM Direccion d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Direccion.findByCalle1", query = "SELECT d FROM Direccion d WHERE d.calle1 = :calle1"),
    @NamedQuery(name = "Direccion.findByCalle2", query = "SELECT d FROM Direccion d WHERE d.calle2 = :calle2"),
    @NamedQuery(name = "Direccion.findByReferencia", query = "SELECT d FROM Direccion d WHERE d.referencia = :referencia"),
    @NamedQuery(name = "Direccion.findByActual", query = "SELECT d FROM Direccion d WHERE d.actual = :actual")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddireccion")
    private Integer iddireccion;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "calle1")
    private String calle1;
    
    @Column(name = "calle2")
    private String calle2;
    
    @Column(name = "referencia")
    private String referencia;
    
    @Column(name = "actual")
    private Integer actual;
    
    @JoinColumn(name = "direcc_cliente", referencedColumnName = "idcliente")
    @ManyToOne
    private Cliente direccCliente;

    public Direccion() {
    }

    public Direccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Integer getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public String getCalle2() {
        return calle2;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public Cliente getDireccCliente() {
        return direccCliente;
    }

    public void setDireccCliente(Cliente direccCliente) {
        this.direccCliente = direccCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddireccion != null ? iddireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.iddireccion == null && other.iddireccion != null) || (this.iddireccion != null && !this.iddireccion.equals(other.iddireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Direccion[ iddireccion=" + iddireccion + " ]";
    }
    
}
