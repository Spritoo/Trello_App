package service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Card;
import model.ListofCards;

@Stateless
public class ListService {
	@PersistenceContext
	private EntityManager entityManager;
	
//	@Inject
//    private MessagingSystemService messagingService;
	
	public String createList(ListofCards list) {
		ListofCards checkList = entityManager.find(ListofCards.class, list.getListId());
		if (checkList != null) {
			return "List already exists";
		}
		entityManager.persist(list);
        //messagingService.sendMessage("New list created: " + list.getName());

		return "List created successfully";
	}
	
	public String updateList(ListofCards updatedList) {
		ListofCards list = entityManager.find(ListofCards.class, updatedList.getListId());
		if (list != null) {
			entityManager.merge(updatedList);
            //messagingService.sendMessage("List updated: " + list.getName());
			return "List updated successfully";
		}
		return "List not found";
	}
	
	public String deleteList(Long listId) {
		ListofCards list = entityManager.find(ListofCards.class, listId);
		if (list != null) {
			entityManager.remove(list);
            //messagingService.sendMessage("List deleted: " + list.getName());
			return "List deleted successfully";
		}
		return "List not found";
	}
	
	public String addCardToList(Long listId, Long cardId) {
		ListofCards list = entityManager.find(ListofCards.class, listId);
		Card card = entityManager.find(Card.class, cardId);
		if (list != null) {
			list.getCards().add(card);
            //messagingService.sendMessage("Card added to list: " + list.getName());
			return "Card added to list successfully";
		}
		return "List not found";
	}
	
	public String removeCardFromList(Long listId, Long cardId) {
		ListofCards list = entityManager.find(ListofCards.class, listId);
		Card card = entityManager.find(Card.class, cardId);
		if (list != null) {
			list.getCards().remove(card);
            //messagingService.sendMessage("Card removed from list: " + list.getName());
			return "Card removed from list successfully";
		}
		return "List not found";
	}
	
	
	public ListofCards getList(Long listId) {
		ListofCards list = entityManager.find(ListofCards.class, listId);
		return list;
	}
	
	
	public Set<Card> getCards(Long listId) {
		ListofCards list = entityManager.find(ListofCards.class, listId);
		return list.getCards();
	}
	

}
