package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardId;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "teamLeaderId", nullable = false)
	private long teamLeaderId;
	
//	@ManyToOne
//	@JoinColumn(name = "teamLeader")
//	private User teamLeader;
	
	@OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
	private Set<ListofCards> lists;
	
	@OneToMany(mappedBy = "board")
    private Set<Board_Members> boardMemberships = new HashSet<>();
    
	 
	@ElementCollection
	private Set<Long> membersIds = new HashSet<>();
	
	public Board() {

	}


	public Board(long id, String name, long teamLeaderId, Set<Long> membersIds) {
		this.boardId = id;
		this.name = name;
		this.teamLeaderId = teamLeaderId;
		this.membersIds = membersIds;
	}


	public long getBoardId() {
		return boardId;
	}


	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(long teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public Set<ListofCards> getLists() {
		if (lists == null) {
			lists = new HashSet<>();
		}
		return lists;
	}

	public void setLists(Set<ListofCards> lists) {
		this.lists = lists;
	}

	public Set<Long> getMembersIds() {
		return membersIds;
	}

	public void setMembersIds(Set<Long> membersIds) {
		this.membersIds = membersIds;
	}


}