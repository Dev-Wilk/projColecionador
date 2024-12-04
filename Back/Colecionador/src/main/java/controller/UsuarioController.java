package controller;

import java.io.InputStream;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.bo.UsuarioBO;
import model.dto.UsuarioDTO;
import model.vo.UsuarioVO;

@Path("/usuario")
public class UsuarioController {

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioVO cadastrarUsuarioController(InputStream usuarioInputStream) throws Exception {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.cadastrarUsuarioBO(usuarioInputStream);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsuarioVO logarUsuarioController(InputStream usuarioInputStream) throws Exception {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.logarUsuarioBO(usuarioInputStream);
    }
    

    @GET
    @Path("/listar")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response consultarTodosUsuariosController() {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.consultarTodosUsuariosBO();
    }

    @GET
    @Path("/pesquisar/{idusuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response consultarUsuarioController(@PathParam("idusuario") int idUsuario) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.consultarUsuarioBO(idUsuario);
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarUsuarioController(InputStream usuarioInputStream) throws Exception {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.atualizarUsuarioBO(usuarioInputStream);
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
