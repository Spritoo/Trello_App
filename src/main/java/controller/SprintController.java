package controller;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.SprintService;

@Path("/sprint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SprintController {
	
	@EJB
	private SprintService sprintService;
	
	@POST
	@Path("/end")
	public Response endSprint(@QueryParam("boardId") long boardId) {
		return sprintService.endSprint(boardId);
	}
	
	@GET
	@Path("/get")
	public Response getSprint(@QueryParam("sprintId") long sprintId) {
		return sprintService.getSprint(sprintId);
	}
}
