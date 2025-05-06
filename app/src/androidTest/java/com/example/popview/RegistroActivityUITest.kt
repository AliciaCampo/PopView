package com.example.popview

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.RegistroActivity
import com.google.android.material.textfield.TextInputEditText
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
        // usuario
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputUsuario))
        )).perform(typeText("Alicia"), closeSoftKeyboard())
        // email
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEmail))
        )).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())
        // edad
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEdad))
        )).perform(typeText("22"), closeSoftKeyboard())
        // contraseña
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenya))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())
        // confirmación
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenyaDos))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())

        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())

        // Aquí podrías comprobar cierre o navegación
        activityRule.scenario.close()
    }

    @Test
    fun nomInvalidCurt() {
        // nombre demasiado corto
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputUsuario))
        )).perform(typeText("Ali"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario))
            .check(matches(hasErrorText("El nom ha de tenir mínim 5 caràcters")))
    }

    @Test
    fun nomAmbCaractersNoPermesos() {
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputUsuario))
        )).perform(typeText("Alicia123"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario))
            .check(matches(hasErrorText("El nom només pot contenir lletres")))
    }

    @Test
    fun emailInvalid() {
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEmail))
        )).perform(typeText("user@mal"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEmail))
            .check(matches(hasErrorText("El format de l'email no és vàlid")))
    }


    @Test
    fun edatInvalidaMenor16() {
        // edad <16
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEdad))
        )).perform(typeText("15"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad))
            .check(matches(hasErrorText("Has de ser major de 16 anys")))
    }

    @Test
    fun edatInvalidaNoNumerica() {
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEdad))
        )).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad))
            .check(matches(hasErrorText("L'edat ha de ser un número")))
    }

    @Test
    fun contrasenyaInvalidaSenseNumero() {
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenya))
        )).perform(typeText("Contrasenya@"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenya))
            .check(matches(hasErrorText("La contrasenya ha de contenir al menys un número")))
    }

    @Test
    fun confirmacioContrasenyaIncorrecta() {
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenya))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenyaDos))
        )).perform(typeText("Otra@1234"), closeSoftKeyboard())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenyaDos))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    @Test
    fun avatarInvalid() {
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("imagenloca.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        // Aquí podrías chequear un Toast, pero tu matcher de TextInputLayout no aplica
    }

    @Test
    fun seleccioAvatar() {
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser2.png")).perform(click())
        onView(withId(R.id.imagenAvatar)).check(matches(isDisplayed()))
    }
    @Test
    fun edatInvalidaNoNumerica_mostraErrorEdat() {
        // 1) Rellenamos usuario válido
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputUsuario))
        )).perform(typeText("Alicia"), closeSoftKeyboard())

        // 2) Email válido
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEmail))
        )).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())

        // 3) Contraseña válida
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenya))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())

        // 4) Confirmación válida
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenyaDos))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())

        // 5) Avatar válido
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())

        // 6) Ahora la edad no numérica
        onView(allOf(
            isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEdad))
        )).perform(typeText("abc"), closeSoftKeyboard())

        // 7) Click en Registre (con scrollTo)
        onView(withId(R.id.buttonRegistre))
            .perform(scrollTo(), click())

        // 8) Comprueba error solo en Edad
        onView(withId(R.id.textInputEdad))
            .check(matches(hasErrorText("L'edat ha de ser un número")))
    }

    @Test
    fun confirmacioContrasenyaIncorrecta_mostraErrorEnConfirmacio() {
        // 1) Usuario
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputUsuario))
        )).perform(typeText("Alicia"), closeSoftKeyboard())

        // 2) Email
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEmail))
        )).perform(typeText("alicia@gmail.com"), closeSoftKeyboard())

        // 3) Edad válida
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputEdad))
        )).perform(typeText("22"), closeSoftKeyboard())

        // 4) Contraseña
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenya))
        )).perform(typeText("Pass@1234"), closeSoftKeyboard())

        // 5) Confirmación incorrecta
        onView(allOf(isAssignableFrom(TextInputEditText::class.java),
            isDescendantOfA(withId(R.id.textInputContrasenyaDos))
        )).perform(typeText("Otra@1234"), closeSoftKeyboard())

        // 6) Avatar válido
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())

        // 7) Click en Registre
        onView(withId(R.id.buttonRegistre))
            .perform(scrollTo(), click())

        // 8) Comprueba error solo en confirmación
        onView(withId(R.id.textInputContrasenyaDos))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    /** Matcher auxiliar para TextInputLayout **/
    private fun hasErrorText(errorText: String) = object : TypeSafeMatcher<View>() {
        override fun matchesSafely(view: View): Boolean =
            (view as? TextInputLayout)?.error == errorText

        override fun describeTo(description: Description) {
            description.appendText("TextInputLayout with error: $errorText")
        }
    }
}
