package controller;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.Board;
import model.ListofCards;
import model.User;
import service.BoardService;
import service.ListService;

@Path("/board")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {

	@EJB
	private BoardService boardService;
	
	@EJB
	private ListService listService;

	@POST
	@Path("/create")
	public Response createBoard(@QueryParam("boardName") String board, @QueryParam("userId") Long userId) {
		Response response = boardService.createBoard(board, userId);
		
		return response;
	}

	@PUT
    @Path("/inviteMember")
	public Response inviteUser(@QueryParam("userId") Long userId, @QueryParam("boardId") Long boardId, @QueryParam("teamLeaderId") Long teamLeaderId) {
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
	@Path("/getBoardsAsLeader")
	public Response getBoardsByTeamLeader(@QueryParam("teamLeaderId") long teamLeaderId) {
		Response response = boardService.getMyBoardsAsLeader(teamLeaderId);
		return response;
	}

	@DELETE
	@Path("/deleteBoard")
	public Response deleteBoard(@QueryParam("teamLeaderId") long teamLeaderId, @QueryParam("boardId") long boardId) {
		Response response = boardService.deleteBoardByTeamLeaderId(teamLeaderId, boardId);
		return response;
	}
	
	//getcontributers of board
	@GET
	@Path("/getContributers")
	public Response getContributers(@QueryParam("boardId") long boardId) {
		Response response = boardService.getContributers(boardId);
		return response;
	}

}
