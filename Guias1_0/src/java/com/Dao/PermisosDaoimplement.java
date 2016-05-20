/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import com.Controladores.PermisosJpaController;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LuisGuillermo
 */
public class PermisosDaoimplement extends PermisosJpaController{

    public PermisosDaoimplement(EntityManagerFactory emf) {
        super(emf);
    }
    
}
