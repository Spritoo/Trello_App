package controller;

import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.Card;
import model.ListofCards;
import service.ListService;

@Path("/list")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListController {
	@EJB
	private ListService listService;
	
	@POST
	@Path("/create")
	public Response createList(ListofCards list) {
		String response = listService.createList(list);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@POST
	@Path("/addCard")
	public Response addCardToList(Long listId, Long cardId) {
		String response = listService.addCardToList(listId, cardId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	
	@PUT
	@Path("/update")
	public Response updateList(ListofCards list) {
		String response = listService.updateList(list);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@DELETE
	@Path("/delete")
	public Response deleteList(Long listId) {
		String response = listService.deleteList(listId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	@DELETE
	@Path("/removeCard")
	public Response removeCardFromList(Long listId, Long cardId) {
		String response = listService.removeCardFromList(listId, cardId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@GET
	@Path("/get/{id}")
	public ListofCards getList(@PathParam("id") Long id) {
		return listService.getList(id);
	}
	
	@GET
	@Path("/getCards/{id}")
	public Set<Card> getCards(@PathParam("id") Long id) {
		return listService.getCards(id);
	}

}
