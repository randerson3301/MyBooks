package br.com.senaijandira.mybooks.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Livro {

    //Estado(Atributos da classe Livro)
    @PrimaryKey(autoGenerate = true) //Setando o id como primary key do banco
    private int id;
    private String descricao;
    private String titulo;
    private boolean isLido;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) //Setando o tipo da capa como blob no banco
    private byte[] capa;

    public Livro() {}

    public Livro(byte[] capa, String titulo, String descricao, boolean isLido) {
        //this.id = id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.isLido = isLido;
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

    //métodos para retornar livros especificados
    public boolean isLido() {
        return isLido;
    }

    public void setLido(boolean lido) {
        isLido = lido;
    }



}
