package com.example.popview

import com.example.popview.service.PopViewService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PopViewServiceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: PopViewService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PopViewService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsuari returns correct user`() = runTest {
        val mockJson = """
            {
              "id": 1,
              "nombre": "Alicia",
              "correo": "alicia@example.com"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(mockJson)
                .setResponseCode(200)
        )

        val result = service.getUsuari(1)

        assertThat(result.nombre, equalTo("Alicia"))
        assertThat(result.correo, equalTo("alicia@example.com"))
        assertThat(result.id, equalTo(1))
    }
}
