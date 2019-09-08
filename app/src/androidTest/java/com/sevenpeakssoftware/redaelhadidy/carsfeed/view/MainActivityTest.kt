package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule val activityActivityTestRule
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup(){
        activityActivityTestRule.activity
            .supportFragmentManager.beginTransaction()
    }

    /**
     * sample case when mocking Articleusercase and return empty list
     */
    @Test
    @Ignore("To figure out why view is freezing until presenter return with data")
    fun testProgressbarDisplayedAndEmptyMessageAppears(){
        onView(withId(R.id.loadingProgressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyTV)).check(matches(isDisplayed()))

    }

    @Test
    fun testArticleListSuccessfully(){
        onView(withId(R.id.articleRV)).check(matches(isDisplayed()))
    }


}