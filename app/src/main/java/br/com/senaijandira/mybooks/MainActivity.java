package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {
    public static  Livro[] livros;
    private MyBooksDatabase myBooksDb; //variavel de acesso ao banco

    //Chamando a class ListView que guardará os cardviews dentro, para poder estruturar a lista geral
    ListView lstListaLivros;

    LivrosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstListaLivros = findViewById(R.id.lstListaLivros); //setando o id para o ListView
        //instanciando...
        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
        .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        adapter = new LivrosAdapter(this);
        lstListaLivros.setAdapter(adapter);
        livros = new Livro[] {
                /*
            new Livro(1, Utils.toByteArray(getResources(), R.drawable.pequeno_principe), "O pequeno principe", getString(R.string.pequeno_principe)),
                new Livro(2, Utils.toByteArray(getResources(), R.drawable.cinquenta_tons_cinza), "Cinquenta tons de cinza",
                        getString(R.string.cinquenta_tons)),
                new Livro(3, Utils.toByteArray(getResources(), R.drawable.kotlin_android), "Kotlin Para Android",
                        getString(R.string.kotlin_android))
            */
        };

        /*
        //Fake
        byte[] capa = Utils.toByteArray(getResources(), R.drawable.pequeno_principe); //covertendo a imagem em um array de bytes
        Livro livro = new Livro(1,
                 capa,
                "O Pequeno Principe",
                getString(R.string.pequeno_principe));*/


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Faz o select de todos os livros cadastros
        livros = myBooksDb.daoLivro().selecionarTodos();

        /*
        listaLivros.removeAllViews();
        for (Livro l: livros) {
            criarLivro(l, listaLivros);
        }
        */
    }


    public void  deletarLivro(final Livro l, final View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Excluir");
        alert.setMessage("Tem certeza de que deseja excluir ?");
        alert.setNegativeButton("Não", null);

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myBooksDb.daoLivro().excluir(l);

                /*
                listaLivros.removeView(v);
                */
            }
        });

        alert.show();


    }




    //método para criar livro
    //ViewGroup é um grupo de elementos view
    public void criarLivro(final Livro livro, ViewGroup root) {
       final View v = LayoutInflater.from(this).inflate(R.layout.livro_layout, root, false);

        //Procurando os ids em livro.layout
        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);
        TextView txtLivroDesc = v.findViewById(R.id.txtLivroDescricao);

        //img lixeira
        ImageView imgDelete = v.findViewById(R.id.imgDeleteLivro);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(livro, v);
            }
        });

        //setando o conteúdo do livro
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa())); //convertendo array de bytes para bitmap
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDesc.setText(livro.getDescricao());

        //Exibindo na tela
        root.addView(v);

    }

    public void abrirCadastro(View v){
        /*
        listaLivros.removeAllViews();
        */
        startActivity(new Intent(
                this,
                CadastroActivity.class
        ));
    }


    /*
        A class LivroAdapater precisa de um construtor , e dentro desse construtor
    * precisa ser colocado um super com os respectivos parâmetros e uma instancia da
    * class ArrayList para assim poder armazenar os livros, como se fosse uma lista.
    * */
    private class LivrosAdapter extends ArrayAdapter<Livro> {
        public LivrosAdapter(Context ctx) {
            super(ctx, 0, new ArrayList<Livro>());
        }
    }


}
