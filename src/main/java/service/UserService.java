package service;

import java.util.List;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import model.User;


@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    
//    @Inject
//    private MessagingSystemService messagingService;
    
//    @Inject
//    private LoginSession loginSession;

    public Response createUser(User user) {
    	try {
            entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", user.getEmail())
                .getSingleResult();
            return Response.status(Response.Status.CONFLICT).entity("User with this email already exists").build();
        } catch (NoResultException e) {
            entityManager.persist(user);
            //messagingService.sendMessage("New user created: " + user.getUsername());
            return Response.status(Response.Status.CREATED).entity("User created").build();
        } catch (Exception e) {
            e.printStackTrace();
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating user").build();
        }
    }
    
	public Response getUsers() {
    	List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    	return Response.ok(users).build();
    }

	public Response updateUser(User user) {
		User updatedUser = entityManager.find(User.class, user.getUserId());
		if (updatedUser != null) {
			entityManager.merge(user);
			//messagingService.sendMessage("User updated: " + user.getUsername());
			return Response.status(Response.Status.CREATED).entity("User updated successfully").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
	}
	
	
    public String loginUser(User loginUser) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                .setParameter("email", loginUser.getEmail())
                .setParameter("password", loginUser.getPassword())
                .getSingleResult();
        if (user != null) {
            return "User logged in successfully";
        }
        return "User not found";
    }

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
            //messagingService.sendMessage("User deleted: " + user.getUsername());
            return "User deleted successfully";
        }
        return "User not found";
    }

    public User getUserById(Long userId) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId).getSingleResult();
    }
}
