package service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import model.Board;
import model.Card;
import model.ListofCards;

@Stateless
public class CardService {
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private BoardService boardService;

//	@Inject
//	private MessagingSystemService messagingService;

	// create a new card by passing Card and userId and listId.
	public Response createCard(Card card, Long userId, Long listId) {
	    ListofCards list = entityManager.find(ListofCards.class, listId);
	    if (list == null) {
	        return Response.status(Status.NOT_FOUND).entity("List not found").build();
	    }

	    Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
	    if (board == null) {
	        return Response.status(Status.NOT_FOUND).entity("Board not found").build();
	    }

	    Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
	        return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
	    }

	    // Create a new Card entity and set its properties
	    Card newCard = new Card();
	    newCard.setName(card.getName());
	    newCard.setAssignedToId(userId);
	    newCard.setDescription(card.getDescription());
	    newCard.setComments(card.getComments());
	    newCard.setListofcards(list);

	    entityManager.persist(newCard); // Persist the new card
	    list.getCards().add(newCard); // Add the new card to the list

	    entityManager.merge(list); // Update the list in the database

	    return Response.ok(newCard).build(); // Return the new card in the response
	}


	// move cards between lists.
	public Response moveCard(Long cardId, Long listId, Long userId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}
		ListofCards list = entityManager.find(ListofCards.class, listId);
		if (list == null) {
			return Response.status(Status.NOT_FOUND).entity("List not found").build();
		}
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		card.getListofcards().getCards().remove(card);
		card.setListofcards(list);
		list.getCards().add(card);
		entityManager.merge(card);
		return Response.ok(card).build();
	}

	// assign cards to themselves or other collaborators.
	public Response assignCard(Long cardId, Long userId, Long assignedToId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}

		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		if (assignedToId != userId && !board.getMembersIds().contains(assignedToId)) {
            return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
        }

		card.setAssignedToId(assignedToId);
		entityManager.merge(card);
		return Response.ok(card).build();

	}

	// add descriptions to cards using cardId. and userId.
	public Response addDescription(Long cardId, Long userId, String description) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		card.setDescription(description);
		entityManager.merge(card);
		return Response.ok(card).build();
	}

	// add comments to cards using cardId. and userId.
	public Response addComment(Long cardId, Long userId, String comment) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();

		}
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();

		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		card.getComments().add(comment);
		entityManager.merge(card);
		return Response.ok(card).build();
	}

	// get all comments of a card.
	public Response getComments(Long cardId, Long userId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		return Response.ok(card.getComments()).build();
	}

	// get card details.
	public Response getCard(Long cardId, Long userId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		return Response.ok(card).build();
	}

	// delete a card.
	public Response deleteCard(Long cardId, Long userId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card == null) {
			return Response.status(Status.NOT_FOUND).entity("Card not found").build();
		}
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		if (board == null) {
			return Response.status(Status.NOT_FOUND).entity("Board not found").build();
		}
		Boolean isMember = board.getMembersIds().contains(userId);
		if (!isMember && board.getTeamLeaderId() != userId) {
			return Response.status(Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		list.getCards().remove(card);
		entityManager.remove(card);
		return Response.ok().build();
	}
}
