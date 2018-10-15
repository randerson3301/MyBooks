package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tbl_livros_lidos")
public class LivrosLidos {

    @PrimaryKey(autoGenerate = true)
    private int idLidos;

    @ForeignKey(entity = Livro.class, childColumns = "idGeral", parentColumns = "id")
    private int idGeral;

    //contrutores
    public LivrosLidos(){}
    public LivrosLidos(int idGeral){}

    //comportamento
    public int getIdGeral() {
        return idGeral;
    }

    public void setIdGeral(int idGeral) {
        this.idGeral = idGeral;
    }

    public int getIdLidos() {
        return idLidos;
    }

    public void setIdLidos(int idLidos) {
        this.idLidos = idLidos;
    }
}
