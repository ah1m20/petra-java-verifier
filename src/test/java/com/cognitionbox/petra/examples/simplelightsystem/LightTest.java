package com.cognitionbox.petra.examples.simplelightsystem;

import com.cognitionbox.petra.ast.terms.External;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@External
public class LightTest {

    private Light light;

    @Before
    public void setUp() throws Exception {
        light = new Light();
    }

    @After
    public void tearDown() throws Exception {
        light = null;
    }

    @Test
    public void testRun() {
        light.run();
        assertTrue(light.on());
    }

    @Test
    public void testRunAfterRun() {
        light.run();
        light.run();
        assertTrue(light.off());
    }
//
//    @Test
//    public void testRunAfterRunAfterRun() {
//        light.run();
//        light.run();
//        light.run();
//        assertTrue(light.on());
//    }
}