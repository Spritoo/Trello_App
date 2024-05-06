package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import model.Board;
import model.ListofCards;
import model.Sprint;

@Stateless
public class SprintService {
	@PersistenceContext
	private EntityManager entityManager;
	
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
		sprint.setReport("Sprint report for board: "+ board.getName() );
		sprint.appendReport("Done tasks: ");
		
		try {
		board.getDoneList().getCards().forEach(card -> {
			sprint.appendReport(card.ParseCard());
			entityManager.remove(card);
			entityManager.find(ListofCards.class, board.getDoneList().getListId()).getCards().remove(card);
			entityManager.flush();
		});
		
		sprint.appendReport("Tasks in progress: ");
		board.getInProgressList().getCards().forEach(card -> {
			sprint.appendReport(card.ParseCard() );
		});
		
		sprint.appendReport("Tasks to do:");
		board.getToDoList().getCards().forEach(card -> {
			sprint.appendReport(card.ParseCard());
		});
		
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
		return Response.ok(sprint).build();
	}
	
}
