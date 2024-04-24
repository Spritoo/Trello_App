package controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.Card;
import service.CardService;

@Path("/card")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardController {
	
	@EJB
	private CardService cardService;
	
	@POST
	@Path("/create")
	public Response createCard(Card card, Long userId) {
		String response = cardService.createCard(card, userId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@POST
	@Path("/addComment")
	public Response addCommentToCard(Long cardId, String comment) {
		String response = cardService.addComment(cardId, comment);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@POST
	@Path("/addDescription")
	public Response addDescriptionToCard(Long cardId, String description) {
		String response = cardService.addDescription(cardId, description);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@PUT
	@Path("/move")
	public Response moveCard(Long cardId, Long listId) {
		String response = cardService.moveCard(cardId, listId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@DELETE
	@Path("/delete")
	public Response deleteCard(Long cardId) {
		String response = cardService.deleteCard(cardId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@GET
	@Path("/get")
	public Response getCard(Long cardId) {
		Card card = cardService.getCard(cardId);
		return Response.status(Response.Status.CREATED).entity(card).build();
	}
	
	@GET
	@Path("/getComments")
	public Response getComments(Long cardId) {
		List<String> response = cardService.getComments(cardId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
}
