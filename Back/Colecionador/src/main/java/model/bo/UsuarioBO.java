package model.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import model.dao.UsuarioDAO;
import model.dto.UsuarioDTO;
import model.vo.UsuarioVO;

public class UsuarioBO {

	private byte[] converterByteParaArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int read = 0;
		byte[] dados = new byte[1024];
		while ((read = inputStream.read(dados, 0, dados.length)) != -1) {
			buffer.write(dados, 0, read);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public UsuarioVO cadastrarUsuarioBO(InputStream usuarioInputStream) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = null;
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioInputStream, UsuarioVO.class);

			if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
				System.out.println("Usuário já cadastrado no banco de dados!");
			} else {
				usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
			}
		} catch (FileNotFoundException erro) {
			System.out.println(erro);
		} catch (IOException e) {
			e.printStackTrace();
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

		MultiPart multiPart = new FormDataMultiPart();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {
			for (UsuarioVO usuarioVO : listaUsuariosVO) {
				// Adiciona o JSON dos usuários
				String usuarioJson = objectMapper.writeValueAsString(usuarioVO);
				multiPart.bodyPart(new StreamDataBodyPart("usuarioVO", new ByteArrayInputStream(usuarioJson.getBytes()),
						usuarioVO.getIdUsuario() + "-usuario.json"));

			}
			return Response.ok(multiPart).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao processar a resposta multipart.").build();
		}
	}

	public Response consultarUsuarioBO(int idUsuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = usuarioDAO.consultarUsuarioDAO(idUsuario);

		if (usuarioVO == null) {
			System.out.println("Objeto Usuário está vazio.");
			return Response.status(Response.Status.NO_CONTENT).entity("Usuário não encontrado.").build();
		}

		MultiPart multiPart = new FormDataMultiPart();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {

			// Adiciona o JSON do usuário
			String usuarioJson = objectMapper.writeValueAsString(usuarioVO);
			multiPart.bodyPart(new StreamDataBodyPart("usuarioVO", new ByteArrayInputStream(usuarioJson.getBytes()),
					usuarioVO.getIdUsuario() + "-usuario.json"));

			return Response.ok(multiPart).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao processar a resposta multipart.").build();
		}
	}

	public Boolean atualizarUsuarioBO(InputStream usuarioInputStream) {
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
		} catch (IOException erro) {
			erro.printStackTrace();
		}
		return resultado;
	}

	public Boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarCadastroUsuarioPorIDDAO(usuarioVO)) {
			resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
		} else {
			System.out.println("\nUsuário não existe na base de dados.");
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
