package com.example.popview

import com.example.popview.data.*
import com.example.popview.service.PopViewService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PopViewServiceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: PopViewService

    @Before
    fun configurar() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PopViewService::class.java)
    }

    @After
    fun cerrar() {
        mockWebServer.shutdown()
    }

    @Test
    fun `obtener un usuario por ID`() = runTest {
        val respuestaJson = """
            {
              "id": 1,
              "nom": "Alicia",
              "imatge": "url.jpg",
              "edat": 25,
              "correu": "alicia@example.com",
              "contrasenya": "1234"
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val usuario = service.getUsuari(1)

        assertEquals(1, usuario.id)
        assertEquals("Alicia", usuario.nombre)
        assertEquals("alicia@example.com", usuario.correo)
    }

    @Test
    fun `crear un nuevo usuario`() = runTest {
        val respuestaJson = """
            {
              "id": 2,
              "nom": "Juan",
              "imatge": "img.jpg",
              "edat": 30,
              "correu": "juan@example.com",
              "contrasenya": "abcd"
            }
        """.trimIndent()
        val nuevoUsuario = Usuario(0, "Juan", "img.jpg", 30, "juan@example.com", "abcd")
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val usuario = service.createUser(nuevoUsuario)

        assertEquals("Juan", usuario.nombre)
        assertEquals("juan@example.com", usuario.correo)
    }

    @Test
    fun `obtener todos los usuarios`() = runTest {
        val respuestaJson = """
            [
              {
                "id": 1,
                "nom": "Alicia",
                "imatge": "a.jpg",
                "edat": 25,
                "correu": "alicia@example.com",
                "contrasenya": "1234"
              },
              {
                "id": 2,
                "nom": "Juan",
                "imatge": "b.jpg",
                "edat": 30,
                "correu": "juan@example.com",
                "contrasenya": "abcd"
              }
            ]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val usuarios = service.getAllUsuaris()

        assertEquals(2, usuarios.size)
        assertEquals("Alicia", usuarios[0].nombre)
        assertEquals("Juan", usuarios[1].nombre)
    }

    @Test
    fun `agregar comentario a un título`() = runTest {
        val comentario = Comentario(1, 1, "Muy bueno", 5.0f)
        mockWebServer.enqueue(MockResponse().setResponseCode(200))

        val respuesta = service.agregarComentario(1, 1, comentario)

        assertTrue(respuesta.isSuccessful)
    }

    @Test
    fun `obtener comentarios de un usuario para un título`() = runTest {
        val respuestaJson = """
            [
              {
                "id": 1,
                "usuari_id": 1,
                "comentaris": "Excelente serie",
                "rating": 4.5
              }
            ]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val comentarios = service.obtenerComentarios(1, 1)

        assertEquals(1, comentarios.size)
        assertEquals("Excelente serie", comentarios[0].comentaris)
    }

    @Test
    fun `obtener un título por ID`() = runTest {
        val respuestaJson = """
            {
              "id": 1,
              "nom": "Breaking Bad",
              "descripcio": "Serie de drama",
              "genere": "Drama",
              "edat_recomanada": 18,
              "plataformes": ["Netflix"],
              "rating": 4.8
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val titulo = service.getTitol(1)

        assertEquals("Breaking Bad", titulo.nombre)
        assertEquals("Drama", titulo.genero)
    }

    @Test
    fun `obtener listas públicas`() = runTest {
        val respuestaJson = """
            [
              {
                "id": 1,
                "nom": "Favoritas",
                "usuariId": 1
              }
            ]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val respuesta = service.getAllLlistesPublicas()
        val listas = respuesta.body()

        assertEquals(1, listas?.size)
        assertEquals("Favoritas", listas?.get(0)?.titulos)
    }

    @Test
    fun `obtener títulos de una lista`() = runTest {
        val respuestaJson = """
        [
            { "id": 1, "nom": "Breaking Bad", "descripcio": "Drama", "edat_recomanada": 16, "genere": "Drama", "plataformes": ["Netflix"], "rating": 4.9, "comentaris": [] }
        ]
    """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val resultado = service.getTitolsFromLlista(1)

        assertEquals(1, resultado.size)
        assertEquals("Breaking Bad", resultado[0].nombre)
    }

    @Test
    fun `añadir un título a una lista`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))

        val respuesta = service.addTituloToList(1, 1)

        // No devuelve nada, simplemente se ejecuta sin fallar
        assertEquals(Unit, respuesta)
    }
    @Test
    fun `eliminar un título de una lista`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val respuesta = service.deleteTituloFromLista(1, 1)
        assertEquals(Unit, respuesta)
    }
    @Test
    fun `buscar usuarios por texto`() = runTest {
        val respuestaJson = """
        [
            { "id": 1, "nom": "Alicia", "imatge": "", "edat": 22, "correu": "alicia@example.com", "contrasenya": "1234" }
        ]
    """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val resultado = service.buscarUsuarios("Alicia")

        assertEquals(1, resultado.size)
        assertEquals("Alicia", resultado[0].nombre)
    }

    @Test
    fun `buscar listas públicas por texto`() = runTest {
        val respuestaJson = """
        [
            { "id": 1, "nom": "Favoritos", "usuariId": 1 }
        ]
    """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val resultado = service.buscarListasPublicas("Favoritos").body()

        assertEquals(1, resultado?.size)
        assertEquals("Favoritos", resultado?.get(0)?.titulos)
    }

    @Test
    fun `buscar en todo por texto`() = runTest {
        val respuestaJson = "[]"

        mockWebServer.enqueue(MockResponse().setBody(respuestaJson).setResponseCode(200))

        val resultado = service.buscarTodo("Breaking")

        assertEquals(0, resultado.size)
    }

    @Test
    fun `actualizar un usuario`() = runTest {
        val usuario = Usuario(1, "Alicia", "", 22, "alicia@example.com", "1234")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.updateUsuari(1, usuario)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `actualizar una lista`() = runTest {
        val lista = Lista(id = 1, titulo = "Actualizada", usuarioId = 1, descripcion = "", esPrivada = false)
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.updateLista(1, lista)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `eliminar un usuario`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.deleteUsuari(1)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `eliminar una lista`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.deleteLista(1)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `eliminar un título`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.deleteTitulo(1)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `modificar un comentario`() = runTest {
        val comentario = Comentario(id = 1, usuari_id = 1, comentaris = "Modificado", rating = 4.5f)
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.modificarComentario(1, 1, comentario)
        assertEquals(Unit, resultado)
    }
    @Test
    fun `eliminar un comentario`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val resultado = service.eliminarComentario(1, 1)
        assertEquals(Unit, resultado)
    }
}