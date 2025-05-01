package com.example.popview

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.popview.activity.RegistroActivity
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComentarioViewModelUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(RegistroActivity::class.java)

    private fun setRating(value: Float): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(RatingBar::class.java)
            }
            override fun getDescription(): String {
                return "Set rating on RatingBar"
            }
            override fun perform(uiController: UiController?, view: View?) {
                if (view is RatingBar) {
                    view.rating = value
                }
            }
        }
    }

    @Test
    fun testAfegirComentariValidUI() {
        val comentariText = "Molt bon servei"
        onView(withId(R.id.editTextComment))
            .perform(typeText(comentariText), closeSoftKeyboard())
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(3.5f))
        onView(withId(R.id.buttonSend)).perform(click())
        onView(withText(comentariText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testComentariBuit() {
        onView(withId(R.id.editTextComment))
            .perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(3.5f))
        onView(withId(R.id.buttonSend)).perform(click())
    }

    @Test
    fun testRatingInvalid() {
        val comentariText = "Molt bon servei"
        onView(withId(R.id.editTextComment))
            .perform(typeText(comentariText), closeSoftKeyboard())
        onView(withId(R.id.ratingBarComment))
            .perform(setRating(6f))
        onView(withId(R.id.buttonSend)).perform(click())
        onView(withText("La puntuación debe estar entre 0 y 4"))
            .check(matches(isDisplayed()))
    }
}

