package com.resatisfy.android_lib;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented in_app_msg, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under in_app_msg.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.resatisfy.android_lib.in_app_msg", appContext.getPackageName());
    }
}
