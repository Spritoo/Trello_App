package service;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;

import model.Board;
import model.ListofCards;
import model.User;

@Stateless
public class BoardService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
//	@Inject
//	private LoginSession loginSession;
//	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response createBoard(Board board, long userId) {
		User user = entityManager.find(User.class, userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		} else {
			board.setTeamLeader(user);
			entityManager.persist(board);
			return Response.status(Response.Status.CREATED).entity("Board created successfully").build();
		}
	}
	
	public Response getBoards() {
		List<Board> boards = entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
		return Response.ok(boards).build();
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response inviteMember(Long boardId, Long memberId) {
		User user = entityManager.find(User.class, memberId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		} else {
			Board board = entityManager.find(Board.class, boardId);
			if (board == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
			} else {
				board.addMember(user);
				return Response.status(Response.Status.CREATED).entity("User added successfully").build();
			}
		}
	}
	
	}

/*	public String createBoard(Board board, User user) {
        if (user.getRole() == "Team Leader") {
            entityManager.persist(board);
            return "Board created successfully";
        }
        return "User does not have permission to create a board";
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
	}*/

