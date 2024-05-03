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

	// create cards within a list to represent individual tasks.
	@POST
	@Path("/create/{listId}/{userId}")
	public Response createCard(Card card, @PathParam("userId") Long userId, @PathParam("listId") Long listId) {
		return cardService.createCard(card, userId, listId);
	}

	// add descriptions
	@PUT
	@Path("/description/{cardId}/{userId}")
	public Response addDescription(@PathParam("cardId") Long cardId, @QueryParam("description") String description,
			@PathParam("userId") Long userId) {
		return cardService.addDescription(cardId, userId, description);
	}

	// add comments
	@PUT
	@Path("/comment/{cardId}/{userId}")
	public Response addComment(@PathParam("cardId") Long cardId, @QueryParam("comment") String comment,
			@PathParam("userId") Long userId) {
		return cardService.addComment(cardId, userId, comment);
	}

	// assign cards to themselves or other collaborators.
	@PUT
	@Path("/assign/{cardId}/{userId}/{assignedToId}")
	public Response assignCard(@PathParam("cardId") Long cardId, @PathParam("userId") Long userId,
			@PathParam("assignedToId") Long assignedToId) {
		return cardService.assignCard(cardId, userId, assignedToId);
	}

	// move cards between lists.
	@PUT
	@Path("/move")
	public Response moveCard(@QueryParam("cardId") Long cardId, @QueryParam("listId") Long listId,
			@QueryParam("userId") Long userId) {
		return cardService.moveCard(cardId, listId, userId);
	}

	// get card details
	@GET
	@Path("/details/{cardId}/{userId}")
	public Response getCardDetails(@PathParam("cardId") Long cardId, @PathParam("userId") Long userId) {
		return cardService.getCard(cardId, userId);
	}

}
