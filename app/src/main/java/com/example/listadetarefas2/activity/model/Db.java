package com.example.listadetarefas2.activity.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Db extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "appTarefas";
    public static String NOME_TABELA = "tarefas";
    public Context context;

    public Db(Context c) {
        super(c, NOME_DB,null, VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+NOME_TABELA+"(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvar(String texto){
        getWritableDatabase();
        //Inserindo dados
        ContentValues cv = new ContentValues();
        cv.put("tarefa", texto);
        getWritableDatabase().insert(Db.NOME_TABELA, null, cv);
    }

    public List<Tarefa> listar(){

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + Db.NOME_TABELA + ";";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("tarefa"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);

        }

        return tarefas;

    }

    public boolean deletar(Long id){
        try{
            String[] args = {Long.toString(id)};
            getWritableDatabase().delete(Db.NOME_TABELA, "id=?", args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean atualizar(Tarefa tarefa){
        ContentValues cv = new ContentValues();
        cv.put("tarefa", tarefa.getNomeTarefa() );
        try {
            String[] args = {tarefa.getId().toString()};
            getWritableDatabase().update(Db.NOME_TABELA, cv, "id=?", args );
            Log.i("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }
        return true;
    }
}
