package controller;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.bo.MoedaBO;
import model.vo.MoedaVO;

@Path("/moeda")
public class MoedaController {
        
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public MoedaVO cadastraMoedaController(@FormDataParam("file") InputStream fileInputStream, 
    @FormDataParam("file") FormDataContentDisposition fileMetaData, 
    @FormDataParam("moedaVO") InputStream moedaInputStream) throws Exception {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.registerCoinBO(moedaInputStream, fileInputStream, fileMetaData);
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarMoedaController(@FormDataParam("file") InputStream fileInputStream, 
    @FormDataParam("file") FormDataContentDisposition fileMetaData, 
    @FormDataParam("moedaVO") InputStream moedaInputStream) throws Exception {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.editCoinBO(moedaInputStream, fileInputStream, fileMetaData);
    }

    @GET
    @Path("/listar/{idUsuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response listarMoedasController(@PathParam("idUsuario") int idUsuario) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.listCoinBO(idUsuario);
    }

    @DELETE
    @Path("/excluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirMoedaController(MoedaVO moedaVO) {
        MoedaBO moedaBO = new MoedaBO();
        boolean excluido = moedaBO.deleteCoinBO(moedaVO);

        if (excluido) {
            return Response.ok().entity("Moeda excluída com sucesso.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao excluir moeda.").build();
        }
    }
}
