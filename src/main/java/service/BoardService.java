package service;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import messagingSystem.client;
import model.Board;
import model.ListofCards;
import model.User;

@Stateless
public class BoardService {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private client messageClient;
	
	@EJB
	private ListService listService;


//	@Inject
//	private LoginSession loginSession;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response createBoard(String name, long userId) {
		
		User user = entityManager.find(User.class, userId);
		if (user == null) {
			messageClient.sendMessage("User not found");
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		if (user.isLeader() == false) {
			messageClient.sendMessage("User is not a team leader");
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a team leader").build();
		}
		 else {
			try {
				Board board = new Board();
				board.setName(name);
				board.setTeamLeader(user);
				entityManager.persist(board);
				Board dbBoard = entityManager.find(Board.class, board.getBoardId());
				listService.createList("To Do", dbBoard.getBoardId(), userId);
				listService.createList("In Progress", dbBoard.getBoardId(), userId);
				listService.createList("Done", dbBoard.getBoardId(), userId);
			} catch (Exception e) {
				e.printStackTrace();
				messageClient.sendMessage("Error creating board");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating board").build();
			}
			messageClient.sendMessage("New board created: " + name);
			
			return Response.status(Response.Status.CREATED).entity("Board created successfully").build();
		}
	}

	public Response getBoards() {
		List<Board> boards = entityManager.createQuery("SELECT b FROM Board b", Board.class).getResultList();
		messageClient.sendMessage("Boards retrieved");
		return Response.ok(boards).build();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response inviteMember(long boardId, long memberId, long teamLeaderId) {
		
		Board board = entityManager.find(Board.class, boardId);
		if (board == null) {
			messageClient.sendMessage("Board not found");
			return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
		}
		User leader = entityManager.find(User.class, teamLeaderId);
		if (leader == null) {
			messageClient.sendMessage("Leader not found");
			return Response.status(Response.Status.NOT_FOUND).entity("Leader not found").build();
			
		}
		if (board.getTeamLeader() != leader ) {
			messageClient.sendMessage("User is not a team leader of the board");
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a team leader of the board")
					.build();
		}
		User member = entityManager.find(User.class, memberId);
		if (member == null) {
			messageClient.sendMessage("User not found");
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		
		if (board.getMembersIds().contains(memberId)) {
			messageClient.sendMessage("User is already a member");
			return Response.status(Response.Status.CONFLICT).entity("User is already a member").build();
		}
		try {
		board.addContributer(member);
		entityManager.merge(board);
		messageClient.sendMessage("User invited to board: " + member.getUsername());
		return Response.status(Response.Status.CREATED).entity("User invited successfully").build();
	} catch (Exception e) {
		e.printStackTrace();
		messageClient.sendMessage("Error inviting user");
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error inviting user").build();
	}
	}

	// get the board by team leader id
	public Response getMyBoardsAsLeader(long teamLeaderId) {
		try {
		List<Board> boards = entityManager
				.createQuery("SELECT b FROM Board b WHERE b.teamLeader.userId = :teamLeaderId", Board.class)
				.setParameter("teamLeaderId", teamLeaderId).getResultList();
		messageClient.sendMessage("Boards retrieved");
		return Response.ok(boards).build();
	} catch (Exception e) {
		e.printStackTrace();
		messageClient.sendMessage("Error getting boards");
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
				messageClient.sendMessage("Board deleted " + board.getName());
				return Response.status(Response.Status.OK).entity("Board deleted successfully").build();
			}
			messageClient.sendMessage("User is not a team leader of the board");
			return Response.status(Response.Status.CONFLICT).entity("User is not a team leader of the board").build();
		}
		messageClient.sendMessage("Board not found");
		return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();

	}
	
	public boolean isMemberOfBoard(Long boardId, Long userId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			if (board.getMembersIds().contains(userId)) {
				messageClient.sendMessage("User is a member of the board");
				return true;
			} else if (board.getTeamLeader().getUserId() == userId) {
				messageClient.sendMessage("User is a team leader of the board");
				return true;
			}
		}
		messageClient.sendMessage("User is not a member of the board");
		return false;
	}

	//get contributers
	public Response getContributers(long boardId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board != null) {
			Set<User> contributors = board.getContributors();
			messageClient.sendMessage("Contributors retrieved");
			return Response.ok(contributors).build();
		}
		messageClient.sendMessage("Contributors not retrieved");
		return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
	}

}
