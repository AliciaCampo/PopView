package com.example.popview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.RegistroActivity
import com.example.popview.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistroActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule<RegistroActivity>(RegistroActivity::class.java)

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
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("22"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
    }

    @Test
    fun nomInvalidCurt() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Ali"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("22"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario)).check(matches(hasErrorText("El nom ha de tenir mínim 5 caràcters")))
    }

    @Test
    fun nomAmbCaractersNoPermesos() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia123"))
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputUsuario)).check(matches(hasErrorText("El nom només pot contenir lletres")))
    }

    @Test
    fun emailInvalid() {
        onView(withId(R.id.textInputEmail)).perform(typeText("user@mal"))
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEmail)).check(matches(hasErrorText("El format de l'email no és vàlid")))
    }

    @Test
    fun edatInvalidaMenor16() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("15"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad)).check(matches(hasErrorText("Has de ser major de 16 anys")))
    }

    @Test
    fun edatInvalidaNoNumerica() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("abc"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputEdad)).check(matches(hasErrorText("L'edat ha de ser un número")))
    }

    @Test
    fun contrasenyaInvalidaSenseNumero() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("22"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Contrasenya@"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Contrasenya@"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenya)).check(matches(hasErrorText("La contrasenya ha de contenir al menys un número")))
    }

    @Test
    fun confirmacioContrasenyaIncorrecta() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("22"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Otra@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser1.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
        onView(withId(R.id.textInputContrasenyaDos)).check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }

    @Test
    fun avatarInvalid() {
        onView(withId(R.id.textInputUsuario)).perform(typeText("Alicia"))
        onView(withId(R.id.textInputEmail)).perform(typeText("alicia@gmail.com"))
        onView(withId(R.id.textInputEdad)).perform(typeText("22"))
        onView(withId(R.id.textInputContrasenya)).perform(typeText("Pass@1234"))
        onView(withId(R.id.textInputContrasenyaDos)).perform(typeText("Pass@1234"))
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("imagenloca.png")).perform(click())
        onView(withId(R.id.buttonRegistre)).perform(click())
    }

    @Test
    fun seleccioAvatar() {
        onView(withId(R.id.imagenAvatar)).perform(click())
        onView(withText("avataruser2.png")).perform(click())
        onView(withId(R.id.imagenAvatar)).check(matches(isDisplayed()))
    }

    // Mètode auxiliar per verificar errors en TextInputLayout
    private fun hasErrorText(errorText: String): org.hamcrest.Matcher<android.view.View> {
        return object : org.hamcrest.TypeSafeMatcher<android.view.View>() {
            override fun matchesSafely(view: android.view.View): Boolean {
                if (view !is com.google.android.material.textfield.TextInputLayout) return false
                return view.error == errorText
            }

            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("TextInputLayout with error: $errorText")
            }
        }
    }
}
