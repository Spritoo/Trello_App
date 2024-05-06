package service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import messagingSystem.client;
import model.Board;
import model.ListofCards;
import model.Sprint;

@Stateless
public class SprintService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private client messageClient;
	
	public Response endSprint(long bloardId) {
		//Check board done list make a new sprint appending to report string the done tasks with their importance and deleting them from done list
		//then check to do , in progress lists and append them to the report string that theyre not completed
		Board board = entityManager.find(Board.class, bloardId);
		if (board == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Board not found").build();
		}
		Sprint sprint = new Sprint();
		sprint.setBoard(board);
		try {
		sprint.setBoard(entityManager.find(Board.class, bloardId));
		sprint.setReport("Sprint report for board: "+ board.getName() + "\n\n" );
		sprint.appendReport("Done tasks:\n");
		
		try {
		board.DoneList().getCards().forEach(card -> {
			sprint.appendReport(card.ParseCard() + "\n");
			entityManager.remove(card);
			entityManager.find(ListofCards.class, board.DoneList().getListId()).getCards().remove(card);
			entityManager.flush();
		});
		sprint.appendReport("\n\nTasks in progress:");
		board.InProgressList().getCards().forEach(card -> {
			sprint.appendReport("\n" + card.ParseCard() );
		});
		
		sprint.appendReport("\nTasks to do:\n");
		board.ToDoList().getCards().forEach(card -> {
		sprint.appendReport(card.ParseCard() + "\n");
		});
		messageClient.sendMessage("Sprint ended for board " + board.getName());
		entityManager.persist(sprint);
	} catch (Exception e) {
		e.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error finding lists").build();
	}
	} catch (Exception e) {
		e.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error finding board").build();
	}
		
		return Response.status(Response.Status.CREATED).entity("Sprint ended successfully with id : " + sprint.getId()).build();
		
		
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
