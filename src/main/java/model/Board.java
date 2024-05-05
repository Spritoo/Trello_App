package model;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Board", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Board implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardId;

	@NotNull
	@Column(name = "name")
	String name;

	// team leader is the user who created the board
	@NotNull
	@ManyToOne
	@JoinColumn(name = "team_leader_id")
	@JsonIgnore
	private User teamLeader;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ListofCards> lists;

	// members are the users who are part of the board
	@JsonIgnore
	@ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "BoardMember",
            joinColumns = @JoinColumn(name = "boardID"),
            inverseJoinColumns = @JoinColumn(name = "userID")
    )
	private Set<User> contributors;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Long> membersIds = new HashSet<>();

	private long teamLeaderId;

	public Board() {
		super();
	}

	public Board(long boardId, @NotNull String name) {
		super();
		this.boardId = boardId;
		this.name = name;
	}

	public void setTeamLeader(User teamLeader) {
		this.teamLeader = teamLeader;
		teamLeaderId = teamLeader.getUserId();
	}

	public void setContributers(Set<User> contributors) {
		this.contributors = contributors;
		for (User user : contributors) {
			membersIds.add(user.getUserId());
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public User getTeamLeader() {
		return teamLeader;
	}

	public Set<User> getContributors() {
		return contributors;
	}

	public Set<Long> getMembersIds() {
		return membersIds;
	}

	public long getTeamLeaderId() {
		return teamLeaderId;
	}
	
	public void addContributer(User contributor) {
	    contributors.add(contributor);
	    membersIds.add(contributor.getUserId());
	}
	

	public Set<ListofCards> getLists() {
		return lists;
	}

	public void setLists(Set<ListofCards> lists) {
		this.lists = lists;
	}

}