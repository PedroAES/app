package com.example.android.books.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.books.interfaces.IToken;
import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TokenDAO implements IToken {

    public TokenDAO(Context context) {
        this.conexao = new Conexao( context );
        escrever = conexao.getWritableDatabase();
        ler = conexao.getReadableDatabase();
    }

    public void inserir(TokenAuthentication token, String username){
        ContentValues values = new ContentValues(  );
        values.put( "username", username );
        values.put( "token", token.getToken() );
        values.put( "status", 1 );
        escrever.insert( "token", null, values );
    }

    @Override
    public List<TokenAuthentication> getTokens() {
        List<TokenAuthentication> tokens = new ArrayList<>(  );
        String sql = "SELECT * FROM " + Conexao.TABELA_TOKEN +" ;";
        Cursor c = ler.rawQuery( sql , null);

        while (c.moveToNext()){
            TokenAuthentication token = new TokenAuthentication(  );
            token.setUsername( c.getString( c.getColumnIndex( "username" ) ));
            token.setToken( c.getString( c.getColumnIndex( "token" ) ) );
            token.setStatus( c.getInt( c.getColumnIndex( "status" ) ) );
            tokens.add( token );
        }

        return tokens;
    }

    @Override
    public TokenAuthentication atualizarStatus(String token) {
        String sql = "SELECT username, token, status from token where token = "+ token;
        Cursor c = ler.rawQuery( sql, null );
        if(c==null)
            return null;
        TokenAuthentication tokenAuthentication = new TokenAuthentication(  );
        while(c.moveToNext()){
            tokenAuthentication.setUsername( c.getString( c.getColumnIndex( "username" ) ));
            tokenAuthentication.setToken( c.getString( c.getColumnIndex( "token" ) ) );
            tokenAuthentication.setStatus( c.getInt( c.getColumnIndex( "status" ) ) );
        }
        return tokenAuthentication;
    }


    private Conexao conexao;
    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;
}
