package com.tugraz.asd.modernnewsgroupapp

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EditNewsgroupAliasTest {

    private var serverName: String? = null
    private var serverAlias: String? = null

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    private fun init() {
        Thread.sleep(3000)

        val inputName = onView(withId(R.id.editText_name)).check(matches(isDisplayed()))
        val inputEmail = onView(withId(R.id.editText_email)).check(matches(isDisplayed()))

        val inputServer = onView(withId(R.id.editText_newsgroupServer)).check(matches(isDisplayed()))
        val inputAlias = onView(withId(R.id.editText_serverAlias)).check(matches(isDisplayed()))

        inputName.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard())
        inputEmail.perform(ViewActions.replaceText("test@test.at"), ViewActions.closeSoftKeyboard())

        inputAlias.perform(ViewActions.replaceText("AliasTest"), ViewActions.closeSoftKeyboard())

        serverName = getText(inputServer)
        serverAlias = getText(inputAlias)

        onView(withText("NEXT")).perform(ViewActions.click())
        onView(withText("FINISH")).perform(ViewActions.click())
    }

    @Before
    fun clearDb(){
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("newsgroup.db")
    }

    @Test
    fun checkIfNewsgroupServerDisplayed()
    {
        init()
        onView(withId(R.id.button_edit_newsgroup)).perform(ViewActions.click())
        onView(withId(R.id.header_newsgroup_name)).check(matches(withText(containsString(serverName))))
    }

    @Test
    fun checkIfNewsgroupServerNotDisplayed()
    {
        init()
        val matchString = "news.KFGraz.at"
        onView(withId(R.id.button_edit_newsgroup)).perform(ViewActions.click())
        onView(withId(R.id.header_newsgroup_name)).check(matches(not(withText(containsString(matchString)))))
    }

    @Test
    fun checkIfNewsgroupAliasDisplayed()
    {
        init()
        onView(withId(R.id.button_edit_newsgroup)).perform(ViewActions.click())
        onView(withId(R.id.editText_newsgroup_alias)).check(matches(withText(containsString(serverAlias))))
    }

    @Test
    fun checkIfNewsgroupAliasDisplayedAtShowNewsgroups()
    {
        init()
        onView(withId(R.id.newsgroups_List)).check(matches(withSpinnerText(containsString(serverAlias))))
    }

    @Test
    fun checkIfNewsgroupAliasIsCorrect()
    {
        init()
        onView(withId(R.id.newsgroups_List)).check(matches(withSpinnerText(not(containsString("bernisNewsgroup")))))
    }
 
    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }
}