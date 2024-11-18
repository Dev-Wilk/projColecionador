package model.bo;

import model.dao.Banco;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import jakarta.ws.rs.core.Response;

public class UsuarioBO {


    private byte[] converterByteParaArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] dados = new byte[1024];
        int read;
        while ((read = inputStream.read(dados, 0, dados.length)) != -1) {
            buffer.write(dados, 0, read);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public UsuarioVO cadastrarUsuarioBO(InputStream jsonInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioVO usuarioVO = null;

        try {
           
            // Lê o conteúdo do JSON
            String json = new String(converterByteParaArray(jsonInputStream), StandardCharsets.UTF_8);

            // Converte o JSON em um objeto Java
            ObjectMapper objectMapper = new ObjectMapper();
            usuarioVO = objectMapper.readValue(json, UsuarioVO.class);
            

            // Verifica se a pessoa já está cadastrada
            if (pessoaDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
                System.out.println("Pessoa já cadastrada no banco de dados!");
                return null;
            } else {
                pessoaDAO.cadastrarUsuarioBancoDAO(usuarioVO);
                System.out.println("Pessoa cadastrada com sucesso!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao processar os dados: " + e.getMessage());
        }

        return usuarioVO;
    }


    


public Response consultarTodosUsuariosBO() {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    ArrayList<UsuarioVO> listaUsuariosVO = usuarioDAO.consultarTodosUsuariosDAO();

    if (listaUsuariosVO.isEmpty()) {
        System.out.println("\nLista de Usuários está vazia.");
        return Response.status(Response.Status.NO_CONTENT).entity("Nenhum usuário encontrado.").build();
    }

    MultiPart multiPart = new FormDataMultiPart();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    try {
        for (UsuarioVO usuarioVO : listaUsuariosVO) {
           

            // Adiciona o JSON dos usuários
            String usuarioJson = objectMapper.writeValueAsString(usuarioVO);
            multiPart.bodyPart(new StreamDataBodyPart("usuario", new ByteArrayInputStream(usuarioJson.getBytes()),
                    usuarioVO.getIdusuario() + "-usuario.json"));
            
            }
        
        return Response.ok(multiPart).build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar a resposta multipart.").build();
    }
}



public UsuarioVO consultarUsuarioBO(int idUsuario) {
    Connection conn = (Connection) Banco.getConnection();
    Statement stmt = (Statement) Banco.getStatement(conn);
    ResultSet resultado = null;

    String query = "SELECT idusuario, nome, email, login, senha, datacadastro, datacadastro FROM usuario WHERE idUsuario = " + idUsuario;

    UsuarioVO usuario = new UsuarioVO();
    try {
        resultado = stmt.executeQuery(query);

        while (resultado.next()) {
            usuario.setIdusuario(Integer.parseInt(resultado.getString(1)));
            usuario.setNome(resultado.getString(2));
            usuario.setEmail(resultado.getString(3));
            usuario.setLogin(resultado.getString(4));
            usuario.setSenha(resultado.getString(5));
            usuario.setDataCadastro(LocalDate.parse(resultado.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método consultarUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeResultSet(resultado);
        Banco.closeStatement(stmt);
        Banco.closeConnection(conn);
    }

    return usuario;
}


public Boolean atualizarUsuarioBO(InputStream usuarioInputStream, InputStream fileInputStream, FormDataContentDisposition fileMetaData) {
    boolean resultado = false;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    UsuarioVO usuarioVO = null;

    try {
       

        // Lê o conteúdo do JSON
        String usuarioJSON = new String(this.converterByteParaArray(usuarioInputStream), StandardCharsets.UTF_8);

        // Converte o JSON em um objeto Java
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        usuarioVO = objectMapper.readValue(usuarioJSON, UsuarioVO.class);
        

        if (usuarioDAO.verificarCadastroUsuarioPorIDDAO(usuarioVO)) {
            resultado = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
        } else {
            System.out.println("Usuário não consta na base de dados!");
        }

    } catch (FileNotFoundException erro) {
        System.out.println(erro);
    } catch (IOException e) {
        e.printStackTrace();
    }

    return resultado;
}



}
