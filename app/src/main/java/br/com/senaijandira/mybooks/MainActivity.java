package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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

        livros = new Livro[] {};

        lstListaLivros = findViewById(R.id.lstListaLivros); //setando o id para o ListView
        //instanciando...
        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
        .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        adapter = new LivrosAdapter(this);
        lstListaLivros.setAdapter(adapter);






    }

    public void carregarLivro() {

        //mostra todos os livros inseridos na lista
        adapter.clear(); //não mostra registros repetidos
        Livro[] livros = myBooksDb.daoLivro().selecionarTodos();

        adapter.addAll(livros);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregarLivro();

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

                carregarLivro(); //atualiza a tela sem o livro excluído

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

        //Retornando o layout do livro com os dados

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;


            /*
            * Caso a view não retorne com o layout determinado, o procedure dentro do if
            * irá fazer isso de forma automática.*/
            if(v == null) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent,
                        false);
            }

            final Livro l = getItem(position);

            //Pegando os ids da livrolayout.xml
            ImageView imageView = v.findViewById(R.id.imgLivroCapa);

            TextView txtTitulo = v.findViewById(R.id.txtTituloLivro);

            TextView txtDesc = v.findViewById(R.id.txtLivroDescricao);

            //img lixeira
            ImageView imgDelete = v.findViewById(R.id.imgDeleteLivro);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletarLivro( l , view);
                }
            });

            //setando o conteúdo do livro
            imageView.setImageBitmap(Utils.toBitmap(l.getCapa()));

            //inserindo conteúdo
            txtTitulo.setText(l.getTitulo());
            txtDesc.setText(l.getDescricao());

            //inserindo opções para o spinner
            Spinner spinner = (Spinner) v.findViewById(R.id.spin);

            //criando um SpinnerAdapter para armazenar o array de strings options
            ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this.getContext(),
                    R.array.options, android.R.layout.simple_spinner_item);

            //setando a forma com que quero que o spinner mostre as opções
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //setando o adapter
            spinner.setAdapter(adapterSpinner);



            return v;
        }
    }


}
