package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long boardId;
	
	private String name;
	
	private long teamLeadId;
	
	private long[] memberIds;
	
	public Board() {

	}
	
	public Board(String name, long teamLeadId, long[] memberIds) {
		this.name = name;
		this.teamLeadId = teamLeadId;
		this.memberIds = memberIds;
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

	public long getTeamLeadId() {
		return teamLeadId;
	}

	public void setTeamLeadId(long teamLeadId) {
		this.teamLeadId = teamLeadId;
	}

	public long[] getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(long[] memberIds) {
		this.memberIds = memberIds;
	}
	
	
	
	
}
