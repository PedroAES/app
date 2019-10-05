package com.example.android.livros.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.livros.interfaces.IToken;
import com.example.android.livros.model.TokenAuthentication;

import java.util.ArrayList;

public class TokenDAO implements IToken {

    public TokenDAO(Context context) {
        this.conexao = new Conexao( context );
        escrever = conexao.getWritableDatabase();
        ler = conexao.getReadableDatabase();
    }

    @Override
    public void inserir(TokenAuthentication token, String username, String matricula){
        ContentValues values = new ContentValues(  );
        values.put( "username", username );
        values.put( "matricula", matricula );
        values.put( "token", token.getToken() );
        values.put( "status", 1 );
        escrever.insert( "token", null, values );
    }

    @Override
    public ArrayList<TokenAuthentication> getTokens() {
        ArrayList<TokenAuthentication> tokens = new ArrayList<>(  );
        String sql = "SELECT * FROM " + Conexao.TABELA_TOKEN +" ;";
        Cursor c = ler.rawQuery( sql , null);

        while (c.moveToNext()){
            TokenAuthentication token = new TokenAuthentication(  );
            token.setUsername( c.getString( c.getColumnIndex( "username" ) ));
            token.setToken( c.getString( c.getColumnIndex( "matricula" ) ) );
            token.setToken( c.getString( c.getColumnIndex( "token" ) ) );
            token.setStatus( c.getInt( c.getColumnIndex( "status" ) ) );
            tokens.add( token );
        }

        return tokens;
    }

    @Override
    public String getTokenPorStatus() {
        String sql = "SELECT token FROM token where status=1;";
        Cursor c = ler.rawQuery( sql, null );
        if(c == null)
            return null;
        String token = null;
        while(c.moveToNext())
            token = c.getString( c.getColumnIndex( "token" ) );

        return token;
    }

    @Override
    public TokenAuthentication getUsuarioLogado() {
        String sql = "SELECT * FROM token where status=1;";
        Cursor c = ler.rawQuery( sql, null );
        if(c == null)
            return null;
        TokenAuthentication authentication= new TokenAuthentication(  );
        while (c.moveToNext()){
            authentication.setUsername( c.getString( c.getColumnIndex( "username" ) ) );
            authentication.setMatricula( c.getString( c.getColumnIndex( "matricula" ) )  );
            authentication.setToken( c.getString( c.getColumnIndex( "token" ) ) );
            authentication.setStatus( c.getInt( c.getColumnIndex( "status" ) )  );
        }
        return authentication;
    }

    @Override
    public void altera(TokenAuthentication token, int status) {
        TokenAuthentication teste = this.getUsuarioLogado();
//        ContentValues values = new ContentValues(  );
//        values.put( "status", status );
        //while compiling: UPDATE token SET status=?,username=?,token=?,matricula=? WHERE matricula = et007
        escrever.delete( "token", "status = 1" +";",null );
    }


    private Conexao conexao;
    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;
}
