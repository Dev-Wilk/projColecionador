package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.bo.UsuarioBO;
import model.vo.UsuarioVO;

@Path("/usuario")
public class UsuarioController {
    
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO cadastrarUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.registerUserBO(usuarioVO);
	}
	
	@POST
	@Path("/logar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO logarUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.loginUserBO(usuarioVO);
	}

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarUsuarioController(UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.editUserBO(usuarioVO);
    }

    @DELETE
    @Path("/excluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirUsuarioController (UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.deleteUserBO(usuarioVO);
    }
}
