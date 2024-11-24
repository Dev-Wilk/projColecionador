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
        PreparedStatement pstmt = null;
        ResultSet resultado = null;
        boolean retorno = false;
    
        String query = "SELECT idusuario FROM usuario WHERE email = ?";
    
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuarioVO.getEmail());
            resultado = pstmt.executeQuery();
    
            if (resultado.next()) {
                retorno = true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao executar a query do método verificarCadastroUsuarioBanco!");
            System.out.println("Erro: " + erro.getMessage());
        } finally {
            Banco.closeResultSet(resultado);
            Banco.closeStatement(pstmt);
            Banco.closeConnection(conn);
        }
    
        return retorno;
    }


    public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
    String query = "INSERT INTO pessoa (nome, email, login, senha, datacadastro) VALUES (?, ?, ?, ?, ?)";
    Connection conn = Banco.getConnection();
    PreparedStatement pstmt =  Banco.getPreparedStatement(conn, query);
    ResultSet resultado = null;

    try {
        pstmt.setString(1, usuarioVO.getNome());
        pstmt.setString(2, usuarioVO.getEmail());
        pstmt.setString(3, usuarioVO.getLogin());
        pstmt.setString(4, usuarioVO.getSenha());
        pstmt.setObject(5, usuarioVO.getDataCadastro());
        
        pstmt.execute();

        resultado = pstmt.getGeneratedKeys();
        if (resultado.next()) {
            usuarioVO.setIdusuario(resultado.getInt(1)); // Assumindo que o ID é chamado de idUsuario em UsuarioVO
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método cadastrarUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeResultSet(resultado);
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
    String query = "SELECT nome, email, login, senha, datacadastro FROM usuario";
    
    try {
        resultado = stmt.executeQuery(query);

        while (resultado.next()) {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setNome(resultado.getString("nome"));
            usuario.setEmail(resultado.getString("email"));
            usuario.setLogin(resultado.getString("login"));
            usuario.setSenha(resultado.getString("senha"));
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
    Connection conn =  Banco.getConnection();
    PreparedStatement pstmt = null;
    ResultSet resultado = null;
    boolean retorno = false;

    String query = "SELECT idusuario FROM usuario WHERE idusuario = ?";

    try {
        pstmt = (PreparedStatement) conn.prepareStatement(query);
        pstmt.setInt(1, usuarioVO.getIdusuario());
        resultado = pstmt.executeQuery();

        if (resultado.next()) {
            retorno = true;
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método verificarCadastroUsuarioPorID!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeResultSet(resultado);
        Banco.closeStatement(pstmt);
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
        pstmt.setInt(5, usuarioVO.getIdusuario());

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
    Connection conn =  Banco.getConnection();
    Statement stmt =  Banco.getStatement(conn);
    boolean retorno = false;

    String query = "DELETE FROM usuario WHERE idusuario = " + usuarioVO.getIdusuario();

    try {
        if (stmt.executeUpdate(query) == 1) {
            retorno = true;
        }
    } catch (SQLException erro) {
        System.out.println("Erro ao executar a query do método excluirUsuarioDAO!");
        System.out.println("Erro: " + erro.getMessage());
    } finally {
        Banco.closeStatement(stmt);
        Banco.closeConnection(conn);
    }

    return retorno;
}



}
