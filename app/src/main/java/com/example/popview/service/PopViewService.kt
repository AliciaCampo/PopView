package com.example.popview.service

import com.example.popview.data.Comentario
import com.example.popview.data.Usuario
import com.example.popview.data.Lista
import com.example.popview.data.ListaPublica
import com.example.popview.data.Titulo
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.logging.HttpLoggingInterceptor

interface PopViewService {
    @GET("usuaris/{usuari_id}")
    suspend fun getUsuari(@Path("usuari_id") usuariId: Int): Usuario
    @PUT("usuaris/{usuari_id}")
    suspend fun updateUsuari(@Path("usuari_id") usuariId: Int, @Body usuario: Usuario): Response<Void>
    @GET("usuaris/")
    suspend fun getAllUsuaris(): List<Usuario>
    @GET("llistes/{llista_id}")
    suspend fun getLlista(@Path("llista_id") llistaId: Int): Lista
    @GET("llistes/")
    suspend fun getAllLlistes(): List<Lista>
    @GET("llistes/publicas")
    suspend fun getAllLlistesPublicas(): Response <List<ListaPublica>>

    @GET("titols/{titol_id}")
    suspend fun getTitol(@Path("titol_id") titolId: Int): Titulo
    @GET("titols/")
    suspend fun getAllTitols(): List<Titulo>
    @POST("usuaris/")
    suspend fun createUser(usuario: Usuario): Usuario
    @POST("llistes/")
    suspend fun createLista(@Body lista: Lista): Lista
    @POST("titols/")
    suspend fun createTitulo(@Body titulo: Titulo): Titulo
    @DELETE("usuaris/{usuari_id}")
    suspend fun deleteUsuari(@Path("usuari_id") usuariId: Int)
    @DELETE("llistes/{llista_id}")
    suspend fun deleteLista(@Path("llista_id") llistaId: Int)
    @DELETE("titols/{titol_id}")
    suspend fun deleteTitulo(@Path("titol_id") titolId: Int)
    @PUT("llistes/{llista_id}")
    suspend fun updateLista(@Path("llista_id") id: Int, @Body lista: Lista)
    @GET("llistes/{llista_id}/titols")
    suspend fun getTitolsFromLlista(@Path("llista_id") llistaId: Int): List<Titulo>
    @POST("llistes/{llista_id}/titols/{titol_id}")
    suspend fun addTituloToList(
        @Path("llista_id") llistaId: Int,
        @Path("titol_id") titolId: Int
    )
    @DELETE("llistes/{llista_id}/titols/{titol_id}")
    suspend fun deleteTituloFromLista(
        @Path("llista_id") llistaId: Int,
        @Path("titol_id") titolId: Int
    )
    // Nuevos métodos de búsqueda
    @GET("buscar/usuarios")
    suspend fun buscarUsuarios(@Query("query") query: String): List<Usuario>

    // Buscar listas públicas por título usando un parámetro de consulta
    @GET("listas/publicas/buscar")
    suspend fun buscarListasPublicas(@Query("titulo") titulo: String):Response <List<ListaPublica>>

    @GET("buscar/todo")
    suspend fun buscarTodo(@Query("query") query: String): List<Any>
    @GET("usuaris/{usuari_id}/llistes")
    suspend fun getLlistesByUsuari(@Path("usuari_id") usuariId: Int): List<Lista>
    //comentarios y rating
    @POST("usuaris/{usuari_id}/titols/{titol_id}/comentarios/")
    suspend fun agregarComentario(
        @Path("usuari_id") usuariId: Int,
        @Path("titol_id") titolId: Int,
        @Body comentario: Comentario
    ): Response<Void>

    @GET("usuaris/{usuari_id}/titols/{titol_id}/comentarios/")
    suspend fun obtenerComentarios(
        @Path("usuari_id") usuariId: Int,
        @Path("titol_id") titolId: Int
    ): List<Comentario>

    @GET("titols/{titol_id}/comentarios/")
    suspend fun obtenerTodosLosComentarios(
        @Path("titol_id") titolId: Int
    ): Response<List<Comentario>>


    @PUT("usuaris/{usuari_id}/titols/{titol_id}/comentarios/")
    suspend fun modificarComentario(
        @Path("usuari_id") usuariId: Int,
        @Path("titol_id") titolId: Int,
        @Body comentario: Comentario
    ): Response<Void>

    @DELETE("usuaris/{usuari_id}/titols/{titol_id}/comentarios/")
    suspend fun eliminarComentario(
        @Path("usuari_id") usuariId: Int,
        @Path("titol_id") titolId: Int
    ): Response<Void>



}
    class PopViewAPI{
        private var mAPI : PopViewService? = null
        @Synchronized
        fun API(): PopViewService {
            if (mAPI == null){
                val client: OkHttpClient = getUnsafeOkHttpClient()
                val gsondateformat= GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://52.72.183.45")
                    .client(getUnsafeOkHttpClient())
                    .build()
                    .create(PopViewService::class.java)
            }
            return mAPI!!
        }
    }
private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }
            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        )
        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // Mostra cos de la petició i resposta
        builder.addInterceptor(logging)

        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}