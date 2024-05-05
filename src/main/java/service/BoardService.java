package service;

import java.util.List;
import java.util.Set;

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
//  private MessagingSystemService messagingService;

//	@Inject
//	private LoginSession loginSession;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response createBoard(String name, long userId) {
		
		User user = entityManager.find(User.class, userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		} else {
			try {
				Board board = new Board();
				board.setName(name);
				board.setTeamLeader(user);
				entityManager.persist(board);
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating board").build();
			}
			// messagingService.sendMessage("New board created: " + board.getName());
			return Response.status(Response.Status.CREATED).entity("Board created successfully").build();
		}
	}

	public Response getBoards() {
		List<Board> boards = entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
		return Response.ok(boards).build();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response inviteMember(long boardId, long memberId, long teamLeaderId) {
		
		Board board = entityManager.find(Board.class, boardId);
		if (board == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
		}
		User leader = entityManager.find(User.class, teamLeaderId);
		if (leader == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Leader not found").build();
			
		}
		if (board.getTeamLeader() != leader ) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a team leader of the board")
					.build();
		}
		User member = entityManager.find(User.class, memberId);
		if (member == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		
		if (board.getMembersIds().contains(memberId)) {
			return Response.status(Response.Status.CONFLICT).entity("User is already a member").build();
		}
		try {
		board.addMember(member);
		return Response.status(Response.Status.CREATED).entity("User invited successfully").build();
	} catch (Exception e) {
		e.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error inviting user").build();
	}
	}

	// get the board by team leader id
	public Response getMyBoardsAsLeader(long teamLeaderId) {
		try {
		List<Board> boards = entityManager
				.createQuery("SELECT b FROM Board b WHERE b.teamLeader.userId = :teamLeaderId", Board.class)
				.setParameter("teamLeaderId", teamLeaderId).getResultList();
		return Response.ok(boards).build();
	} catch (Exception e) {
		e.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error getting boards").build();
	}
	}

	// delete the board by team leader id
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response deleteBoardByTeamLeaderId(long teamLeaderId, long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		User leader = entityManager.find(User.class, teamLeaderId);
		if (board != null && leader != null) {
			if (board.getTeamLeader() == leader) {
				entityManager.remove(board);
				return Response.status(Response.Status.OK).entity("Board deleted successfully").build();
			}
			return Response.status(Response.Status.CONFLICT).entity("User is not a team leader of the board").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();

	}
	
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


}
