package service;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Board;
import model.ListofCards;

@Stateless
public class BoardService {
	
	@PersistenceContext
	private EntityManager entityManager;

	public String createBoard(Board board) {
		Board checkBoard = entityManager.find(Board.class, board.getBoardId());
		if (checkBoard != null) {
			return "Board already exists";
		}
		entityManager.persist(board);
		return "Board created successfully";
	}


	public String updateBoard(Board updatedBoard) {
		
		Board board = entityManager.find(Board.class, updatedBoard.getBoardId());
		if (board != null) {
			entityManager.merge(updatedBoard);
			return "Board updated successfully";
		}
		return "Board not found";
	}
	
	
	public String updateTeamLeader(Long boardId, Long teamLeaderId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			board.setTeamLeaderId(teamLeaderId);
			return "Team Leader updated successfully";
		}
		return "Board not found";
	}
	
	public String addListToBoard(Long boardId, ListofCards list) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			board.getLists().add(list);
			list.setBoard(board);
			return "List added successfully";
		}
		return "Board not found";
	}
	
	public String addMemberToBoard(Long boardId, Long memberId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			board.getMembersIds().add(memberId);
			return "Member added successfully";
		}
		return "Board not found";
	}
	
	public String deleteBoard(Long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			entityManager.remove(board);
			return "Board deleted successfully";
		}
		return "Board not found";
	}
	
	public String removeListFromBoard(Long boardId, Long listId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			ListofCards list = entityManager.find(ListofCards.class, listId);
			if (list != null) {
				board.getLists().remove(list);
				//list.setBoardId(null);
				return "List removed successfully";
			}
			return "List not found";
		}
		return "Board not found";
	}
	
	public String removeMemberFromBoard(Long boardId, Long memberId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			board.getMembersIds().remove(memberId);
			return "Member removed successfully";
		}
		return "Board not found";
	}
	
	public Set<ListofCards> getListsOfBoard(Long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			return board.getLists();
		}
		return new HashSet<>();
	}
	
	public Board getBoardById(Long boardId) {
		return entityManager.find(Board.class, boardId);
	}
	
	public Set<Long> getMembersOfBoard(Long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			return board.getMembersIds();
		}
		return new HashSet<>();
	}

}
