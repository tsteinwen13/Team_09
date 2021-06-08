package com.tugraz.asd.modernnewsgroupapp


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NewsgroupServerMenuTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun newsgroupServerMenuTest() {
        Thread.sleep(3000);
        val appCompatEditText = onView(
                allOf(withId(R.id.editText_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                1),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.editText_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout2),
                                        1),
                                2),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("test@test.com"), closeSoftKeyboard())

        val materialButton = onView(
                allOf(withId(R.id.button_subscribe), withText("NEXT"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout5),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                2)),
                                0),
                        isDisplayed()))
        materialButton.perform(click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.checkBox), withText("tu-graz.algorithmen"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear_scroll),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.button_finish), withText("FINISH"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout4),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                2)),
                                1),
                        isDisplayed()))
        materialButton2.perform(click())

        val appCompatSpinner = onView(
                allOf(withId(R.id.newsgroups_List),
                        childAtPosition(
                                allOf(withId(R.id.simple_spinner_dropdown_item),
                                        childAtPosition(
                                                withId(R.id.linearLayout2),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatSpinner.perform(click())

        val checkedTextView = onView(
                allOf(withId(android.R.id.text1), withText("news.tugraz.at"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                        isDisplayed()))
        checkedTextView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
