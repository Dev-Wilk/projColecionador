package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.vo.MoedaVO;

public class MoedaDAO {
	
	public byte[] consultarImagemMoedaDAO(int idMoeda) {
        Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultado = null;
        byte[] retorno = null;
        String query = "SELECT imagem FROM moeda WHERE idMoeda = ?";
        
        try {
    		pstmt = Banco.getPreparedStatement(conn, query);
    		pstmt.setInt(1, idMoeda);
    		resultado = pstmt.executeQuery();
    		if( resultado.next()) {
    			retorno = resultado.getBytes("imagem");
    		}
    	} catch (SQLException erro) {
    		System.out.println("Erro ao executar a query do método consultarImagemPessoaDAO!");
    		System.out.println("Erro: " +erro.getMessage());
    	} finally {
    		Banco.closeResultSet(resultado);
    		Banco.closeStatement(pstmt);
    		Banco.closeConnection(conn);
    	}
    	return retorno;
    }
	
	
	
	public boolean verificarCadastroMoedaBancoDAO(MoedaVO moedaVO) {
		Connection conn = Banco.getConnection();
        PreparedStatement pstmt = null;
        ResultSet resultado = null;
        boolean retorno = false;
		String query = "SELECT idmoeda FROM moeda WHERE nome = ? AND idusuario = ? AND ano = ? AND pais = ?";
		
		try {
				pstmt = Banco.getPreparedStatement(conn, query);
				pstmt.setString(1, moedaVO.getNome());
				pstmt.setInt(2, moedaVO.getIdUsuario());
				pstmt.setInt(3, moedaVO.getAno());
        		pstmt.setString(4, moedaVO.getPais());
        	        	
        		resultado = pstmt.executeQuery();
        		if (resultado.next()) {
                	retorno = true;
            }
		} catch (SQLException erro) {
            	System.out.println("Erro ao executar a query do método verificarCadastroMoedaDAO.");
            	System.out.println("Erro: " + erro.getMessage());
        } finally {
            	Banco.closeResultSet(resultado);
            	Banco.closeStatement(pstmt);
            	Banco.closeConnection(conn);
        }
        return retorno;
	}

	
	public MoedaVO cadastrarMoedaDAO (MoedaVO moedaVO) {
		String query ="INSERT INTO moeda (nome, pais, ano, valor, detalhes, datacadastro, imagem, idusuario )";
		Connection conn = Banco.getConnection();
	    PreparedStatement pstmt =  Banco.getPreparedStatement(conn, query);
	    
	    
	    try {
			pstmt.setString(1, moedaVO.getNome());
			pstmt.setString(2, moedaVO.getPais());
			pstmt.setInt(3, moedaVO.getAno());
			pstmt.setDouble(4, moedaVO.getValor());
			pstmt.setString(5, moedaVO.getDetalhes());
			pstmt.setObject(6, LocalDate.now());
			pstmt.setBytes(7, moedaVO.getImagem());
			pstmt.setInt(8, moedaVO.getIdUsuario());
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				moedaVO.setIdMoeda(resultado.getInt(1));
		}
	    } catch (SQLException erro) {
        	System.out.println("Erro ao executar a query do método cadastrarMoedaDAO.");
        	System.out.println("Erro: " + erro.getMessage());
	    } finally {
        	Banco.closeStatement(pstmt);
        	Banco.closeConnection(conn);
	    }
    return moedaVO;
	
	}
	
	
	public ArrayList<MoedaVO> consultarTodasMoedasDAO(int idUsuario){
		Connection conn = Banco.getConnection();
	    Statement stmt = Banco.getStatement(conn);
	    ResultSet resultado = null;
	    ArrayList<MoedaVO> listaMoedas = new ArrayList<>();
	    String query = ("SELECT * FROM moeda WHERE idusuario = "+ idUsuario);
	    
	    try {
			resultado = stmt.executeQuery(query);
		
			while(resultado.next()) {
				MoedaVO moeda = new MoedaVO();
				moeda.setIdMoeda(Integer.parseInt(resultado.getString(1)));
				moeda.setNome(resultado.getString(2));
				moeda.setPais(resultado.getString(3));
				moeda.setAno(resultado.getInt(4));
				moeda.setValor(resultado.getDouble(5));
				moeda.setDetalhes(resultado.getString(6));
				listaMoedas.add(moeda);
			}
		
	    } catch (SQLException erro) {
        	System.out.println("Erro ao executar a query do método consultarTodasMoedasDAO!");
        	System.out.println("Erro: " + erro.getMessage());
	    } finally {
        	Banco.closeResultSet(resultado);
        	Banco.closeStatement(stmt);
        	Banco.closeConnection(conn);
	    
	} return listaMoedas; }
	
	
	public boolean verificarMoedaDAO(MoedaVO moedaVO) {
		Connection conn = Banco.getConnection();
	    Statement stmt = Banco.getStatement(conn);
	    ResultSet resultado = null;
	    boolean retorno = false;
	    String query = "SELECT idmoeda FROM moeda WHERE idmoeda = "+ moedaVO.getIdMoeda();
	
	    try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
	    }catch (SQLException erro) {
        	System.out.println("Erro ao executar a query do método verificarCadastroMoedaPorID!");
        	System.out.println("Erro: " + erro.getMessage());
	    } finally {
        	Banco.closeResultSet(resultado);
        	Banco.closeStatement(stmt);
        	Banco.closeConnection(conn);
    	}
    return retorno;
	        
	}
	
	
	public boolean atualizarMoedaDAO(MoedaVO moedaVO) {
		Connection conn = Banco.getConnection();
	    PreparedStatement pstmt = null;
	    boolean retorno = false;
	    String query = "";
	    
	    	if (moedaVO.getImagem() != null && moedaVO.getImagem().length > 0) {
	    		query = "UPDATE moeda SET nome = ? , pais = ? , ano = ? , valor = ? detalhes = ? , imagem = ? WHERE idmoeda = ?";
	    		pstmt = Banco.getPreparedStatement(conn, query);
	    	} else {
	    		query = "UPDATE moeda SET nome = ? , pais = ? , ano = ? , valor = ? detalhes = ? WHERE idmoeda = ?";
	    		pstmt = Banco.getPreparedStatement(conn, query);
	    	}
	   try {
			pstmt.setString(1, moedaVO.getNome());
			pstmt.setString(2, moedaVO.getPais());
			pstmt.setInt(3, moedaVO.getAno());
			pstmt.setDouble(4, moedaVO.getValor());
			pstmt.setString(5, moedaVO.getDetalhes());
			if (moedaVO.getImagem() != null && moedaVO.getImagem().length > 0) {
			pstmt.setBytes(6, moedaVO.getImagem());
			pstmt.setInt(7, moedaVO.getIdMoeda());
			}else {
				pstmt.setInt(7, moedaVO.getIdMoeda());
			}
		
			if (pstmt.executeUpdate() == 1) {
            	retorno = true;
			}
	   } catch (SQLException erro) {
        	System.out.println("Erro ao executar a query do método cadastrarMoedaDAO!");
        	System.out.println("Erro: " + erro.getMessage());
	   } finally {
        	Banco.closeStatement(pstmt);
        	Banco.closeConnection(conn);
    }
    return retorno;
	}
		   
	   
	
	public boolean excluirMoedaDAO(MoedaVO moedaVO) {
		Connection conn = Banco.getConnection();
	    Statement stmt = Banco.getStatement(conn);
	    boolean retorno = false;
	    String query = "DELETE FROM moeda WHERE idmoeda = "+ moedaVO.getIdMoeda();
	    
	    try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
        }
	    } catch (SQLException erro) {
        	System.out.println("Erro ao executar a query do método excluirMoedaDAO!");
        	System.out.println("Erro: " + erro.getMessage());
	    } finally {
        	Banco.closeStatement(stmt);
        	Banco.closeConnection(conn);
	    }
    return retorno;	   
	}
	
		
	
	
}