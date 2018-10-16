package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Arrays;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class EditarActivity extends Activity {

    ImageView imgLivroCapa;
    Bitmap livroCapa;
    EditText txtTitulo, txtDescricao;
    private MyBooksDatabase myBooksDb;
    private final int COD_REQ_GALERIA = 101;

    int idLivro, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDesc);

        //pegando os dados do livro
        idLivro = getIntent().getIntExtra("livro", 0);
        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        //atribuindo os dados a tela de editar
        Livro l  = myBooksDb.daoLivro().selecionarLivroUnico(idLivro);
        imgLivroCapa.setImageBitmap(Utils.toBitmap(l.getCapa()));
        txtTitulo.setText(l.getTitulo());
        txtDescricao.setText(l.getTitulo());




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
        byte[] capa = new byte[0];
        String titulo = "";
        String descricao = "";

        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alert.setTitle("⚠Oopss...");


        if(livroCapa != null ) {
            capa = Utils.toByteArray(livroCapa);

        }

        Boolean isAdded = myBooksDb.daoLivro().isAdded(idLivro);
        titulo = txtTitulo.getText().toString();

        descricao = txtDescricao.getText().toString();



        if(!titulo.equals("") || !descricao.equals("") || imgLivroCapa != null) {
            //capa = Utils.toByteArray(livroCapa);

            alert.setTitle("\uD83D\uDE09️Conseguiu");

            Livro livro = new Livro(idLivro,capa, titulo, descricao, false);

            myBooksDb.daoLivro().atualizar(livro);

          //  tamanhoArray = 0;

            alert.setMessage("Atualizado com sucesso");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else  {
            alert.setMessage("Por favor, atualize pelo menos um campo, ou volte para a tela principal.");

        }

        alert.create().show();

    }
}
