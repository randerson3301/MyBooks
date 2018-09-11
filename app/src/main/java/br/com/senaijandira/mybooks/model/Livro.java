package br.com.senaijandira.mybooks.model;

public class Livro {
    //Estado(Atributos da classe Livro)
    private int id;
    private String descricao;
    private String titulo;
    private byte[] capa;


    public Livro() {

    }

    public Livro(int id, byte[] capa, String titulo, String descricao) {
        this.id = id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    //Comportamento
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }



}
