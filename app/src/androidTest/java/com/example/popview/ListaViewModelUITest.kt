package com.example.popview

import android.content.Intent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.popview.activity.EditLista
import com.example.popview.data.Lista
import com.example.popview.data.Titulo
import com.example.popview.data.Item
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListaViewModelUITest {
    private val listaVacia = Lista(
        id = 0,
        titulo = "",
        descripcion = null,
        esPrivada = false,
        usuarioId = null,
        titulos = mutableListOf()
    )
    @get:Rule
    val activityRule = ActivityScenarioRule<EditLista>(
        Intent(
            ApplicationProvider.getApplicationContext(),
            EditLista::class.java
        ).apply {
            putExtra("lista", listaVacia)
        }
    )
    /** 1) Agregar título en blanco muestra error en campo título */
    @Test
    fun agregarTituloNombreEnBlanco_muestraError() {
        onView(withId(R.id.btnAñadirPelicula)).perform(click())
        onView(withId(R.id.editTextTitulo))
            .check(matches(hasErrorText("El títol és obligatori")))
    }

}
