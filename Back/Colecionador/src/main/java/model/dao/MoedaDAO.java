package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.vo.MoedaVO;

public class MoedaDAO {
	
	
	public MoedaVO cadastrarMoedaDAO(MoedaVO moedaVO) {
	    String sql = "INSERT INTO moeda (nome, pais, ano, valor, detalhes, datacadastro, imagem, idusuario) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, sql);
	    ResultSet resultado = null;
	    try {
	        System.out.println("Preparando para inserir moeda no banco: " + moedaVO);
	        
	        pstmt.setString(1, moedaVO.getNomeMoeda());
	        pstmt.setString(2, moedaVO.getPais());
	        pstmt.setInt(3, moedaVO.getAno());
	        pstmt.setDouble(4, moedaVO.getValor());
	        pstmt.setString(5, moedaVO.getDetalhes());
	        pstmt.setDate(6, Date.valueOf(java.time.LocalDate.now()));
	        pstmt.setBytes(7, moedaVO.getImagem()); // Imagem pode ser nula
	        pstmt.setInt(8, moedaVO.getIdUsuario());

	        int linhasAfetadas = pstmt.executeUpdate();
	        System.out.println("Linhas afetadas na inserção: " + linhasAfetadas);

	        resultado = pstmt.getGeneratedKeys();
	        if (resultado.next()) {
	            moedaVO.setIdMoeda(resultado.getInt(1));
	            System.out.println("ID da moeda cadastrada: " + resultado.getInt(1));
	        } else {
	            System.out.println("Falha ao gerar ID da moeda.");
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao executar a query no método cadastrarMoedaDAO:");
	        System.out.println("Mensagem de erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	        Banco.closeResultSet(resultado);
	    }
	    return moedaVO;
	}
	public boolean editCoinDAO(MoedaVO moedaVO) {
		boolean retorno = false;
	    Connection conn = Banco.getConnection();
	    PreparedStatement stmt = null;
	    String query = "";
	    if(moedaVO.getImagem() != null && moedaVO.getImagem().length > 0) { 
	    	query = "UPDATE moeda SET nome = ?, pais = ?, ano = ?, valor = ?, detalhes = ?, imagem = ? WHERE idmoeda = ?";
	    	stmt = Banco.getPreparedStatement(conn, query);
	    } else {
	    	query = "UPDATE moeda SET nome = ?, pais = ?, ano = ?, valor = ?, detalhes = ? WHERE idmoeda = ?";
	    	stmt = Banco.getPreparedStatement(conn, query);
	    }
	    try {
		    	stmt.setString(1, moedaVO.getNomeMoeda());
				stmt.setString(2, moedaVO.getPais());
				stmt.setInt(3, moedaVO.getAno());
				stmt.setDouble(4, moedaVO.getValor());
				stmt.setString(5, moedaVO.getDetalhes());
				if (moedaVO.getImagem() != null && moedaVO.getImagem().length > 0) {
					stmt.setBytes(6, moedaVO.getImagem());
					stmt.setInt(7, moedaVO.getIdMoeda());
				} else {
					stmt.setInt(6, moedaVO.getIdMoeda());
				}
 						if(stmt.executeUpdate() == 1) {
 							retorno = true;
 						}
	    }  catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método, editarMoedaDAO");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	public boolean excluirMoedaDAO(MoedaVO moedaVO) {
	    String query = "DELETE FROM moeda WHERE idmoeda = ?";
	    Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
	    boolean retorno = false;

	    try {
	        pstmt.setInt(1, moedaVO.getIdMoeda());
	        if (pstmt.executeUpdate() == 1) {
	            retorno = true;
	        }
	    } catch (SQLException erro) {
	        System.out.println("Erro ao executar a query do método excluirMoedaDAO");
	        System.out.println("Erro: " + erro.getMessage());
	    } finally {
	        Banco.closeStatement(pstmt);
	        Banco.closeConnection(conn);
	    }
	    return retorno;
	}
	public ArrayList<MoedaVO> consultarMoedaDAO(int idusuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		String query = "SELECT idmoeda, nome, pais, ano, valor, detalhes, imagem FROM moeda WHERE idusuario = '" + idusuario + "' ";
		ArrayList<MoedaVO> bostaMoeda  = new ArrayList<>(); 
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				MoedaVO moeda = new MoedaVO();
				moeda.setIdMoeda(Integer.parseInt(resultado.getString(1)));
				moeda.setNomeMoeda(resultado.getString(2));
				moeda.setPais(resultado.getString(3));
				moeda.setAno(resultado.getInt(4));
				moeda.setValor(resultado.getDouble(5));
				moeda.setDetalhes(resultado.getString(6));
				moeda.setImagem(resultado.getBytes(7));
				bostaMoeda.add(moeda);
		
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método, consultarMoedaDAO");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return bostaMoeda;
	}
	
	public boolean verificarCadastroMoedaDAO(MoedaVO moedaVO) {
		String query = "SELECT idmoeda FROM moeda WHERE idmoeda = '" + moedaVO.getIdMoeda() + "' ";
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		try {
			resultado = stmt.executeQuery(query);
		if (resultado.next()) {
			return true;
			} 
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método, verificarMoedaDAO");
			System.out.println("Erro " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
			Banco.closeResultSet(resultado);
		}
		return false;

	}
	
	
	
}