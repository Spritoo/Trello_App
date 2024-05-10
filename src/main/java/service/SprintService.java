package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import messagingSystem.client;
import model.Board;
import model.Card;
import model.ListofCards;
import model.Sprint;

@Stateless
public class SprintService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private client messageClient;
	
	public Response endSprint(long boardId) {
	    Board board = entityManager.find(Board.class, boardId);
	    if (board == null) {
	        return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
	    }

	    Sprint sprint = new Sprint();
	    sprint.setBoard(board);

	    try {
	        StringBuilder reportBuilder = new StringBuilder("Sprint report for board: ").append(board.getName()).append("\n\n");

	        // Process done tasks
	        reportBuilder.append("Done tasks:\n");
	        List<Card> doneTasks = new ArrayList<>(board.DoneList().getCards());
	        for (Card card : doneTasks) {
	            reportBuilder.append(card.ParseCard()).append("\n");
	            entityManager.remove(card);
	        }

	        // Process tasks in progress
	        reportBuilder.append("\nTasks in progress:\n");
	        for (Card card : board.InProgressList().getCards()) {
	            reportBuilder.append(card.ParseCard()).append("\n");
	        }

	        // Process tasks to do
	        reportBuilder.append("\nTasks to do:\n");
	        for (Card card : board.ToDoList().getCards()) {
	            reportBuilder.append(card.ParseCard()).append("\n");
	        }

	        sprint.setReport(reportBuilder.toString());
	        messageClient.sendMessage("Sprint ended for board " + board.getName());
	        entityManager.persist(sprint);

	        return Response.status(Response.Status.CREATED).entity("Sprint ended successfully with id : " + sprint.getId()).build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error ending sprint").build();
	    }
	}

	
	public Response getSprint(long sprintId) {
		Sprint sprint = entityManager.find(Sprint.class, sprintId);
		if (sprint == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Sprint not found").build();
		}
		messageClient.sendMessage("Sprint retrieved with id " + sprint.getId());
		return Response.ok(sprint).build();
	}
	
}
