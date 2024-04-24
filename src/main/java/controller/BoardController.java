package controller;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.Board;
import model.ListofCards;
import service.BoardService;

@Path("/board")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BoardController {
	
	@EJB
	private BoardService boardService;
	
	@POST
	@Path("/create")
	public Response createBoard(Board board) {
		String response = boardService.createBoard(board);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@POST
	@Path("/addList")
	public Response addListToBoard(Long boardId, ListofCards listofCards) {
		String response = boardService.addListToBoard(boardId, listofCards);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@POST
	@Path("/addMember")
	public Response addMemberToBoard(Long boardId, Long memberId) {
		String response = boardService.addMemberToBoard(boardId, memberId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@PUT
	@Path("/update")
	public Response updateBoard(Board board) {
		String response = boardService.updateBoard(board);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@PUT
	@Path("/updateTeamLeader")
	public Response updateTeamLeader(Long boardId, Long teamLeaderId) {
		String response = boardService.updateTeamLeader(boardId, teamLeaderId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	
	@DELETE
	@Path("/delete")
	public Response deleteBoard(Long boardId) {
		String response = boardService.deleteBoard(boardId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@DELETE
	@Path("/deleteList")
	public Response deleteList(Long boardId, Long listId) {
		String response = boardService.removeListFromBoard(boardId, listId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	@DELETE
	@Path("/deleteMember")
	public Response deleteMember(Long boardId, Long memberId) {
		String response = boardService.removeMemberFromBoard(boardId, memberId);
		return Response.status(Response.Status.CREATED).entity(response).build();
	}
	
	
	@GET
	@Path("/get/{id}")
	public Response getBoard(@PathParam("id") Long boardId) {
		Board board = boardService.getBoardById(boardId);
		if (board != null) {
			return Response.ok(board).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/getLists/{id}")
	public Response getLists(@PathParam("id") Long boardId) {
		return Response.ok(boardService.getListsOfBoard(boardId)).build();
	}
	
	@GET
	@Path("/getMembers/{id}")
	public Response getMembers(@PathParam("id") Long boardId) {
		return Response.ok(boardService.getMembersOfBoard(boardId)).build();
	}
	
}
