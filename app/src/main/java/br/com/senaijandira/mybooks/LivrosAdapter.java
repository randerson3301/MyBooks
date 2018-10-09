package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosAdapter extends ArrayAdapter<Livro> implements
        AdapterView.OnItemSelectedListener {
    /*Variaveis que vieram da MainActivity*/

    Spinner spinner;
    ArrayAdapter<CharSequence> adapterSpinner;
    MyBooksDatabase myBooksDb; //variavel de acesso ao banco


    public LivrosAdapter(Context ctx, MyBooksDatabase myBooksDatabase) {
        super(ctx, 0, new ArrayList<Livro>());
        this.myBooksDb = myBooksDatabase;

    }
    Livro l = null;



    //-------------MÉTODOS DA OnItemSelectedListener-----------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 1) {
            //Toast.makeText(parent.getContext(), "Estou aqui! ", Toast.LENGTH_SHORT).show();
            myBooksDb.daoLivrosLidos().inserir(l);
            Toast.makeText(parent.getContext(), "Você já leu !! ", Toast.LENGTH_SHORT).show();

        } else if(position == 2) {
            l.setLido(false);
            Toast.makeText(parent.getContext(), "Você ainda n leu !!! ", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
/*
    //instanciando...
    myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
            .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

*/
    public void  deletarLivro(final Livro l, final View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Excluir");
        alert.setMessage("Tem certeza de que deseja excluir ?");
        alert.setNegativeButton("Não", null);

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myBooksDb.daoLivro().excluir(l);
            }

        });

        alert.show();
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

        l = getItem(position);



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
        spinner = (Spinner) v.findViewById(R.id.spin);
        spinner.setOnItemSelectedListener(this);

        //criando um SpinnerAdapter para armazenar o array de strings options
        adapterSpinner = ArrayAdapter.createFromResource(this.getContext(), R.array.options,
                android.R.layout.simple_spinner_item);

        //setando a forma com que quero que o spinner mostre as opções
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setando o adapter
        spinner.setAdapter(adapterSpinner);
        return v;
    }


}
