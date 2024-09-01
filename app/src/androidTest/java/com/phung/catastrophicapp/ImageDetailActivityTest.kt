package com.phung.catastrophicapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.phung.catastrophicapp.ui.ImageDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageDetailActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ImageDetailActivity::class.java)

    @Test
    fun testImageViewDetailDisplayed() {
        onView(withId(R.id.photoView)).check(matches(isDisplayed()))
    }
}