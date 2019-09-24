package com.example.android.books.retrofit;

import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LivroService {

//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/json"
//    })

    //USUARIOS
    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<TokenAuthentication> loginUsuario(@Field( "username" ) String username, @Field( "password" ) String password);
    @POST("logout/")
    Call<Usuario> logoutUsuario(@Body Usuario usuario);
    @GET("usuarios/{matricula}")
    Call<Usuario> getLivro(@Header("Authorization") String token, @Body Usuario usuario, @Query("matricula") String matricula);

    //LIVROS
    @GET("livros/")
    Call<List<Livro>> getLivros(@Header("Authorization") String token);
    @GET("livros/{codigo}")
    Call<Livro> getLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);
    @PUT("livros/{codigo}")
    Call<Livro> updateLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);
    @DELETE("livros/{codigo}")
    Call<Livro> deleteLivro(@Header("Authorization") String token,@Body Livro livro,@Query("codigo") String codigo);

    //EMPRESTIMOS
    @GET("emprestimos/")
    Call<List<Emprestimo>> getEmprestimos(@Header("Authorization") String token);
    @GET("emprestimos/{codigo}")
    Call<Emprestimo> getEmprestimo(@Header("Authorization") String token,@Body Emprestimo emprestimo,@Query("codigo") String codigo);
    @PUT("emprestimos/{codigo}")
    Call<Emprestimo> updateEmprestimo(@Header("Authorization") String token,@Body Emprestimo Emprestimo,@Query("codigo") String codigo);
    @DELETE("emprestimos/{codigo}")
    Call<Emprestimo> deleteEmprestimo(@Header("Authorization") String token,@Body Emprestimo Emprestimo,@Query("codigo") String codigo);

    //SESSOES
    @GET("sessoes/")
    Call<List<Sessao>> getSessoes(@Header("Authorization") String token);

}
