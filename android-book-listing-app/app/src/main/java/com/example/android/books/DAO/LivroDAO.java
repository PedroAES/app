package com.example.android.books.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.books.interfaces.ILivro;
import com.example.android.books.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements ILivro {

    public LivroDAO(Context context) {
        this.conexao = new Conexao( context );
        escrever = conexao.getWritableDatabase();
        ler = conexao.getReadableDatabase();
    }

    @Override
    public void inserirLivro(Livro livro) {
        ContentValues values = new ContentValues(  );
        values.put( "codigo", livro.getCodigo() );
        values.put( "titulo", livro.getTitulo());
        values.put( "autor", livro.getAutor() );
        values.put("codigo_sessao", livro.getCodigo_sessao());
        escrever.insert( "livro", null, values );
    }

    @Override
    public List<Livro> getAllLivros() {
        String sql= "select * from livro;";
        Cursor c = ler.rawQuery( sql , null);
        if(c == null)
            return null;
        List<Livro> livros = new ArrayList<>();
        while (c.moveToNext()){
            Livro livro = new Livro();
            livro.setTitulo(c.getString(c.getColumnIndex("titulo")));
            livro.setAutor(c.getString(c.getColumnIndex("autor")));
            livro.setCodigo(c.getString(c.getColumnIndex("codigo")));
            livro.setCodigo_sessao(c.getString(c.getColumnIndex("codigo_sessao")));
            livros.add(livro);
        }

        return livros;
    }

    private Conexao conexao;
    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;
}
