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
    var activityRule = ActivityScenarioRule(RegistroActivity::class.java)

    @Test
    fun testNombreUsuarioVacioMuestraError() {
        onView(withId(R.id.textInputUsuario))
            .perform(click(), clearText())

        onView(withId(R.id.buttonRegistre)).perform(click())

        onView(withId(R.id.textInputUsuario))
            .check(matches(hasErrorText("El nom ha de tenir mínim 5 caràcters")))
    }
}
