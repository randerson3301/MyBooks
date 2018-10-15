package br.com.senaijandira.mybooks;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivrosLidosAdapter extends ArrayAdapter<Livro>  {

    MyBooksDatabase myBooksDb; //variavel de acesso ao banco


    public LivrosLidosAdapter(Context ctx, MyBooksDatabase myBooksDatabase) {
        super(ctx, 0, new ArrayList<Livro>());
        this.myBooksDb = myBooksDatabase;

    }
    Livro l = null;


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

        l = (Livro) getItem( position);



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


        return v;
    }

    }
