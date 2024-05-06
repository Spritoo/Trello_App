package service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import messagingSystem.client;
import model.Board;
import model.Card;
import model.ListofCards;
import model.User;

@Stateless
public class ListService {
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private client messageClient; 

	public Response createList(String listName, long boardId, long teamLeaderId) {
        // check if board exists
        Board board = entityManager.find(Board.class, boardId);
        if (board == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
        }
        // check if user exists
        User user = entityManager.find(User.class, teamLeaderId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
        // check if user is a teamLeader of the board
        if (!board.getTeamLeader().equals(user)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a team leader of the board")
                    .build();
        }
        ListofCards list = new ListofCards();
        list.setName(listName);
        list.setBoard(board);
        entityManager.persist(list);
        messageClient.sendMessage("List created " + listName + " in board " + board.getName());
        return Response.ok(list).build();
	}
	


	// delete list from board using user id
	public Response deleteList(Long listId, Long teamLeaderId) {
		// check if list exists
		ListofCards list = entityManager.find(ListofCards.class, listId);
		if (list == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("List not found").build();
		}
		// check if user exists
		User user = entityManager.find(User.class, teamLeaderId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		// check if user is a teamLeader of the board
		Board board = list.getBoard();
		if (!board.getTeamLeader().equals(user)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a team leader of the board")
					.build();
		}
		entityManager.remove(list);
		entityManager.find(Board.class, board.getBoardId()).getLists().remove(list);
		entityManager.flush();
		messageClient.sendMessage("List deleted " + list.getName());
		return Response.ok("List deleted successfully").build();
	}
	
	public Response getLists(Long boardId, Long userId) {
		Board board = entityManager.find(Board.class, boardId);
		if (board == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
		}
		User user = entityManager.find(User.class, userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
		}
		Boolean isMember = board.getContributors().contains(user);
		if (!isMember && !board.getTeamLeader().equals(user)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("User is not a member of the board").build();
		}
		Set<ListofCards> lists = board.getLists();
		messageClient.sendMessage("Lists retrieved");
		return Response.ok(lists).build();
	}

}
