package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cardId;
	
	private String name;
	
	private long assigendToId;
	
	@ManyToOne
	@JoinColumn(name="listId")
	
	private ListofCards listOfCards;
	
	private String description;	
	
	private List<String> comments;

	public Card() {
		
	}
	
	public Card(String name, long assigendToId, String description, List<String> comments) {
		this.name = name;
		this.assigendToId = assigendToId;
		this.description = description;
		this.comments = comments;
	}
	

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public ListofCards getListOfCards() {
		return listOfCards;
	}

	public void setListOfCards(ListofCards listOfCards) {
		this.listOfCards = listOfCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAssigendToId() {
		return assigendToId;
	}

	public void setAssigendToId(long assigendToId) {
		this.assigendToId = assigendToId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
		
	
	
	
}
