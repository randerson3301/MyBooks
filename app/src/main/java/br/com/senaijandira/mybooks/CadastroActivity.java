package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Arrays;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {

    ImageView imgLivroCapa;
    Bitmap livroCapa;
    EditText txtTitulo, txtDescricao;
    private MyBooksDatabase myBooksDb;
    private final int COD_REQ_GALERIA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDesc);

        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();




    }

    public void abrirGaleria(View view) {
        /*
        * Intent explicito: Digo o nome da classe q quero abrir
        * Intent implicito: O SO resolve qual app será aberto
        * */

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        //retorna um resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"),
                COD_REQ_GALERIA );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK) {

            try {
                InputStream input = getContentResolver().openInputStream(data.getData());

                livroCapa = BitmapFactory.decodeStream(input); //converteu para bitmap

                //Exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void salvarLivro(View view) {
        byte[] capa;
        String titulo = "";
        String descricao = "";

        titulo = txtTitulo.getText().toString();

        descricao = txtDescricao.getText().toString();

        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alert.setTitle("⚠️ Oopss...");


        if(livroCapa == null || titulo.equals("") || descricao.equals("")) {
            alert.setMessage("Por favor, preencha todos os campos");

        } else {
            capa = Utils.toByteArray(livroCapa);


            Livro livro = new Livro(capa, titulo, descricao, false);
             int tamanhoArray = MainActivity.livros.length;


            MainActivity.livros = Arrays.copyOf(MainActivity.livros,
                    tamanhoArray + 1);


            //Inserindo no espaço a mais
            MainActivity.livros[tamanhoArray] = livro;


            myBooksDb.daoLivro().inserir(livro);

            tamanhoArray = 0;

            alert.setMessage("É isso meu jovem");

            txtTitulo.setText("");
            txtDescricao.setText("");
        }

        alert.create().show();

    }
}
