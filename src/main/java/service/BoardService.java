package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import model.Board;
import model.ListofCards;
import model.User;

@Stateless
public class BoardService {

	@PersistenceContext
	private EntityManager entityManager;

//	@Inject
//    private MessagingSystemService messagingService;

//	@Inject
//	private LoginSession loginSession;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response createBoard(Board board, long userId) {
		User user = entityManager.find(User.class, userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		} else {
			// check if board name is unique
			Board existingBoard = entityManager.createQuery("SELECT b FROM Board b WHERE b.name = :name", Board.class)
					.setParameter("name", board.getName()).getSingleResult();
			if (existingBoard != null) {
				return Response.status(Response.Status.CONFLICT).entity("Board name already exists").build();
			}
			board.setTeamLeader(user);
			entityManager.persist(board);
			// messagingService.sendMessage("New board created: " + board.getName());
			return Response.status(Response.Status.CREATED).entity("Board created successfully").build();
		}
	}

	public Response getBoards() {
		List<Board> boards = entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
		return Response.ok(boards).build();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response inviteMember(Long boardId, Long memberId, Long teamLeaderId) {
		// check if board exists

		Board board = entityManager.find(Board.class, boardId);
		if (board == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
		}
		// check if user exists
		User user = entityManager.find(User.class, memberId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		// check team leader exists
		User teamLeader = entityManager.find(User.class, teamLeaderId);
		if (teamLeader == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Team Leader not found").build();
		}
		// check if user is already a member
		if (board.getMembersIds().contains(memberId)) {
			return Response.status(Response.Status.CONFLICT).entity("User is already a member").build();
		}
		// check if user is team leader
		if (teamLeaderId == memberId) {
			return Response.status(Response.Status.CONFLICT).entity("User is team leader").build();
		}
		// check if team leader is a team leader of the board
		if (teamLeaderId != board.getTeamLeader().getUserId()) {
			return Response.status(Response.Status.CONFLICT).entity("User is not a team leader of the board").build();
		}
		// add user to board
		board.getMembersIds().add(memberId);
		return Response.status(Response.Status.CREATED).entity("User invited successfully").build();
	}

	// get the board by team leader id
	public Response getBoardByTeamLeaderId(Long teamLeaderId) {
		Board board = entityManager
				.createQuery("SELECT b FROM Board b WHERE b.teamLeader.id = :teamLeaderId", Board.class)
				.setParameter("teamLeaderId", teamLeaderId).getSingleResult();
		if (board != null) {
			return Response.ok(board).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	// delete the board by team leader id
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response deleteBoardByTeamLeaderId(Long teamLeaderId, Long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			if (board.getTeamLeader().getUserId() == teamLeaderId) {
				entityManager.remove(board);
				return Response.status(Response.Status.OK).entity("Board deleted successfully").build();
			}
			return Response.status(Response.Status.CONFLICT).entity("User is not a team leader of the board").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();

	}

	// check if user is a member of the board
	public boolean isMemberOfBoard(Long boardId, Long userId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			if (board.getMembersIds().contains(userId)) {
				return true;
			} else if (board.getTeamLeader().getUserId() == userId) {
				return true;
			}
		}
		return false;
	}

	// check if List is a member of the board
	public boolean isListMemberOfBoard(Long boardId, Long listId) {
		Board board = entityManager.find(Board.class, boardId);
		ListofCards list = entityManager.find(ListofCards.class, listId);
		if (board != null && list != null) {
			if (board.getLists().contains(list)) {
				return true;
			}
		}
		return false;
	}
}
