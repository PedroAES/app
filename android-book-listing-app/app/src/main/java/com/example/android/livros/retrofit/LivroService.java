package com.example.android.livros.retrofit;

import com.example.android.livros.model.Emprestimo;
import com.example.android.livros.model.Livro;
import com.example.android.livros.model.Sessao;
import com.example.android.livros.model.TokenAuthentication;
import com.example.android.livros.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LivroService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    //USUARIOS
    @POST("api-token-auth/")
    Call<TokenAuthentication> loginUsuario(@Body Usuario usuario);
    @GET("usuarios/{matricula}")
    Call<Usuario> getUsuario(@Header("Authorization") String token,@Path("matricula") String matricula);
    @GET("usuarios/")
    Call<List<Usuario>> getUsuarios();
    @POST("usuarios/")
    Call<Usuario> addUsuarios(@Body Usuario usuario);

    //LIVROS
    @GET("livros/")
    Call<List<Livro>> getLivros(@Header("Authorization") String token);
    @POST("livros/")
    Call<Livro> addLivros(@Header("Authorization") String token, @Body Livro livro);

    //EMPRESTIMOS
    @GET("emprestimos/")
    Call<List<Emprestimo>> getEmprestimos(@Header("Authorization") String token);
    @POST("emprestimos/")
    Call<Emprestimo> addEmprestimo(@Header("Authorization") String token,@Body Emprestimo emprestimo);
    @PUT("emprestimos/{codigo}")
    Call<Emprestimo> updateEmprestimo(@Header("Authorization") String token,@Path("codigo") String codigo, @Body Emprestimo emprestimo);

    //SESSOES
    @GET("sessoes/")
    Call<List<Sessao>> getSessoes(@Header("Authorization") String token);

}
