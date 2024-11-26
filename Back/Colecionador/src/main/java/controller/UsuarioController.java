package controller;

import java.io.InputStream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import model.bo.UsuarioBO;
import model.vo.UsuarioVO;


@Path("/usuario")
public class UsuarioController {
	
	@POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioVO cadastrarUsuario(InputStream jsonInputStream) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.cadastrarUsuarioBO(jsonInputStream);
    }
	
    @GET
     @Path("/listar")
     @Produces(MediaType.APPLICATION_JSON)
     public Response consultarTodosUsuariosController() {
     UsuarioBO usuarioBO = new UsuarioBO();
     return usuarioBO.consultarTodosUsuariosBO();
     }

    @GET
     @Path("/pesquisar/{idusuario}")
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.MULTIPART_FORM_DATA)
     public UsuarioVO consultarPessoaController(@PathParam("idusuario") int idusuario) {
     UsuarioBO usuarioBO = new UsuarioBO();
     return usuarioBO.consultarUsuarioBO(idusuario);
     }

   @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(UsuarioVO usuarioVO) {
        UsuarioBO usuarioBO = new UsuarioBO();
        boolean atualizado = usuarioBO.atualizarUsuarioBO(usuarioVO);
        
        if (atualizado) {
            return Response.ok(usuarioVO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado ou atualização falhou.").build();
        }
    }


   
   @DELETE
   	@Path("/excluir")
   	@Consumes(MediaType.APPLICATION_JSON)
   	@Produces(MediaType.APPLICATION_JSON)
   	public Boolean excluirUsuarioController(UsuarioVO usuarioVO) {
   		UsuarioBO usuarioBO = new UsuarioBO();
   		return usuarioBO.excluirUsuarioBO(usuarioVO);
   	}

}
