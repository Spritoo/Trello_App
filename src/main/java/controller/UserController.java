package controller;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import model.User;
import service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
@PermitAll
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
    
    @PUT
    @Path("/update")
	public Response updateUser(User user) {
		Response updatedUser = userService.updateUser(user);
		return updatedUser;
	}
    
    
    @POST
    @Path("/login")
    public Response loginUser(User user) {
        String loginStatus = userService.loginUser(user);
        return Response.ok(loginStatus).build();
    }
}
