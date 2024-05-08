package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import messagingSystem.client;
import model.User;
import util.EmailValidator;

@Stateless
public class UserService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private client messageClient;
	
	
//    @Inject
//    private LoginSession loginSession;

	public Response createUser(User user) {
			// check if email is valid
			if (!EmailValidator.isValid(user.getEmail()) || user.getEmail().isEmpty()) {
				messageClient.sendMessage("Invalid email");
				return Response.status(Response.Status.BAD_REQUEST).entity("Invalid email").build();
			}
			// check if password is valid
			if (user.getPassword().isEmpty()) {
				messageClient.sendMessage("Password cannot be empty");
				return Response.status(Response.Status.BAD_REQUEST).entity("Password cannot be empty").build();
			}
			else if (user.getPassword().length() < 8) {
				messageClient.sendMessage("Password must be at least 8 characters");
				return Response.status(Response.Status.BAD_REQUEST).entity("Password must be at least 8 characters")
						.build();
			}
			try {
				entityManager.persist(user);
				messageClient.sendMessage("New user created: " + user.getUsername());

			return Response.status(Response.Status.CREATED).entity("User created with id: " + user.getUserId()).build();
		} catch (Exception e) {
			e.printStackTrace();
			messageClient.sendMessage("Error creating user, user with same email");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating user, user with same email").build();
		}
	}

	public Response getUsers() {
		
		//return all users by entity manager;
		messageClient.sendMessage("Getting all users");
		return Response.ok().entity(entityManager.createQuery("SELECT u FROM User u", User.class).getResultList()).build();
		
	}
	
	

	public Response updateUser(User user) {
		User updatedUser = entityManager.find(User.class, user.getUserId());
		if (updatedUser != null) {
			try {
				messageClient.sendMessage("User updated: " + user.getUsername());
				entityManager.merge(user);
				return Response.status(Response.Status.CREATED).entity("User updated successfully").build();
			} catch (Exception e) {
				e.printStackTrace();
				messageClient.sendMessage("Error updating user");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating user").build();
			}
		}
		messageClient.sendMessage("User not found");
		return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
	}

	public String loginUser(User loginUser) {
		User user = entityManager
				.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
				.setParameter("email", loginUser.getEmail()).setParameter("password", loginUser.getPassword())
				.getSingleResult();
		if (user != null) {
			messageClient.sendMessage("User logged in: " + user.getUsername());
			return "User logged in successfully";
		}
		messageClient.sendMessage("User not found");
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
			messageClient.sendMessage("User deleted: " + user.getUsername());
			return "User deleted successfully";
		}
		messageClient.sendMessage("User not found");
		return "User not found";
	}

	public User getUserById(Long userId) {
		messageClient.sendMessage("Getting user by id: " + userId);
		return entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
				.setParameter("userId", userId).getSingleResult();
	}


}