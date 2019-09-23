package com.example.android.books.retrofit;

import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.Sessao;
import com.example.android.books.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LivroService {

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })

    //USUARIOS
    @POST("login/")
    Call<Usuario> loginUsuario();
    @POST("logout/")
    Call<Usuario> logoutUsuario(@Body Usuario usuario);
    @GET("usuarios/{matricula}")
    Call<Usuario> getLivro(@Body Usuario usuario,@Path("matricula") String matricula);

    //AUTH-TOKEN
    @POST("api-token-auth/")
    Call<Usuario> adquirirToken(@Body Usuario usuario);

    //LIVROS
    @GET("livros/")
    Call<List<Livro>> getLivros();
    @GET("livros/{codigo}")
    Call<Livro> getLivro(@Body Livro livro,@Path("codigo") String codigo);
    @PUT("livros/{codigo}")
    Call<Livro> updateLivro(@Body Livro livro,@Path("codigo") String codigo);
    @DELETE("livros/{codigo}")
    Call<Livro> deleteLivro(@Body Livro livro,@Path("codigo") String codigo);

    //EMPRESTIMOS
    @GET("emprestimos/")
    Call<List<Emprestimo>> getEmprestimos();
    @GET("emprestimos/{codigo}")
    Call<Emprestimo> getEmprestimo(@Body Emprestimo emprestimo,@Path("codigo") String codigo);
    @PUT("emprestimos/{codigo}")
    Call<Emprestimo> updateEmprestimo(@Body Emprestimo Emprestimo,@Path("codigo") String codigo);
    @DELETE("emprestimos/{codigo}")
    Call<Emprestimo> deleteEmprestimo(@Body Emprestimo Emprestimo,@Path("codigo") String codigo);

    //SESSOES
    @GET("sessoes/")
    Call<List<Sessao>> getSessoes();

}
