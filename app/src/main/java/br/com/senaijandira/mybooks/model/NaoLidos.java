package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tbl_livros_naolidos")
public class NaoLidos {

    @PrimaryKey(autoGenerate = true)
    private int idNaoLidos;

    @ForeignKey(entity = Livro.class, childColumns = "idGeral", parentColumns = "id")
    private int idGeral;

    //contrutores
    public NaoLidos(){}
    public NaoLidos(int idGeral){}

    //comportamento
    public int getIdGeral() {
        return idGeral;
    }

    public void setIdGeral(int idGeral) {
        this.idGeral = idGeral;
    }

    public int getIdNaoLidos() {
        return idNaoLidos;
    }

    public void setIdNaoLidos(int idLidos) {
        this.idNaoLidos = idLidos;
    }
}
