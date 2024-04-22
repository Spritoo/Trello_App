package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    public void updateUser(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    
    
}