package com.example.popview

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.RegistroActivity
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistroActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegistroActivity::class.java)

    @Test
    fun visualitzacioInicial() {
        onView(withId(R.id.textInputUsuario)).check(matches(isDisplayed()))
        onView(withId(R.id.textInputEmail)).check(matches(isDisplayed()))
        onView(withId(R.id.textInputEdad)).check(matches(isDisplayed()))
        onView(withId(R.id.textInputContrasenya)).check(matches(isDisplayed()))
        onView(withId(R.id.textInputContrasenyaDos)).check(matches(isDisplayed()))
        onView(withId(R.id.imagenAvatar)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonRegistre)).check(matches(isDisplayed()))
    }

    @Test
    fun registreAmbDadesValides() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("22"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())

        // Aquí podrías comprobar si se cierra, o si hay un Toast de confirmación
    }

    @Test
    fun nomInvalidCurt() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Ali"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("22"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario)).check(matches(hasErrorText("El nom ha de tenir mínim 5 caràcters")))
    }

    @Test
    fun nomAmbCaractersNoPermesos() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia123"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario)).check(matches(hasErrorText("El nom només pot contenir lletres")))
    }

    @Test
    fun emailInvalid() {
        onView(withId(R.id.textInputEmail)).perform(typeText("user@mal"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEmail)).check(matches(hasErrorText("El format de l'email no és vàlid")))
    }

    @Test
    fun edatInvalidaMenor16() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("15"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad)).check(matches(hasErrorText("Has de ser major de 16 anys")))
    }

    @Test
    fun edatInvalidaNoNumerica() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad)).check(matches(hasErrorText("L'edat ha de ser un número")))
    }

    @Test
    fun contrasenyaInvalidaSenseNumero() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("22"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Contrasenya@"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Contrasenya@"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenya)).check(matches(hasErrorText("La contrasenya ha de contenir al menys un número")))
    }

    @Test
    fun confirmacioContrasenyaIncorrecta() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("22"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Otra@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenyaDos)).check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    @Test
    fun avatarInvalid() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"), closeSoftKeyboard())
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.textInputEdad)).perform(typeText("22"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("imagenloca.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())

        // Podrías añadir verificación si lanza Toast o error de selección
    }

    @Test
    fun seleccioAvatar() {
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser2.png")).perform(click())
        onView(withId(R.id.imagenAvatar)).check(matches(isDisplayed()))
    }

    // Matcher auxiliar
    private fun hasErrorText(errorText: String) = object : TypeSafeMatcher<View>() {
        override fun matchesSafely(view: View): Boolean {
            return (view as? TextInputLayout)?.error == errorText
        }

        override fun describeTo(description: Description) {
            description.appendText("TextInputLayout with error: $errorText")
        }
    }
}
