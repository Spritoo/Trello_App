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
	public Response createList(@QueryParam("boardId") long boardId, @QueryParam("listName") String listName,
			@QueryParam("userId") long teamLeaderId) {
		return listService.createList(listName, boardId, teamLeaderId);
	}

	@DELETE
	@Path("/deleteList")
	public Response deleteList(@QueryParam("listId") Long listId, @QueryParam("boardId") Long boardId,
			@PathParam("userId") Long userId) {
		return listService.deleteList(listId,  userId);
	}

}
