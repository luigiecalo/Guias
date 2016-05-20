/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidates;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author LuisGuillermo
 */
@Embeddable
public class MiembroPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmiembro")
    private int idmiembro;
    @Basic(optional = false)
    @Column(name = "idusuarios")
    private int idusuarios;

    public MiembroPK() {
    }

    public MiembroPK(int idmiembro, int idusuarios) {
        this.idmiembro = idmiembro;
        this.idusuarios = idusuarios;
    }

    public int getIdmiembro() {
        return idmiembro;
    }

    public void setIdmiembro(int idmiembro) {
        this.idmiembro = idmiembro;
    }

    public int getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(int idusuarios) {
        this.idusuarios = idusuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmiembro;
        hash += (int) idusuarios;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroPK)) {
            return false;
        }
        MiembroPK other = (MiembroPK) object;
        if (this.idmiembro != other.idmiembro) {
            return false;
        }
        if (this.idusuarios != other.idusuarios) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.Entidates.MiembroPK[ idmiembro=" + idmiembro + ", idusuarios=" + idusuarios + " ]";
    }
    
}
