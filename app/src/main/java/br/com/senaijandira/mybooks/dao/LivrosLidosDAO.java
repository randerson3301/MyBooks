package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.senaijandira.mybooks.model.Livro;

public interface LivrosLidosDAO {

    @Insert
        //métodos do banco de dados
    void inserir(Livro l);

    @Delete
    void excluir(Livro l);

    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();
}
