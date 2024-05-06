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
	@Path("/create")
	public Response createCard(Card card, @QueryParam("userId") Long userId, @QueryParam("listId") Long listId) {
		return cardService.createCard(card, userId, listId);
	}

	// add descriptions
	@PUT
	@Path("/description")
	public Response addDescription(@QueryParam("cardId") Long cardId, @QueryParam("description") String description,
			@QueryParam("userId") Long userId) {
		return cardService.addDescription(cardId, userId, description);
	}

	// add comments
	@PUT
	@Path("/comment")
	public Response addComment(@QueryParam("cardId") Long cardId, @QueryParam("comment") String comment,
			@QueryParam("userId") Long userId) {
		return cardService.addComment(cardId, userId, comment);
	}

	// assign cards to themselves or other collaborators.
	@PUT
	@Path("/assign")
	public Response assignCard(@QueryParam("cardId") Long cardId, @QueryParam("userId") Long userId,
			@QueryParam("assignedToId") Long assignedToId) {
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
	@Path("/details")
	public Response getCardDetails(@QueryParam("cardId") Long cardId, @QueryParam("userId") Long userId) {
		return cardService.getCard(cardId, userId);
	}
	
	// get all cards in a list
	@GET
	@Path("/all")
	public Response getAllCards(@QueryParam("listId") Long listId, @QueryParam("userId") Long userId) {
		return cardService.getCards(listId, userId);
	}

}
