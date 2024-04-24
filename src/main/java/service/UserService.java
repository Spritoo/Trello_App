package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public String createUser(User user) {
        User checkUser = entityManager.find(User.class, user.getUserId());
        if (checkUser != null) {
            return "User already exists";
        }
        entityManager.persist(user);
        return "User created successfully";
    }

    public String updateUser(User updatedUser) {
        User user = entityManager.find(User.class, updatedUser.getUserId());
        if (user != null) {
            entityManager.merge(updatedUser);
            return "User updated successfully";
        }
        return "User not found";
    }

    public String deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
            return "User deleted successfully";
        }
        return "User not found";
    }

    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }
}
