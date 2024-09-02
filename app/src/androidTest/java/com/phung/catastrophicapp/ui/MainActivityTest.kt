package com.phung.catastrophicapp.ui

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phung.catastrophicapp.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testRecyclerViewIsDisplayed() {
        // check recyclerView should be displayed
        onView(withId(R.id.catRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerViewScrollAndLoadedImages() {
        // recyclerView should be displayed
        onView(withId(R.id.catRecyclerView)).check(matches(isDisplayed()))

        SystemClock.sleep(5000)

        // Scroll to the 20 position in list
        onView(withId(R.id.catRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))

        // check if an item at position 0 has an image display
        onView(withId(R.id.catRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testClickImageAndOpenImageDetailActivity() {
        // Ensure the RecyclerView is displayed
        onView(withId(R.id.catRecyclerView)).check(matches(isDisplayed()))

        SystemClock.sleep(5000)

        // Perform a click on the first item in the RecyclerView
        onView(withId(R.id.catRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // Verify that an Intent was sent to launch ImageDetailActivity
        Intents.intended(hasComponent(ImageDetailActivity::class.java.name))
    }
}