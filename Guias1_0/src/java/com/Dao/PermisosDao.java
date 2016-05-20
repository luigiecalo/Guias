/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;


import com.Entidates.Permisos;
import java.util.List;

/**
 *
 * @author LuisGuillermo
 */
public interface PermisosDao {

    public void guardar(Permisos permisos);

    public void modificar(Permisos permisos);

    public Permisos buscarId(Long id);

    public void eliminar(Long id);

    public List<Permisos> Listar();

}
