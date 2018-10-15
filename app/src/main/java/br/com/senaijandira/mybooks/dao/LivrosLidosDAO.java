package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.senaijandira.mybooks.model.LivrosLidos;

@Dao
public interface LivrosLidosDAO {

    @Insert
        //métodos do banco de dados
    void inserir(LivrosLidos l);

    @Delete
    void excluir(LivrosLidos l);


    @Query("select  tll.*, l.capa, l.titulo, \n" +
            "l.descricao as livro from \n" +
            "tbl_livros_lidos tll inner join livro l on\n" +
            "tll.idGeral = l.id")
    LivrosLidos[] selecionarTodos();

}
