package model.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.vo.UsuarioVO;

public class UsuarioDAO {


	
	public boolean verificarCadastroUsuarioBancoDAO(UsuarioVO usuarioVO) {
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = null;
	    ResultSet resultado = null;
	    boolean retorno = false;
	    String query = "SELECT COUNT(*) FROM usuario WHERE idusuario = ?";
	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, usuarioVO.getIdUsuario()); // Passa o ID do usuário corretamente
	        resultado = pstmt.executeQuery();
	        if (resultado.next() && resultado.getInt(1) > 0) {
	            retorno = true;
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao executar a query do método verificarCadastroUsuarioBancoDAO");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeResultSet(resultado);
	        Banco.closePreparedStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return retorno;
	}
	
	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
		String query = "INSERT INTO usuario (nome, email, login, senha, datacadastro) VALUES(?, ?, ?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			pstmt.setString(1, usuarioVO.getNome());
			pstmt.setString(2, usuarioVO.getEmail());
			pstmt.setString(3, usuarioVO.getLogin());
			pstmt.setString(4, usuarioVO.getSenha());
			pstmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
			pstmt.execute();
	        resultado = pstmt.getGeneratedKeys();
		if (resultado.next()) {
			usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método, cadastrarUsuarioDAO");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
			Banco.closeResultSet(resultado);
			
		}
		return usuarioVO;
	}


	
	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        boolean retorno = false;
        String query = "UPDATE  usuario set dataExpiracao = ?  WHERE idUsuario = ?";
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
	
	
	
	public boolean editarUsuarioDAO(UsuarioVO usuarioVO) {
	    String query = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE idusuario = ? "  ;
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	    boolean retorno = false;
	    try {
	        pstmt.setString(1, usuarioVO.getNome());
	        pstmt.setString(2, usuarioVO.getEmail());
	        pstmt.setString(3, usuarioVO.getLogin());
	        pstmt.setString(4, usuarioVO.getSenha());
	        pstmt.setInt(5, usuarioVO.getIdUsuario());
	        int linhasAfetadas = pstmt.executeUpdate();
	        if (linhasAfetadas > 0) {
	            retorno = true;
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao executar a query do método, editarUsuarioDAO");
	        System.out.println("Erro " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	        
	    }
	    return retorno;
	}
	
	public UsuarioVO logarUsuarioDAO(UsuarioVO usuarioVO) {
		String query = "SELECT idusuario, nome, email, login, senha " + "FROM usuario " + "WHERE login = '" + usuarioVO.getLogin() + "' AND senha = '" + usuarioVO.getSenha() + "'";;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		UsuarioVO user = new UsuarioVO();
		   try{
	            resultado = stmt.executeQuery(query);
	            if(resultado.next()){
	                user = new UsuarioVO();
	                user.setIdUsuario(Integer.parseInt(resultado.getString(1)));
	                user.setNome(resultado.getString(2));
	                user.setEmail(resultado.getString(3));
	                user.setLogin(resultado.getString(4));
	                user.setSenha(resultado.getString(5));
	            }
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método, logarUsuarioDAO");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return user;
	}
	
}