package model.bo;

import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

public class UsuarioBO {

    // Registrar user
    public UsuarioVO registerUserBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
                    System.out.println("Usuário já cadastrado");
        } else {
        usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
        }
        return usuarioVO;
    }

    // Fazer o login do user
    public UsuarioVO loginUserBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        return usuarioDAO.logarUsuarioDAO(usuarioVO);
    }

    // Editar User
    public boolean editUserBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
    
            if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
                resultado = usuarioDAO.editarUsuarioDAO(usuarioVO);
            } else {
                System.out.println("Usuário não consta na base de dados");
            } 
        return resultado;
    }

    // Delete user
    public boolean deleteUserBO(UsuarioVO usuarioVO) {
        boolean resultado = false;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        // Verifica se o usuário existe antes de deletar
        if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
            resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
            System.out.println("Usuário deletado com sucesso!");
        } else {
            System.out.println("Falha na tentativa de deletar o usuário!");
        }
        return resultado;
    }
}