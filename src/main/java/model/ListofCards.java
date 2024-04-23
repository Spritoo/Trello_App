package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class ListofCards{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long listId;
	
	private String name;
	
	//@OneToMany(mappedBy = "listOfCards")
	
	private List<Card> cards;
	
	public ListofCards() {

	}
	
	public ListofCards(String name, List<Card> cards) {
		this.name = name;
		this.cards = cards;
	}

	public long getListId() {
		return listId;
	}

	public void setListId(long listId) {
		this.listId = listId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	

	
}
