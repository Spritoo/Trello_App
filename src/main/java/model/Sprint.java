package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Sprints")
public class Sprint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardId")
    @JsonIgnore
    private Board board;
	
	@Column(length=3000)
	private String Report;

	public Sprint() {
		super();
	}

	public Sprint(long id, Board board, String report) {
		super();
		this.id = id;
		this.board = board;
		Report = report;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getReport() {
		return Report;
	}

	public void setReport(String report) {
		Report = report;
	}
	
	public void appendReport(String report) {
		Report += report;
	}
	
	
}
