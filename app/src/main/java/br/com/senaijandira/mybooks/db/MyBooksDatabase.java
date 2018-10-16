package br.com.senaijandira.mybooks.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDAO;
import br.com.senaijandira.mybooks.dao.LivrosLidosDAO;
import br.com.senaijandira.mybooks.dao.NaoLidosDAO;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.NaoLidos;

@Database(entities = {Livro.class, LivrosLidos.class, NaoLidos.class},  version = 6) //Caso alguma alteração seja feita no banco, a versão será 2 e assim por diante
public abstract class MyBooksDatabase extends RoomDatabase {

    public abstract LivroDAO daoLivro();

    public abstract LivrosLidosDAO daoLivrosLidos();

    public abstract NaoLidosDAO daoNaoLidos();

}
