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

    /** 1) Agregar título exitoso */
    @Test
    fun agregarTituloExitoso_muestraItemEnRecycler() {
        // 1. Introducir el título de la lista
        onView(withId(R.id.editTextTitulo))
            .perform(typeText("Mi primera lista"), closeSoftKeyboard())
        // 2. Marcar switch como privada
        onView(withId(R.id.switchPrivada))
            .perform(click())
        // 3. Introducir nombre de película y pulsar añadir
        onView(withId(R.id.editTextPelicula))
            .perform(typeText("Mi primera lista"), closeSoftKeyboard())
        onView(withId(R.id.btnAñadirPelicula))
            .perform(click())
        // 4. Verificar que el RecyclerView contiene un ítem con ese texto
        onView(withId(R.id.recyclerViewPeliculas))
            .check(matches(hasDescendant(withText("Mi primera lista"))))
    }

    /** 2) Agregar título en blanco muestra error en campo título */
    @Test
    fun agregarTituloNombreEnBlanco_muestraError() {
        onView(withId(R.id.btnAñadirPelicula)).perform(click())
        onView(withId(R.id.editTextTitulo))
            .check(matches(hasErrorText("El títol és obligatori")))
    }

    /** 3) Agregar título con ID duplicado muestra Toast y no añade */
    @Test
    fun agregarTituloIdDuplicado_noAgregaYMuestraToast() {
        // Cierra el escenario abierto por la regla
        activityRule.scenario.close()
        // Crea una lista que ya contiene un Titulo con id=1
        val listaDuplicada = listaVacia.copy(
            titulos = mutableListOf(
                Titulo(
                    imagen = "",
                    nombre = "Lista duplicada",
                    description = "",
                    genero = null,
                    edadRecomendada = null,
                    platforms = "",
                    rating = 0f,
                    comments = null,
                    id = 1
                )
            )
        )
        // Vuelve a lanzar la Activity con esta lista inicial
        val scenario = ActivityScenario.launch<EditLista>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                EditLista::class.java
            ).apply { putExtra("lista", listaDuplicada) }
        )

        // Intenta añadir de nuevo ese mismo título
        onView(withId(R.id.editTextPelicula))
            .perform(typeText("Lista duplicada"), closeSoftKeyboard())
        onView(withId(R.id.btnAñadirPelicula)).perform(click())

        // Comprueba que sigue habiendo sólo 1 ítem
        onView(withId(R.id.recyclerViewPeliculas))
            .check(matches(hasDescendant(withText("Lista duplicada"))))

        // Comprueba que aparece un Toast con el mensaje
        scenario.onActivity { activity ->
            onView(withText("Ya existe un título con ese ID"))
                .inRoot(withDecorView(not(`is`(activity.window.decorView))))
                .check(matches(isDisplayed()))
        }
        scenario.close()
    }

    /** 4) Eliminar un título existente lo quita de la lista */
    @Test
    fun eliminarTituloExistente_quitaItem() {
        activityRule.scenario.close()
        // Lista con un elemento para eliminar
        val listaParaEliminar = listaVacia.copy(
            titulos = mutableListOf(
                Titulo(
                    imagen = "",
                    nombre = "Para eliminar",
                    description = "",
                    genero = null,
                    edadRecomendada = null,
                    platforms = "",
                    rating = 0f,
                    comments = null,
                    id = 2
                )
            )
        )
        val scenario = ActivityScenario.launch<EditLista>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                EditLista::class.java
            ).apply { putExtra("lista", listaParaEliminar) }
        )

        // Pulsar el botón de eliminar que está junto al texto "Para eliminar"
        onView(allOf(
            withId(R.id.btnEliminar),                 // tu ImageView de eliminar lista
            hasSibling(withText("Para eliminar"))     // asegura que es el de ese ítem
        )).perform(click())

        // Confirmar el AlertDialog
        onView(withText("Confirmar")).perform(click())

        // Verificar que ya no aparece "Para eliminar"
        onView(withText("Para eliminar")).check(matches(not(isDisplayed())))

        scenario.close()
    }
}
