package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.LivrosLidosAdapter;
import br.com.senaijandira.mybooks.NaoLidosAdapter;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;


public class FragmentNaoLidos extends Fragment {
    ListView lstListaLivros;
    MyBooksDatabase myBooksDb; //variavel de acesso ao banco
    NaoLidosAdapter adapter;
    public  static Livro[] livros;
    //Chamando o arquivo xml da lista geral
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myBooksDb = Room.databaseBuilder(getActivity(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        View v = inflater.inflate(R.layout.fragment_livro_naolido, container, false);

        lstListaLivros = v.findViewById(R.id.lstListaLivrosNaoLidos); //setando o id para o ListView

        adapter = new NaoLidosAdapter(getActivity(), myBooksDb);
        lstListaLivros.setAdapter(adapter);

        //o array de Livros deve ser setado com o resultadp
        livros =  myBooksDb.daoNaoLidos().selecionarTodos();

        adapter.addAll(livros);
        return v;
    }
}
