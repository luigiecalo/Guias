/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Controladores;

import com.Controladores.exceptions.IllegalOrphanException;
import com.Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.Entidates.Roles;
import java.util.ArrayList;
import java.util.List;
import com.Entidates.Miembro;
import com.Entidates.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author LuisGuillermo
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getRolesList() == null) {
            usuarios.setRolesList(new ArrayList<Roles>());
        }
        if (usuarios.getMiembroList() == null) {
            usuarios.setMiembroList(new ArrayList<Miembro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Roles> attachedRolesList = new ArrayList<Roles>();
            for (Roles rolesListRolesToAttach : usuarios.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getIdroles());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            usuarios.setRolesList(attachedRolesList);
            List<Miembro> attachedMiembroList = new ArrayList<Miembro>();
            for (Miembro miembroListMiembroToAttach : usuarios.getMiembroList()) {
                miembroListMiembroToAttach = em.getReference(miembroListMiembroToAttach.getClass(), miembroListMiembroToAttach.getMiembroPK());
                attachedMiembroList.add(miembroListMiembroToAttach);
            }
            usuarios.setMiembroList(attachedMiembroList);
            em.persist(usuarios);
            for (Roles rolesListRoles : usuarios.getRolesList()) {
                rolesListRoles.getUsuariosList().add(usuarios);
                rolesListRoles = em.merge(rolesListRoles);
            }
            for (Miembro miembroListMiembro : usuarios.getMiembroList()) {
                Usuarios oldUsuariosOfMiembroListMiembro = miembroListMiembro.getUsuarios();
                miembroListMiembro.setUsuarios(usuarios);
                miembroListMiembro = em.merge(miembroListMiembro);
                if (oldUsuariosOfMiembroListMiembro != null) {
                    oldUsuariosOfMiembroListMiembro.getMiembroList().remove(miembroListMiembro);
                    oldUsuariosOfMiembroListMiembro = em.merge(oldUsuariosOfMiembroListMiembro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdusuarios());
            List<Roles> rolesListOld = persistentUsuarios.getRolesList();
            List<Roles> rolesListNew = usuarios.getRolesList();
            List<Miembro> miembroListOld = persistentUsuarios.getMiembroList();
            List<Miembro> miembroListNew = usuarios.getMiembroList();
            List<String> illegalOrphanMessages = null;
            for (Miembro miembroListOldMiembro : miembroListOld) {
                if (!miembroListNew.contains(miembroListOldMiembro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Miembro " + miembroListOldMiembro + " since its usuarios field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Roles> attachedRolesListNew = new ArrayList<Roles>();
            for (Roles rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getIdroles());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            usuarios.setRolesList(rolesListNew);
            List<Miembro> attachedMiembroListNew = new ArrayList<Miembro>();
            for (Miembro miembroListNewMiembroToAttach : miembroListNew) {
                miembroListNewMiembroToAttach = em.getReference(miembroListNewMiembroToAttach.getClass(), miembroListNewMiembroToAttach.getMiembroPK());
                attachedMiembroListNew.add(miembroListNewMiembroToAttach);
            }
            miembroListNew = attachedMiembroListNew;
            usuarios.setMiembroList(miembroListNew);
            usuarios = em.merge(usuarios);
            for (Roles rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    rolesListOldRoles.getUsuariosList().remove(usuarios);
                    rolesListOldRoles = em.merge(rolesListOldRoles);
                }
            }
            for (Roles rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    rolesListNewRoles.getUsuariosList().add(usuarios);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                }
            }
            for (Miembro miembroListNewMiembro : miembroListNew) {
                if (!miembroListOld.contains(miembroListNewMiembro)) {
                    Usuarios oldUsuariosOfMiembroListNewMiembro = miembroListNewMiembro.getUsuarios();
                    miembroListNewMiembro.setUsuarios(usuarios);
                    miembroListNewMiembro = em.merge(miembroListNewMiembro);
                    if (oldUsuariosOfMiembroListNewMiembro != null && !oldUsuariosOfMiembroListNewMiembro.equals(usuarios)) {
                        oldUsuariosOfMiembroListNewMiembro.getMiembroList().remove(miembroListNewMiembro);
                        oldUsuariosOfMiembroListNewMiembro = em.merge(oldUsuariosOfMiembroListNewMiembro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdusuarios();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdusuarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Miembro> miembroListOrphanCheck = usuarios.getMiembroList();
            for (Miembro miembroListOrphanCheckMiembro : miembroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Miembro " + miembroListOrphanCheckMiembro + " in its miembroList field has a non-nullable usuarios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Roles> rolesList = usuarios.getRolesList();
            for (Roles rolesListRoles : rolesList) {
                rolesListRoles.getUsuariosList().remove(usuarios);
                rolesListRoles = em.merge(rolesListRoles);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
