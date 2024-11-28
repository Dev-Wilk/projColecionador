package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.vo.UsuarioVO;

public class UsuarioDAO {
	
	public boolean verificarCadastroUsuarioBancoDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet resultado = null;
        boolean retorno = false;
        
        String query = "SELECT idUsuario FROM usuario WHERE email = '" + usuarioVO.getEmail() + "' ";
       
        try {
        	
            resultado = stmt.executeQuery(query);
            if (resultado.next()) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao executar a query do método verificarCadastroUsuarioBancoDAO.");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return retorno;
    }


    public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
    	String query = "INSERT INTO usuario (nome, email, login, senha, datacadastro) VALUES (?, ?, ?, ?, ?)";
    	Connection conn = Banco.getConnection();
    	PreparedStatement pstmt =  Banco.getPreparedStatement(conn, query);
    

    try {
        pstmt.setString(1, usuarioVO.getNome());
        pstmt.setString(2, usuarioVO.getEmail());
        pstmt.setString(3, usuarioVO.getLogin());
        pstmt.setString(4, usuarioVO.getSenha());
        pstmt.setObject(5, usuarioVO.getDataCadastro());
        pstmt.execute();

        ResultSet resultado = pstmt.getGeneratedKeys();
        if (resultado.next()) {
            usuarioVO.setIdUsuario(resultado.getInt(1));
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método cadastrarUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        
        Banco.closeStatement(pstmt);
        Banco.closeConnection(conn);
    }

    return usuarioVO;
}


public ArrayList<UsuarioVO> consultarTodosUsuariosDAO() {
    Connection conn = Banco.getConnection();
    Statement stmt = Banco.getStatement(conn);
    ResultSet resultado = null;
    ArrayList<UsuarioVO> listaUsuarios = new ArrayList<>();
    String query = "SELECT idusuario, nome, email, login, senha, datacadastro FROM usuario";
    
    try {
        resultado = stmt.executeQuery(query);

        while (resultado.next()) {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setIdUsuario(Integer.parseInt(resultado.getString(1)));
            usuario.setNome(resultado.getString(2));
            usuario.setEmail(resultado.getString(3));
            usuario.setLogin(resultado.getString(4));
            usuario.setSenha(resultado.getString(5));
            usuario.setDataCadastro(resultado.getObject("datacadastro", LocalDate.class));
            listaUsuarios.add(usuario);
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método consultarTodosUsuariosDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeResultSet(resultado);
        Banco.closeStatement(stmt);
        Banco.closeConnection(conn);
    }

    return listaUsuarios;
}


public UsuarioVO consultarUsuarioDAO(int idUsuario) {
    Connection conn = Banco.getConnection();
    Statement stmt = Banco.getStatement(conn);
    ResultSet resultado = null;

    String query = "SELECT nome, email, login, senha, datacadastro FROM usuario WHERE idusuario = " + idUsuario;

    UsuarioVO usuario = new UsuarioVO();
    try {
        resultado = stmt.executeQuery(query);

        while (resultado.next()) {
            usuario.setNome(resultado.getString("nome"));
            usuario.setEmail(resultado.getString("email"));
            usuario.setLogin(resultado.getString("login"));
            usuario.setSenha(resultado.getString("senha"));
            usuario.setDataCadastro(resultado.getObject("datacadastro", LocalDate.class));
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



public boolean verificarCadastroUsuarioPorIDDAO(UsuarioVO usuarioVO) {
	  Connection conn = Banco.getConnection();
      Statement stmt = Banco.getStatement(conn);
      ResultSet resultado = null;
      boolean retorno = false;

    String query = "SELECT idusuario FROM usuario WHERE idusuario = " + usuarioVO.getIdUsuario();

    try {
        resultado = stmt.executeQuery(query);
        if (resultado.next()) {
            retorno = true;
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método verificarCadastroUsuarioPorID!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeResultSet(resultado);
        Banco.closeStatement(stmt);
        Banco.closeConnection(conn);
    }
    return retorno;
}


public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
    boolean retorno = false;
    Connection conn = Banco.getConnection();
    PreparedStatement pstmt = null;

    String query = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE idUsuario = ?";
    
    

    try {
        pstmt = (PreparedStatement) Banco.getPreparedStatement(conn, query);
        pstmt.setString(1, usuarioVO.getNome());
        pstmt.setString(2, usuarioVO.getEmail());
        pstmt.setString(3, usuarioVO.getLogin());
        pstmt.setString(4, usuarioVO.getSenha());
        pstmt.setInt(5, usuarioVO.getIdUsuario());

        pstmt.executeUpdate();
        retorno = true;
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método atualizarUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeStatement(pstmt);
        Banco.closeConnection(conn);
    }

    return retorno;
}


public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
	Connection conn = Banco.getConnection();
    PreparedStatement pstmt = null;
    boolean retorno = false;

    String query = "UPDATE  usuario set dataExpiracao = ?  WHERE idusuario = ?";
    pstmt = Banco.getPreparedStatement(conn, query);
    try {
    	pstmt.setObject(1, LocalDate.now());
    	pstmt.setInt(2, usuarioVO.getIdUsuario());
    	
        if (pstmt.executeUpdate() == 1) {
            retorno = true;
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método excluirUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeStatement(pstmt);
        Banco.closeConnection(conn);
    }
    return retorno;
}


public UsuarioVO logarUsuarioDAO(UsuarioVO usuarioVO) {
    String query = "SELECT idUsuario FROM usuario WHERE login = ? AND senha = ? AND dataExpiracao is null";

    try (Connection conn = Banco.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, usuarioVO.getLogin());
        pstmt.setNString(2, usuarioVO.getSenha());

        try (ResultSet resultado = pstmt.executeQuery()) {
            if (resultado.next()) {
                usuarioVO.setIdUsuario(resultado.getInt(1));
            }
        }

    } catch (SQLException erro) {
        System.err.println("Erro ao logar usuário: " + erro.getMessage()); 
    }

    return usuarioVO; 
}


}
