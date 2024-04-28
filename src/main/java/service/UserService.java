package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import model.User;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public Response createUser(User user) {
    	
		try {
			entityManager.persist(user);
			return Response.status(Response.Status.CREATED).entity("User created successfully").build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User already exists").build();
		}
		
    }

//    public String loginUser(User loginUser) {
//        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
//                .setParameter("email", loginUser.getEmail())
//                .setParameter("password", loginUser.getPassword())
//                .getSingleResult();
//        if (user != null) {
//            // Set the role based on user input (assuming it comes from the frontend)
//            user.setRole(loginUser.getRole());
//            return "User logged in successfully";
//        }
//        return "User not found";
//    }

//    public String updateUser(User updatedUser) {
//        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
//                .setParameter("userId", updatedUser.getUserId()).getSingleResult();
//        if (user != null) {
//            // Update other user details as needed
//            user.setUsername(updatedUser.getUsername());
//            user.setEmail(updatedUser.getEmail());
//            user.setPassword(updatedUser.getPassword());
//            user.setRole(updatedUser.getRole());  // Update role
//            entityManager.merge(user);
//            return "User updated successfully";
//        }
//        return "User not found";
//    }

    public String deleteUser(Long userId) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId).getSingleResult();
        if (user != null) {
            entityManager.remove(user);
            return "User deleted successfully";
        }
        return "User not found";
    }

    public User getUserById(Long userId) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId).getSingleResult();
    }
}
