package model.vo;

import java.time.LocalDate;

public class MoedaVO {
	
	private int idmoeda;
	private int idusuario;
	private String nomeMoeda;
	private String pais;
	private int ano;
	private double valor;
	private String detalhes;
	private LocalDate dataCadastroMoeda;
	private byte[] imagem;
	
	public MoedaVO(int idmoeda,int idusuario,  String nomeMoeda, String pais, int ano,double valor, String detalhes, LocalDate dataCadastroMoeda,byte[] imagem ) {
		super(); 
		this.idmoeda = idmoeda;
		this.idusuario = idusuario;
		this.nomeMoeda = nomeMoeda;
		this.pais = pais;
		this.ano = ano;
		this.valor = valor;
		this.detalhes = detalhes;
		this.dataCadastroMoeda = dataCadastroMoeda;
		this.imagem = imagem;
	}
	public MoedaVO() {
		super();
	}
	public int getIdMoeda() {
		return idmoeda;
	}
	public void setIdMoeda(int idmoeda) {
		this.idmoeda = idmoeda;
	}
	public int getIdUsuario() {
		return idusuario;
	}
	public void setIdUsuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public String getNomeMoeda() {
		return nomeMoeda;
	}
	public void setNomeMoeda(String nomeMoeda) {
		this.nomeMoeda = nomeMoeda;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	public LocalDate getDataCadastroMoeda () {
		return  dataCadastroMoeda;
	}
	public void  setDataCadastroMoeda(LocalDate dataCadastroMoeda) {
		this.dataCadastroMoeda = dataCadastroMoeda;
	}
	public byte[] getImagem() {
		return imagem;	
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}