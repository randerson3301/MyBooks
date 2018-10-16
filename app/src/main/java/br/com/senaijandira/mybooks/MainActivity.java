package br.com.senaijandira.mybooks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.fragments.FragmentLivrosGerais;
import br.com.senaijandira.mybooks.fragments.FragmentLivrosLidos;
import br.com.senaijandira.mybooks.fragments.FragmentNaoLidos;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {


    public static  Livro[] livros;
    TabLayout tabMenu;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        livros = new Livro[] {};
        tabMenu = findViewById(R.id.tabMenu);

        //Instanciando FragmentManager
        fm = getSupportFragmentManager();

        openFragmentGeral();

        tabMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    openFragmentGeral();
                } else if(tab.getPosition() == 1) {
                    openFragmentLidos();
                } else if(tab.getPosition() == 2) {
                    openFragmentNaoLidos();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    //Irá abrir o fragment dos livros gerais
    public void openFragmentGeral() {
        FragmentTransaction ft = fm.beginTransaction();

        //O frameLayout será substituido pelo class FragmentLivrosGerais
        ft.replace(R.id.frameLayout, new FragmentLivrosGerais());

        ft.commit();
    }
    //Irá abrir o fragment dos livros lidos
    public void openFragmentLidos() {
        FragmentTransaction ft = fm.beginTransaction();

        //O frameLayout será substituido pelo class FragmentLivrosGerais
        ft.replace(R.id.frameLayout, new FragmentLivrosLidos());

        ft.commit();
    }

    //Irá abrir o fragment dos livros não lidos
    public void openFragmentNaoLidos() {
        FragmentTransaction ft = fm.beginTransaction();

        //O frameLayout será substituido pelo class FragmentLivrosGerais
        ft.replace(R.id.frameLayout, new FragmentNaoLidos());

        ft.commit();
    }



    //essa var vai armazenar o id do livro, e passar para a EditarActivity
    static int id;

    public void  atualizarLivro(final Livro l, final View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Atualizar");
        alert.setMessage("Tem certeza de que deseja atualizar ?");
        alert.setNegativeButton("Não", null);

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            id = l.getId();
/*
            startActivity(new Intent(
                        getApplicationContext(),
                        CadastroActivity.class
            ));
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
    * Para poder adicionar eventos ao Spinner é necessário implementar a interface
    * AdapterView.OnItemSelectedListener e configurar dentro dos métodos
    * */

}

