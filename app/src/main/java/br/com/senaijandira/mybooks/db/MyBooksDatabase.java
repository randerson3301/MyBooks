package br.com.senaijandira.mybooks.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDAO;
import br.com.senaijandira.mybooks.model.Livro;

@Database(entities = {Livro.class}, version = 2) //Caso alguma alteração seja feita no banco, a versão será 2 e assim por diante
public abstract class MyBooksDatabase extends RoomDatabase {

    public abstract LivroDAO daoLivro();

}
