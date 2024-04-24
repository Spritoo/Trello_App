package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException; // Import for exception handling
import model.User;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public String createUser(User user) {
        try {
            // Check if user with the same email already exists
            entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", user.getEmail())
                .getSingleResult();
            // User with this email already exists
            return "User with this email already exists";
        } catch (NoResultException e) {
            // User does not exist, proceed to create
            entityManager.persist(user);
            return "User created successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating user";
        }
    }

    public String loginUser(User user) {
        try {
            User foundUser = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", user.getEmail())
                    .setParameter("password", user.getPassword())
                    .getSingleResult();
            if (foundUser != null) {
                return "User logged in successfully";
            }
            return "Invalid email or password";
        } catch (NoResultException e) {
            return "User not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error logging in";
        }
    }

    public String updateUser(User updatedUser) {
        try {
            User user = entityManager.find(User.class, updatedUser.getUserId());
            if (user != null) {
                entityManager.merge(updatedUser);
                return "User updated successfully";
            }
            return "User not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating user";
        }
    }

    public String deleteUser(Long userId) {
        try {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                entityManager.remove(user);
                return "User deleted successfully";
            }
            return "User not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting user";
        }
    }

    public User getUserById(Long userId) {
        try {
            return entityManager.find(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
