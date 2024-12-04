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
import model.bo.MoedaBO;
import model.dto.MoedaDTO;
import model.vo.MoedaVO;

@Path("/moeda")
public class MoedaController {

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public MoedaVO cadastrarMoedaController(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("moedaVO") InputStream moedaInputStream) throws Exception {	
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.cadastrarMoedaBO(moedaInputStream, fileInputStream, fileMetaData);
    }

    @GET
    @Path("/listar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodasMoedasController(@PathParam("idUsuario") int idusuario) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.consultarTodasMoedasBO(idusuario);
    }

    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean atualizarMoedaController(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("moedaVO") InputStream moedaInputStream) throws Exception {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.atualizarMoedaBO(moedaInputStream, fileInputStream, fileMetaData);
    }

    @DELETE
    @Path("/excluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirMoedaController(MoedaVO moedaVO) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.excluirMoedaBO(moedaVO);
    }


    @GET
    @Path("/imagemMoeda/{idMoeda}")
    @Produces("image/jpeg")
    public Response consultarImagemMoedaController(@PathParam("idMoeda") int idMoeda) {
        MoedaBO moedaBO = new MoedaBO();
        return moedaBO.consultarImagemMoedaBO(idMoeda);
    }
}
