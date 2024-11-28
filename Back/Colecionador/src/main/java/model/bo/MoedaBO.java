package model.bo;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.dao.MoedaDAO;
import model.vo.MoedaVO;




public class MoedaBO {

	private byte[] converterByteParaArray (InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read = 0;
        byte[] dados = new byte[1024];
        while ((read = inputStream.read(dados, 0, dados.length)) != -1) {
            buffer.write(dados, 0, read);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public MoedaVO registerCoinBO(InputStream moedaInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) throws Exception {
        MoedaDAO moedaDAO = new MoedaDAO();
        MoedaVO moedaVO = null;
        try {
            byte[] arquivo = this.converterByteParaArray(fileInputStream);
            String moedaJSON = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();

            moedaVO = objectMapper.readValue(moedaJSON, MoedaVO.class);
            moedaVO.setImagem(arquivo);

            if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
                System.out.println("Essa moeda já existe no Banco de dados.");
            } else {
                moedaVO = moedaDAO.cadastrarMoedaDAO(moedaVO);
            }
        } catch (FileNotFoundException erro) {
            System.out.println("deu merda nessa porra BO !" + erro);
        } catch (IOException erro) {
            erro.printStackTrace();
        }
        return moedaVO;
    }
    //
    public boolean editCoinBO(InputStream moedaInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
        boolean resultado = false;
        MoedaDAO moedaDAO = new MoedaDAO();
        MoedaVO moedaVO = null;

        try {
            byte[] arquivo = null;
            if (fileInputStream != null) {
                arquivo = this.converterByteParaArray(fileInputStream);
            }

            String moedaJSON = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            moedaVO = objectMapper.readValue(moedaJSON, MoedaVO.class);

            // Verifica se o arquivo existe antes de tentar usá-lo
            if (arquivo != null && arquivo.length > 0) {
                moedaVO.setImagem(arquivo);
            }

            if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
                resultado = moedaDAO.editCoinDAO(moedaVO);
            } else {
                System.out.println("Essa moeda não existe no Banco de Dados!");
            }
        } catch (FileNotFoundException erro) {
            System.out.println(erro);
        } catch (IOException erro) {
            erro.printStackTrace();
        }
        return resultado;
    }

    public boolean deleteCoinBO(MoedaVO moedaVO) {
        boolean resultado = false;
        MoedaDAO moedaDAO = new MoedaDAO();

        if (moedaDAO.verificarCadastroMoedaDAO(moedaVO)) {
            resultado = moedaDAO.excluirMoedaDAO(moedaVO);
            System.out.println("moeda deletada com sucesso!");
        } else {
            System.out.println("Falha na tentativa de deletar a moeda!");
        } 
        return resultado;
    }

    public Response listCoinBO(int idUsuario) {
        MoedaDAO moedaDAO = new MoedaDAO();
        ArrayList<MoedaVO> listaMoedasVO = moedaDAO.consultarMoedaDAO(idUsuario);
        if (listaMoedasVO.isEmpty()) {
            System.out.println("Não há nenhuma moeda na lista!");
            return Response.status(Response.Status.NO_CONTENT).entity("Nenhuma moeda encontrada").build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Retorna a lista de moedas como um JSON simples
            String moedasJson = objectMapper.writeValueAsString(listaMoedasVO);
            return Response.ok(moedasJson, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar a resposta.").build();
        }
    }
    
}