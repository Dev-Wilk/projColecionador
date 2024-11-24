package controller;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
    public UsuarioVO cadastrarUsuario(InputStream jsonInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
        UsuarioBO usuarioBO = new UsuarioBO();
        return usuarioBO.cadastrarUsuarioBO(jsonInputStream, fileInputStream, fileMetaData);
    }
	
    @GET
     @Path("/listar")
     @Produces(MediaType.MULTIPART_FORM_DATA)
     public Response consultarTodasPessoasController() {
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




}
