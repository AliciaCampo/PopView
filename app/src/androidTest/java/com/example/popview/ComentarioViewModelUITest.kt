package com.example.popview

import android.content.Intent
import android.view.View
import android.widget.RatingBar
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.ValoracionTituloActivity
import com.example.popview.data.Titulo
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComentarioViewModelUITest {
    @get:Rule
    val rule = ActivityScenarioRule<ValoracionTituloActivity>(
        Intent(
            ApplicationProvider.getApplicationContext(),
            ValoracionTituloActivity::class.java
        ).apply {
            putExtra("titulo", Titulo(
                id = 123,
                nombre = "Test Movie",
                description = "Desc",
                imagen = "test.jpg",
                rating = 3f,
                platforms = "Netflix",
                genero = "Drama",
                edadRecomendada = 13,
                comments = emptyList()
            ))
        }
    )
    private fun setRating(value: Float): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> = isAssignableFrom(RatingBar::class.java)
        override fun getDescription(): String = "Set rating on RatingBar"
        override fun perform(uiController: UiController?, view: View?) {
            (view as? RatingBar)?.rating = value
        }
    }
    @Test
    fun testAfegirComentariValidUI() {
        val comentariText = "Molt bon servei"
        // 1) Escribe comentario
        onView(withId(R.id.editTextComment))
            .perform(typeText(comentariText), closeSoftKeyboard())
        // 2) Pone rating válido
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(3.5f))
        // 3) Cierra teclado y click enviar
        onView(withId(R.id.buttonSend))
            .perform(closeSoftKeyboard(), click())
        // 4) Comprueba que aparece el propio texto en pantalla
        onView(withText(comentariText))
            .check(matches(isDisplayed()))
    }
    @Test
    fun testRatingInvalid_showsError() {
        // 1) Escribe un comentario “válido”
        onView(withId(R.id.editTextComment))
            .perform(typeText("Que maca"), closeSoftKeyboard())
        // 2) Pone un rating fuera de rango (>4)
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(5f))
        // 3) Click enviar
        onView(withId(R.id.buttonSend)).perform(click())
        // 4) Muy importante: espera un ratito a que el error se propague al EditText
        Thread.sleep(500)
        // 5) Ahora checkea el error
        onView(withId(R.id.editTextComment))
            .check(matches(hasErrorText("La puntuación debe estar entre 0 y 4")))
    }
    @Test
    fun testComentariBuit_showsError() {
        // Deja el comentario vacío y rating válido
        onView(withId(R.id.editTextComment))
            .perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(2f))
        // Click enviar (cerrando teclado primero)
        onView(withId(R.id.buttonSend))
            .perform(closeSoftKeyboard(), click())
        // Comprueba que el EditText muestra el error
        onView(withId(R.id.editTextComment))
            .check(matches(hasErrorText("El comentario no puede estar vacío")))
    }
}