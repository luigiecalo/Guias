/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidates;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LuisGuillermo
 */
@Entity
@Table(name = "miembro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembro.findAll", query = "SELECT m FROM Miembro m"),
    @NamedQuery(name = "Miembro.findByIdmiembro", query = "SELECT m FROM Miembro m WHERE m.miembroPK.idmiembro = :idmiembro"),
    @NamedQuery(name = "Miembro.findByNombre1", query = "SELECT m FROM Miembro m WHERE m.nombre1 = :nombre1"),
    @NamedQuery(name = "Miembro.findByNombre2", query = "SELECT m FROM Miembro m WHERE m.nombre2 = :nombre2"),
    @NamedQuery(name = "Miembro.findByApellido1", query = "SELECT m FROM Miembro m WHERE m.apellido1 = :apellido1"),
    @NamedQuery(name = "Miembro.findByApellido2", query = "SELECT m FROM Miembro m WHERE m.apellido2 = :apellido2"),
    @NamedQuery(name = "Miembro.findByEstado", query = "SELECT m FROM Miembro m WHERE m.estado = :estado"),
    @NamedQuery(name = "Miembro.findByIdusuarios", query = "SELECT m FROM Miembro m WHERE m.miembroPK.idusuarios = :idusuarios")})
public class Miembro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroPK miembroPK;
    @Basic(optional = false)
    @Column(name = "nombre1")
    private String nombre1;
    @Column(name = "nombre2")
    private String nombre2;
    @Basic(optional = false)
    @Column(name = "apellido1")
    private String apellido1;
    @Column(name = "apellido2")
    private String apellido2;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idusuarios", referencedColumnName = "idusuarios", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Miembro() {
    }

    public Miembro(MiembroPK miembroPK) {
        this.miembroPK = miembroPK;
    }

    public Miembro(MiembroPK miembroPK, String nombre1, String apellido1, String estado) {
        this.miembroPK = miembroPK;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.estado = estado;
    }

    public Miembro(int idmiembro, int idusuarios) {
        this.miembroPK = new MiembroPK(idmiembro, idusuarios);
    }

    public MiembroPK getMiembroPK() {
        return miembroPK;
    }

    public void setMiembroPK(MiembroPK miembroPK) {
        this.miembroPK = miembroPK;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroPK != null ? miembroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembro)) {
            return false;
        }
        Miembro other = (Miembro) object;
        if ((this.miembroPK == null && other.miembroPK != null) || (this.miembroPK != null && !this.miembroPK.equals(other.miembroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidates.Miembro[ miembroPK=" + miembroPK + " ]";
    }
    
}
