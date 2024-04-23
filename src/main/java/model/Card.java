package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Card")
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cardId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "assigendToId")
	private long assigendToId;

	@Column(name = "description")
	private String description;	
	
	@ManyToOne
	@JoinColumn(name = "listId")
	private ListofCards listofcards;
	
	@ElementCollection
	private List<String> comments = new ArrayList<>();

	

	public Card() {
		
	}


	public Card(long cardId, String name, long assigendToId, String description
			) {
		this.cardId = cardId;
		this.name = name;
		this.assigendToId = assigendToId;
		this.description = description;
	}


	public long getCardId() {
		return cardId;
	}


	public void setCardId(long cardId) {
		this.cardId = cardId;
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


	public ListofCards getListofcards() {
		return listofcards;
	}


	public void setListofcards(ListofCards listofcards) {
		this.listofcards = listofcards;
	}


	public List<String> getComments() {
		return comments;
	}


	public void setComments(List<String> comments) {
		this.comments = comments;
	}



	
}
