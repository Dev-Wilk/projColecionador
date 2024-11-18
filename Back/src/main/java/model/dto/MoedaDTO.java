package model.dto;

import java.time.LocalDate;

public class MoedaDTO {

 private int idmoeda;
    private int idusuario;
    private String nome;
    private String pais;
    private int ano;
    private double valor;
    private String detalhes;
    private LocalDate datacadastro;
    private byte [] imagem;

    public MoedaDTO() {
    }

    public MoedaDTO(int idmoeda, int idusuario, String nome, String pais, int ano, double valor, String detalhes,
            LocalDate datacadastro) {
        this.idmoeda = idmoeda;
        this.idusuario = idusuario;
        this.nome = nome;
        this.pais = pais;
        this.ano = ano;
        this.valor = valor;
        this.detalhes = detalhes;
        this.datacadastro = datacadastro;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public LocalDate getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(LocalDate datacadastro) {
        this.datacadastro = datacadastro;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public int getIdmoeda() {
        return idmoeda;
    }

    public void setIdmoeda(int idmoeda) {
        this.idmoeda = idmoeda;
    }








}
