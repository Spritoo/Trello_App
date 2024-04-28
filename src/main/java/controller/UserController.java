package controller;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.User;
import service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @EJB
    private UserService userService;

    @POST
    @Path("/create")
    public Response createUser(User user) {
        Response usercreate = userService.createUser(user);
        return usercreate;
    }
    
    @GET
	public Response getUsers() {
    	Response users = userService.getUsers();
		return users;
	}
    
    @POST
	public Response updateUser(User user) {
		Response updatedUser = userService.updateUser(user);
		return updatedUser;
	}
    
    @POST
    @Path("/login")
    public Response loginUser(User user) {
    	   Response loginStatus = userService.loginUser(user);
    	   return loginStatus;
    }
//    @POST
//    @Path("/login")
//    public Response loginUser(User user) {
//        String loginStatus = userService.loginUser(user);
//        return Response.ok(loginStatus).build();
//    }
//    
//    @GET
//    @Path("/{id}")
//    public Response getUserById(@PathParam("id") Long userId) {
//        User user = userService.getUserById(userId);
//        if (user != null) {
//            return Response.ok(user).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateUser(@PathParam("id") Long userId, User updatedUser) {
//        updatedUser.setUserId(userId);
//        userService.updateUser(updatedUser);
//        return Response.ok(updatedUser).build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteUser(@PathParam("id") Long userId) {
//        userService.deleteUser(userId);
//        return Response.noContent().build();
//    }
}
