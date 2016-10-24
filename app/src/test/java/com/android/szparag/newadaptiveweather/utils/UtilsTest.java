package com.android.szparag.newadaptiveweather.utils;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by ciemek on 24/10/2016.
 */
public class UtilsTest {

    @Test
    public void getFirstDigitCorrectMultipleTest() {
        for (int i=1; i < 10; ++i) {
            getFirstDigitTest(i, i);
            getFirstDigitTest((i*100)+1, i);
            getFirstDigitTest((i*100)+50, i);
            getFirstDigitTest((i*100)+75, i);
            getFirstDigitTest((i*100)+99, i);
        }
    }

    @Test
    public void getFistDigitRandomMultipleTest() {
        Random random = new Random();
        for (int i=1; i < 10; ++i) {
            getFirstDigitTest(i, i);
            getFirstDigitTest((i*100)+random.nextInt(99), i);
            getFirstDigitTest((i*100)+random.nextInt(99), i);
            getFirstDigitTest((i*100)+random.nextInt(99), i);
            getFirstDigitTest((i*100)+random.nextInt(99), i);
        }
    }

    public void getFirstDigitTest(int givenInt, int expected){
        int actual = Utils.getFirstDigit(givenInt);
        System.out.println("given: \t" + givenInt + ", expected: \t" + expected + ", actual: \t" + actual);
        assertEquals(actual, expected);
    }
}