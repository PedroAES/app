package com.example.android.books.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    public static final String TABELA_TOKEN ="token";
    public static final String TABELA_USUARIO ="usuario";
    public static final String TABELA_LIVRO ="livro";
    public static final String TABELA_SESSAO ="sessao";
    public static final String TABELA_EMPRESTIMO ="emprestimo";
    private static final int version = 1;

    public Conexao(Context context) {
        super( context, name, null, version );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_token= "create table IF NOT EXISTS "+ TABELA_TOKEN + " (username text primary key, " +
                "token text not null, status integer default 0);";
        String sql_usuario= "create table IF NOT EXISTS "+ TABELA_USUARIO + " (matricula text primary key , username text not null, " +
                "password text not null, endereco text not null, telefone text not null);";
        String sql_livro= "create table IF NOT EXISTS "+ TABELA_LIVRO + " (codigo text primary key , titulo text not null, " +
                "autor text not null, codigo_sessao text not null);";
        String sql_sessao= "create table IF NOT EXISTS "+ TABELA_SESSAO + " (codigo text primary key , descricao text not null, " +
                "localizacao text not null);";
        String sql_emprestimo= "create table IF NOT EXISTS "+ TABELA_EMPRESTIMO + " (codigo text primary key , data_emprestimo text not null, " +
                "data_devolucao text not null, matricula_usuario text not null, emprestimos text not null);";

        try{
            db.execSQL( sql_token );
            db.execSQL( sql_usuario );
            db.execSQL( sql_livro );
            db.execSQL( sql_sessao );
            db.execSQL( sql_emprestimo );
            Log.i("INFO DB", "Sucesso ao criar as tabelas");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_token = "DROP TABLE IF EXISTS " + TABELA_TOKEN + ";";
        String sql_usuario = "DROP TABLE IF EXISTS " + TABELA_USUARIO + ";";
        String sql_livro = "DROP TABLE IF EXISTS " + TABELA_LIVRO + ";";
        String sql_sessao = "DROP TABLE IF EXISTS " + TABELA_SESSAO + ";";
        String sql_emprestimo = "DROP TABLE IF EXISTS " + TABELA_EMPRESTIMO + ";";

        try{
            db.execSQL( sql_token );
            db.execSQL( sql_usuario );
            db.execSQL( sql_livro );
            db.execSQL( sql_sessao );
            db.execSQL( sql_emprestimo );
            onCreate( db );
            Log.i("INFO DB", "Sucesso ao atualizar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela " + e.getMessage());
        }
    }
}
