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
import com.example.popview.activity.LoginActivity
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun emptyFields_showsEmptyToast() {
        // Preparamos: capturamos el decorView antes
        var decorView: View? = null
        activityRule.scenario.onActivity { decorView = it.window.decorView }

        // Ejecutamos la acción
        onView(withId(R.id.editTextUserID)).perform(clearText())
        onView(withId(R.id.editTextPassword)).perform(clearText())
        onView(withId(R.id.inici)).perform(click())

        // Hacemos la comprobación de Toast FUERA de onActivity
        onView(withText("Siusplau, escriu l'ID de l'usuari i la contrasenya"))
            .inRoot(withDecorView(not(`is`(decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun invalidCredentials_showsErrorToast() {
        var decorView: View? = null
        activityRule.scenario.onActivity { decorView = it.window.decorView }

        onView(withId(R.id.editTextUserID))
            .perform(typeText("baduser"), closeSoftKeyboard())
        onView(withId(R.id.editTextPassword))
            .perform(typeText("badpass"), closeSoftKeyboard())
        onView(withId(R.id.inici)).perform(click())

        onView(withText("ID de l'usuari o contrasenya incorrectes"))
            .inRoot(withDecorView(not(`is`(decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun validCredentials_finishesLoginActivity() {
        // No Toast, solo finish()
        onView(withId(R.id.editTextUserID))
            .perform(typeText("user2131"), closeSoftKeyboard())
        onView(withId(R.id.editTextPassword))
            .perform(typeText("pirineus"), closeSoftKeyboard())
        onView(withId(R.id.inici)).perform(click())

        // Damos un breve margen para el finish
        Thread.sleep(500)
        activityRule.scenario.state.run {
            assert(this == Lifecycle.State.DESTROYED)
        }
    }
}
