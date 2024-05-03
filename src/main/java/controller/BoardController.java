package controller;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.Board;
import model.ListofCards;
import model.User;
import service.BoardService;

@Path("/board")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

	@EJB
	private BoardService boardService;

	@POST
	@Path("/create/{userId}")
	public Response createBoard(Board board, @PathParam("userId") Long userId) {
		Response response = boardService.createBoard(board, userId);
		return response;
	}

	@PUT
	@Path("/inviteMember/{userId}/{boardId}/{teamLeaderId}")
	public Response inviteUser(@PathParam("userId") Long userId, @PathParam("boardId") Long boardId,
			@PathParam("teamLeaderId") Long teamLeaderId) {
		Response response = boardService.inviteMember(boardId, userId, teamLeaderId);
		return response;
	}

	@GET
	@Path("/getAllBoards")
	public Response getBoards() {
		Response response = boardService.getBoards();
		return response;
	}

	@GET
	@Path("/getBoardsByTeamLeader/{teamLeaderId}")
	public Response getBoardsByTeamLeader(@PathParam("teamLeaderId") Long teamLeaderId) {
		Response response = boardService.getBoardByTeamLeaderId(teamLeaderId);
		return response;
	}

	@DELETE
	@Path("/deleteBoard/{teamLeaderId}/{boardId}")
	public Response deleteBoard(@PathParam("teamLeaderId") Long teamLeaderId, @PathParam("boardId") Long boardId) {
		Response response = boardService.deleteBoardByTeamLeaderId(teamLeaderId, boardId);
		return response;
	}

}
