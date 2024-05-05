package controller;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.ListofCards;
import service.ListService;

@Path("/list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListController {
	@EJB
	private ListService listService;

	@POST
	@Path("/createList")
	public Response createList(@QueryParam("boardId") Long boardId, @QueryParam("listName") String listName,
			@PathParam("userId") Long userId) {
		return listService.createList(listName, boardId, userId);
	}

	@DELETE
	@Path("/deleteList")
	public Response deleteList(@QueryParam("listId") Long listId, @QueryParam("boardId") Long boardId,
			@PathParam("userId") Long userId) {
		return listService.deleteList(listId,  userId);
	}

}
