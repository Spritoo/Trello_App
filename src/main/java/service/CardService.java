package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Board;
import model.Card;
import model.ListofCards;

@Stateless
public class CardService {
	@PersistenceContext
	private EntityManager entityManager;
	
	// create a new card.
	public String createCard(Card card, Long userId) {
		Card checkCard = entityManager.find(Card.class, card.getCardId());
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		Boolean isMember = false;
		for (Long memberId : board.getMembersIds()) {
			if (memberId == userId) {
				isMember = true;
				break;
			}
		}
		if (checkCard != null) {
			return "Card already exists";
		}
		if (!isMember) {
			return "User is not a member of the board";
		}
		entityManager.persist(card);
		return "Card created successfully";
		
	}
	
	// move cards between lists.
	public String moveCard(Long cardId, Long listId) {
		Card card = entityManager.find(Card.class, cardId);
		ListofCards list = entityManager.find(ListofCards.class, listId);
		if (card != null && list != null) {
			card.setListofcards(list);
			return "Card moved successfully";
		}
		return "Card or list not found";
	}
	
	// assign cards to themselves or other collaborators.
	public String assignCard(Long cardId, Long userId) {
		Card card = entityManager.find(Card.class, cardId);
		if (card != null) {
			card.setAssignedToId(userId);
			return "Card assigned successfully";
		}
		return "Card not found";
	}

	// add descriptions to cards.
	
	public String addDescription(Long cardId, String description) {
		Card card = entityManager.find(Card.class, cardId);
		if (card != null) {
			card.setDescription(description);
			return "Description added successfully";
		}
		return "Card not found";
	}
	
	// add comments to cards.
	public String addComment(Long cardId, String comment) {
        Card card = entityManager.find(Card.class, cardId);
		if (card != null) {
			card.getComments().add(comment);
			return "Comment added successfully";
		}
		return "Card not found";
	}
	
	// get all comments of a card.
	public List<String> getComments(Long cardId) {
        Card card = entityManager.find(Card.class, cardId);
		if (card != null) {
			return card.getComments();
		}
		return new ArrayList<>();
	}
	
	//get card details.
	public Card getCard(Long cardId) {
		Card card = entityManager.find(Card.class, cardId);
		return card;
	}
	
	// delete a card.
	public String deleteCard(Long cardId) {
		Card card = entityManager.find(Card.class, cardId);
		ListofCards list = entityManager.find(ListofCards.class, card.getListofcards().getListId());
		Board board = entityManager.find(Board.class, list.getBoard().getBoardId());
		Boolean isTeamLeader = false;
		if (board.getTeamLeaderId() == card.getAssignedToId()) {
			isTeamLeader = true;
		}
		
		if (card != null && isTeamLeader) {
			entityManager.remove(card);
			return "Card deleted successfully";
		}
		if (isTeamLeader) {
			return "insufficient permissions";
		}
		return "Card not found";
	}
}
