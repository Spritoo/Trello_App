package model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="User", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username")
    private String username;
    
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    
//    @NotNull
//    @Column(name = "leader")
//    private boolean leader;
    
    // if the user is a team leader
    @JsonIgnore
    @OneToMany(mappedBy = "teamLeader")
    private Set<Board> boardsAsLeader = new HashSet<>();
    
    
    // if the user is a contributer
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_contributions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    private Set<User> contributedBoards = new HashSet<>();

    
	public User() {
		super();
	}


//	public User(Long userId, String username, @NotNull String email, String password, @NotNull boolean leader) {
//		super();
//		this.userId = userId;
//		this.username = username;
//		this.email = email;
//		this.password = password;
//		this.leader = leader;
//	}

	public User(Long userId, String username, @NotNull String email, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


//	public boolean isLeader() {
//		return leader;
//	}
//
//
//	public void setRole(boolean leader) {
//		this.leader = leader;
//	}


	public Set<Board> getBoardsAsLeader() {
		return boardsAsLeader;
	}


	public void setBoardsAsLeader(Set<Board> boardsAsLeader) {
		this.boardsAsLeader = boardsAsLeader;
	}


	public Set<User> getContributedBoards() {
		return contributedBoards;
	}


	public void setContributedBoards(Set<User> contributedBoards) {
		this.contributedBoards = contributedBoards;
	}
    
   
    
    
}
