/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "bodeguero")
@NamedQueries({
    @NamedQuery(name = "Bodeguero.findAll", query = "SELECT b FROM Bodeguero b"),
    @NamedQuery(name = "Bodeguero.findByIdbodeguero", query = "SELECT b FROM Bodeguero b WHERE b.idbodeguero = :idbodeguero"),
    @NamedQuery(name = "Bodeguero.findByLocal", query = "SELECT b FROM Bodeguero b WHERE b.local = :local")})
public class Bodeguero extends Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbodeguero")
    private Integer idbodeguero;
    
    @Column(name = "local")
    private String local;

    public Bodeguero() {
    }

    public Bodeguero(Integer idbodeguero) {
        this.idbodeguero = idbodeguero;
    }

    public Integer getIdbodeguero() {
        return idbodeguero;
    }

    public void setIdbodeguero(Integer idbodeguero) {
        this.idbodeguero = idbodeguero;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbodeguero != null ? idbodeguero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodeguero)) {
            return false;
        }
        Bodeguero other = (Bodeguero) object;
        if ((this.idbodeguero == null && other.idbodeguero != null) || (this.idbodeguero != null && !this.idbodeguero.equals(other.idbodeguero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Bodeguero[ idbodeguero=" + idbodeguero + " ]";
    }
    
}

