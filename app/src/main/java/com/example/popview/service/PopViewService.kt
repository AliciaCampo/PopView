package com.example.popview.service

import com.example.popview.clase.Usuario
import com.example.popview.data.Lista
import com.example.popview.data.Titulo
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface PopViewService {
    @GET("/usuaris/{usuari_id}")
    suspend fun getUsuari(@Path("usuari_id") usuariId: Int): Usuario
    @GET("/llistes/{llista_id}")
    suspend fun getLlista(@Path("llista_id") llistaId: Int): Lista
    @GET("/titols/{titol_id}")
    suspend fun getTitol(@Path("titol_id") titolId: Int): Titulo
    @POST("/usuaris")
    suspend fun createUser(usuario: Usuario): Usuario
    @POST("/llistes")
    suspend fun createLista(lista: Lista): Lista
    @POST("/titols")
    suspend fun createTitulo(titulo: Titulo): Titulo
    @DELETE("/usuaris/{usuari_id}")
    suspend fun deleteUsuari(@Path("usuari_id") usuariId: Int)
    @DELETE("/llistes/{llista_id}")
    suspend fun deleteLista(@Path("llista_id") llistaId: Int)
    @DELETE("/titols/{titol_id}")
    suspend fun deleteTitulo(@Path("titol_id") titolId: Int)
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
                    .baseUrl("https://52.72.183.45/")
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
        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}