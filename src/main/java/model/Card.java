package model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Card")
public class Card implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;

    @Column(name = "name")
    private String name;

    @Column(name = "assignedToId")
    private long assignedToId;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "listId")
    @JsonIgnore
    private ListofCards listofcards;

    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> comments = new ArrayList<>();

    public Card() {
    }

    public Card(String name, long assignedToId, String description) {
        this.name = name;
        this.assignedToId = assignedToId;
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

    public long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(long assignedToId) {
        this.assignedToId = assignedToId;
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
