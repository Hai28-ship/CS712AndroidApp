package com.example.cs712androidapp
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class Assignment6UITest {

    private lateinit var device: UiDevice

    @Before
    fun setup() {
        device = UiDevice.getInstance(
            InstrumentationRegistry.getInstrumentation()
        )

        device.pressHome()

        val context = InstrumentationRegistry.getInstrumentation().context
        val intent = context.packageManager
            .getLaunchIntentForPackage("com.example.cs712androidapp")

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        device.wait(
            Until.hasObject(By.pkg("com.example.cs712androidapp").depth(0)),
            5000
        )
    }

    @Test
    fun testNavigationAndContent() {

        val startButton = device.findObject(
            UiSelector().text("Start Activity Explicitly")
        )

        assertTrue(startButton.exists())
        startButton.click()

        device.wait(
            Until.hasObject(By.textContains("challenge")),
            3000
        )

        val challengeText = device.findObject(
            UiSelector().textContains("challenge")
        )

        assertTrue(challengeText.exists())
    }
}