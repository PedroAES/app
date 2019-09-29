package com.example.android.books.retrofit;

import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface LivroService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    //USUARIOS
    @POST("api-token-auth/")
    Call<TokenAuthentication> loginUsuario(@Body Usuario usuario);
    @POST("logout/")
    Call<Usuario> logoutUsuario(@Body Usuario usuario);
    @GET("usuarios/{matricula}")
    Call<Usuario> getUsuario(@Header("Authorization") String token,@Query("matricula") String matricula);
    @GET("usuarios/")
    Call<List<Usuario>> getUsuarios();
    @POST("usuarios/")
    Call<Usuario> addUsuarios(@Body Usuario usuario);

    //LIVROS
    @GET("livros/")
    Call<List<Livro>> getLivros(@Header("Authorization") String token);
    @POST("livros/")
    Call<Livro> addLivros(@Header("Authorization") String token, @Body Livro livro);
    @GET("livros/{codigo}")
    Call<Livro> getLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);
    @PUT("livros/{codigo}")
    Call<Livro> updateLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);
    @DELETE("livros/{codigo}")
    Call<Livro> deleteLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);

    //EMPRESTIMOS
    @GET("emprestimos/")
    Call<List<Emprestimo>> getEmprestimos(@Header("Authorization") String token);
    @POST("emprestimos/")
    Call<Emprestimo> addEmprestimos(@Header("Authorization") String token,@Body Emprestimo emprestimo);
    @GET("emprestimos/")
    Call<Emprestimo> getEmprestimo(@Header("Authorization") String token,@Query("matricula_usuario") String matricula_usuario);
    @PUT("emprestimos/{matricula_usuario}")
    Call<Emprestimo> updateEmprestimo(@Header("Authorization") String token,@Body Emprestimo Emprestimo,@Query("matricula_usuario") String matricula_usuario);
    @DELETE("emprestimos/{matricula_usuario}")
    Call<Emprestimo> deleteEmprestimo(@Header("Authorization") String token,@Body Emprestimo Emprestimo,@Query("matricula_usuario") String matricula_usuario);

    //SESSOES
    @GET("sessoes/")
    Call<List<Sessao>> getSessoes(@Header("Authorization") String token);

}
