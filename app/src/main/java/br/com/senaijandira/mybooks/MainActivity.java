package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {
    LinearLayout listaLivros;

    //
    public static  Livro[] livros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaLivros = findViewById(R.id.listaLivros);
        livros = new Livro[] {
                /*
            new Livro(1, Utils.toByteArray(getResources(), R.drawable.pequeno_principe), "O pequeno principe", getString(R.string.pequeno_principe)),
                new Livro(2, Utils.toByteArray(getResources(), R.drawable.cinquenta_tons_cinza), "Cinquenta tons de cinza",
                        getString(R.string.cinquenta_tons)),
                new Livro(3, Utils.toByteArray(getResources(), R.drawable.kotlin_android), "Kotlin Para Android",
                        getString(R.string.kotlin_android))
            */
        };


        //Fake
        byte[] capa = Utils.toByteArray(getResources(), R.drawable.pequeno_principe); //covertendo a imagem em um array de bytes
        Livro livro = new Livro(1,
                 capa,
                "O Pequeno Principe",
                getString(R.string.pequeno_principe));


    }

    @Override
    protected void onResume() {
        super.onResume();

        listaLivros.removeAllViews();
        for (Livro l: livros) {
            criarLivro(l, listaLivros);
        }
    }

    //método para criar livro
    //ViewGroup é um grupo de elementos view
    public void criarLivro(Livro livro, ViewGroup root) {
        View v = LayoutInflater.from(this).inflate(R.layout.livro_layout, root, false);

        //Procurando os ids em livro.layout
        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);
        TextView txtLivroDesc = v.findViewById(R.id.txtLivroDescricao);

        //setando o conteúdo do livro
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa())); //convertendo array de bytes para bitmap
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDesc.setText(livro.getDescricao());

        //Exibindo na tela
        root.addView(v);

    }

    public void abrirCadastro(View v){
        listaLivros.removeAllViews();
        startActivity(new Intent(
                this,
                CadastroActivity.class
        ));
    }


}
