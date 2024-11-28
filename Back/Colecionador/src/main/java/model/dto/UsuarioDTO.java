package model.dto;
import java.time.LocalDate;
public class UsuarioDTO {

	private int idusuario;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private LocalDate datacadastro;
	private LocalDate dataexpiracao;
	
	
	public UsuarioDTO(int idusuario, String nome, String email, String login, String senha, LocalDate datacadastro, LocalDate dataexpiracao) {
		super();
		this.idusuario = idusuario;
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.datacadastro = datacadastro;
		this.dataexpiracao = dataexpiracao;
	}
	
	public UsuarioDTO() {
		super();
	}
	
	public int getIdUsuario() {
		return idusuario;
	}
	
	public void setIdUsuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public LocalDate getDataCadastro() {
		return datacadastro;
	}
	
	public void setDataCadastro(LocalDate datacadastro) {
		this.datacadastro = datacadastro;
	}
	
	public LocalDate getDataExpiracao() {
		return dataexpiracao;
	}
	
	public void setDataExpiracao(LocalDate dataexpiracao) {
		this.dataexpiracao = dataexpiracao;
	}
}