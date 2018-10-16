package br.com.senaijandira.mybooks.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;

//Só precisamos definir os métodos que existirão

@Dao
public interface LivroDAO {

    @Insert //métodos do banco de dados
    void inserir(Livro l);

    @Update
    void atualizar(Livro l);

    @Delete
    void excluir(Livro l);

   // @Update
    //void updateAdded(boolean added, int id);

    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();

    //selecionando ddados de apenas um livro
    @Query("SELECT * FROM livro where id = :idLivro")
    Livro selecionarLivroUnico(int idLivro);

    @Query("SELECT livro.* FROM livro, tbl_livros_lidos where livro.id = tbl_livros_lidos.idGeral")
    Livro[] selecionarLivrosLidos();

    @Query("select livro.added from livro where id = :id ")
    Boolean isAdded(int id);

    //atualizar livro pelo i
    //void atualizar(Livro id);




}
