package com.paula.config;

import com.paula.model.Role;
import org.springframework.stereotype.Component;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class DataLoaderConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void loadRoles() {
        persistRoleIfNotExists("admin");
        persistRoleIfNotExists("user");
    }

    private void persistRoleIfNotExists(String roleName) {
        Role existingRole = entityManager.createQuery("SELECT * FROM Role r WHERE r.roleName = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (existingRole == null) {
            Role role = new Role();
            role.setRoleName(roleName);
            entityManager.persist(role);
        }
    }
}
