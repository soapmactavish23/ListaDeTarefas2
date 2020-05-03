package com.example.listadetarefas2.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas2.R;
import com.example.listadetarefas2.activity.model.Db;
import com.example.listadetarefas2.activity.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class TarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    private  Db db = new Db(TarefaActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        editTarefa = (TextInputEditText) findViewById(R.id.txtTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    //Criando o Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Salvando a Tarefa
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar:
                String texto = editTarefa.getText().toString();
                if(!texto.isEmpty()){ // Edição

                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(texto);
                    tarefa.setId(tarefaAtual.getId());

                    //Atualizar no banco de dados
                    if(db.atualizar(tarefa)){
                        Toast.makeText(
                                TarefaActivity.this,
                                "Tarefa atualizar com sucesso!",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    }
                }else{//Salvar
                    if(texto.equals("")){
                        Toast.makeText(
                                TarefaActivity.this,
                                "Digite uma tarefa",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else{
                        //Receber da classe db os métodos
                        db.salvar(texto);
                        Toast.makeText(
                                TarefaActivity.this,
                                "Tarefa salva com sucesso!",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}