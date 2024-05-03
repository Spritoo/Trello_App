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
	@Path("/createList/{boardId}/{userId}")
	public Response createList(ListofCards list, @PathParam("boardId") Long boardId, @PathParam("userId") Long userId) {
		return listService.createList(list, boardId, userId);
	}

	@DELETE
	@Path("/deleteList/{listId}/{boardId}/{userId}")
	public Response deleteList(@PathParam("listId") Long listId, @PathParam("boardId") Long boardId,
			@PathParam("userId") Long userId) {
		return listService.deleteList(listId, boardId, userId);
	}

}
