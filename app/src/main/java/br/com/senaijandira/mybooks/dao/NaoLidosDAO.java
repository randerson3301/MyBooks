package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.NaoLidos;

@Dao
public interface NaoLidosDAO {

    @Insert
        //métodos do banco de dados
    void inserir(NaoLidos l);

    @Query("delete from tbl_livros_naolidos where idGeral = :idGeral")
    void excluir(int idGeral);


    @Query("select  l.*, l.capa, l.titulo, \n" +
            "l.descricao as livro from \n" +
            "tbl_livros_naolidos tnl inner join livro l on\n" +
            "tnl.idGeral = l.id")
    Livro[] selecionarTodos();

}
