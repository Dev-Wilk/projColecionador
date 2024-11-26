package model.bo;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import model.dao.Banco;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

public class UsuarioBO {
	


    public UsuarioVO cadastrarUsuarioBO(InputStream jsonInputStream) {
    	 UsuarioDAO usuarioDAO = new UsuarioDAO();
         UsuarioVO usuarioVO = null;

         try {
             // Converte o InputStream para String
             byte[] bytes = jsonInputStream.readAllBytes();
             String json = new String(bytes, StandardCharsets.UTF_8);

             // Converte o JSON para objeto UsuarioVO
             ObjectMapper objectMapper = new ObjectMapper();
             objectMapper.findAndRegisterModules(); // Necessário para LocalDate
             usuarioVO = objectMapper.readValue(json, UsuarioVO.class);
             
             // Define a data de cadastro se não foi informada
             if (usuarioVO.getDataCadastro() == null) {
                 usuarioVO.setDataCadastro(LocalDate.now());
             }

             // Verifica se usuário já existe
             if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
                 System.out.println("Usuário já cadastrado no banco de dados!");
                 return null;
             }

             // Realiza o cadastro
             usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
             System.out.println("Usuário cadastrado com sucesso!");
             
         } catch (IOException e) {
             System.out.println("Erro ao processar dados do usuário: " + e.getMessage());
             e.printStackTrace();
             return null;
         }

         return usuarioVO;
     
    }


    


public Response consultarTodosUsuariosBO() {
	    UsuarioDAO usuarioDAO = new UsuarioDAO();
	    ArrayList<UsuarioVO> listaUsuariosVO = usuarioDAO.consultarTodosUsuariosDAO();

	    if (listaUsuariosVO.isEmpty()) {
	        System.out.println("Lista de Usuários está vazia.");
	        return Response.status(Response.Status.NO_CONTENT).entity("Nenhum usuário encontrado.").build();
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.findAndRegisterModules();

	    try {
	        String usuarioJson = objectMapper.writeValueAsString(listaUsuariosVO);
	        return Response.ok(usuarioJson).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar a resposta multipart.").build();
	    }
	
}



public UsuarioVO consultarUsuarioBO(int idUsuario) {
    Connection conn = Banco.getConnection();
    Statement stmt = Banco.getStatement(conn);
    ResultSet resultado = null;

    String query = "SELECT idusuario, nome, email, login, senha, datacadastro, datacadastro FROM usuario WHERE idusuario =" + idUsuario;

    UsuarioVO usuario = new UsuarioVO();
    try {
        resultado = stmt.executeQuery(query);

        while (resultado.next()) {
            usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
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


public boolean atualizarUsuarioBO(UsuarioVO usuarioVO) {
    try {
        // Validações básicas
        if (usuarioVO == null) {
            System.out.println("Usuário não pode ser nulo");
            return false;
        }
        
        if (usuarioVO.getIdUsuario() <= 0) {
            System.out.println("ID do usuário inválido");
            return false;
        }
        
        // Verificar se o usuário existe e não está expirado
        UsuarioVO usuarioExistente = consultarUsuarioBO(usuarioVO.getIdUsuario());
        if (usuarioExistente == null || usuarioExistente.getIdUsuario() == 0) {
            System.out.println("Usuário não encontrado ou já expirado");
            return false;
        }
        
        // Realizar atualização
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.atualizarUsuarioDAO(usuarioVO);
        
    } catch (Exception e) {
        System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        return false;
    }
}



	public boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarCadastroUsuarioPorIDDAO(usuarioVO)) {
			resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
		} else {
			System.out.println("\nUsuário não existe.");
		}
		return resultado;
	}



	public UsuarioVO logarUsuarioBO(InputStream usuarioInputStream) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = null;
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioInputStream, UsuarioVO.class);

			usuarioVO = usuarioDAO.logarUsuarioDAO(usuarioVO);

		} catch (FileNotFoundException erro) {
		System.out.println(erro);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuarioVO;
}
}
