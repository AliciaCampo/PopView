package com.example.popview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)
    @Test
    fun camposVacios_muestraErroresEnAmbosCampos() {
        // Dejamos ambos campos vacíos
        onView(withId(R.id.editTextUserID)).perform(clearText())
        onView(withId(R.id.editTextPassword)).perform(clearText())
        onView(withId(R.id.inici)).perform(click())
        // Comprobamos inmediatamente los errores
        onView(withId(R.id.editTextUserID))
            .check(matches(hasErrorText("L'ID d'usuari és obligatori")))

        onView(withId(R.id.editTextPassword))
            .check(matches(hasErrorText("La contrasenya és obligatòria")))
    }

    @Test
    fun credencialesInvalidas_muestraErrorEnPassword() {
        // Introducimos un user válido para saltar el primer error
        onView(withId(R.id.editTextUserID))
            .perform(typeText("user2131"), closeSoftKeyboard())
        // Contraseña inválida
        onView(withId(R.id.editTextPassword))
            .perform(typeText("badpass"), closeSoftKeyboard())
        onView(withId(R.id.inici)).perform(click())

        // Comprobamos el error en la contraseña
        onView(withId(R.id.editTextPassword))
            .check(matches(hasErrorText("ID o contrasenya incorrectes")))
    }

    @Test
    fun credencialesValidas_finalizaLoginActivity() {
        // Introducimos credenciales correctas
        onView(withId(R.id.editTextUserID))
            .perform(typeText("user2131"), closeSoftKeyboard())
        onView(withId(R.id.editTextPassword))
            .perform(typeText("pirineus"), closeSoftKeyboard())
        onView(withId(R.id.inici)).perform(click())

        // Si la Activity se cierra sin excepciones, test OK
        activityRule.scenario.close()
    }
}
