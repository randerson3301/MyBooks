package br.com.senaijandira.mybooks.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;

//Só precisamos definir os métodos que existirão

@Dao
public interface LivroDAO {

    @Insert //métodos do banco de dados
    void inserir(Livro l);

    @Update
    void atualizar(Livro l);

    @Delete
    void excluir(Livro l);

    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();
}
