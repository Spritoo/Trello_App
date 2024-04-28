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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Board", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardId;
	
	@NotNull
	@Column(name = "name")
	String name;
	
	//team leader is the user who created the board
	@NotNull
	@ManyToOne
    @JoinColumn(name = "team_leader_id")
    private User teamLeader;
	
	@OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
	private Set<ListofCards> lists;
	
	//members are the users who are part of the board
	@ManyToMany(mappedBy = "contributedBoards")
    private Set<User> contributors = new HashSet<>();
	 
	@ElementCollection
	private Set<Long> membersIds = new HashSet<>();
	
	public Board() {

	}
	
	public Board(String name, User teamLeader) {
		this.name = name;
		this.teamLeader = teamLeader;
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
	
	public void setTeamLeader(User teamLeader) {
		this.teamLeader = teamLeader;
	}
}