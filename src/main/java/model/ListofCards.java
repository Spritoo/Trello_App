package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="List")
public class ListofCards{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long listId;

	private String name;
	
	@OneToMany(mappedBy = "listofcards")
	private Set<Card> cards;
	
	public ListofCards(long listId, String name) {
		super();
		this.listId = listId;
		this.name = name;
	}

	public Set<Card> getCards() {
		if (cards == null) {
			cards = new HashSet<>();
		}
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	public ListofCards() {

	}
	
	public ListofCards(String name) {
		this.name = name;
		
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





}
