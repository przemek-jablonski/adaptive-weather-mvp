package com.android.szparag.newadaptiveweather.backend;


import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.utils.Computation;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

import io.realm.RealmQuery;
import io.realm.RealmResults;

import static org.junit.Assert.*;


/**
 * Created by ciemek on 10/10/2016.
 */
public class RealmUtilsTest {

    RealmQuery<Weather> mockRealmQuery;
    RealmResults        mockRealmResults;
    RealmUtils          realmUtils;
    long                currentTime;
    Random              random;


    @Before
    public void setUp() throws Exception {
        currentTime = System.currentTimeMillis()/1000L;
        realmUtils = new RealmUtils();
        random = new Random();
//        mockRealmQuery = mock(RealmQuery.class);
//        mockRealmResults = mock(RealmResults.class);
    }

    @Test
    public void testFindClosestTimeValueInRandomListFluctuatingTimeStandard() throws Exception {
        int randomListSize = random.nextInt(2) + 1;
        long validTime = currentTime + Computation.UnixTimeInterval.HOUR_2;
        int fluctuationTimeInterval = Computation.UnixTimeInterval.HOUR_3;
        LinkedList<Weather> weatherList = new LinkedList<>();

        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);
        Weather validWeatherFluctuatingTime = new Weather();
        validWeatherFluctuatingTime.setUnixTime(validTime);
        weatherList.add(validWeatherFluctuatingTime);
        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);

        assertTrue(currentWeather.getUnixTime() < currentTime + fluctuationTimeInterval);
        assertTrue(currentWeather.getUnixTime() > currentTime - fluctuationTimeInterval);
    }

    @Test
    public void testFindClosestTimeValueInRandomListFluctuatingTimeMinor() throws Exception {
        int randomListSize = random.nextInt(1000) + 1000;
        LinkedList<Weather> weatherList = new LinkedList<>();
        long validTime = currentTime + Computation.UnixTimeInterval.MINUTE_1;
        int fluctuationTimeInterval = Computation.UnixTimeInterval.MINUTE_5;

        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);
        Weather validWeatherFluctuatingTime = new Weather();
        validWeatherFluctuatingTime.setUnixTime(validTime);
        weatherList.add(validWeatherFluctuatingTime);
        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);

        assertTrue(currentWeather.getUnixTime() < currentTime + fluctuationTimeInterval);
        assertTrue(currentWeather.getUnixTime() > currentTime - fluctuationTimeInterval);
    }

    @Test
    public void testFindClosestTimeValueInRandomListFluctuatingTimeMajor() throws Exception {
        int randomListSize = random.nextInt(1000) + 1000;
        LinkedList<Weather> weatherList = new LinkedList<>();
        long validTime = currentTime + Computation.UnixTimeInterval.HOUR_3;
        int fluctuationTimeInterval = Computation.UnixTimeInterval.DAY_5;

        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);
        Weather validWeatherFluctuatingTime = new Weather();
        validWeatherFluctuatingTime.setUnixTime(validTime);
        weatherList.add(validWeatherFluctuatingTime);
        weatherList = appendRandomElements(weatherList, randomListSize, true, fluctuationTimeInterval);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);

        assertTrue(currentWeather.getUnixTime() < currentTime + fluctuationTimeInterval);
        assertTrue(currentWeather.getUnixTime() > currentTime - fluctuationTimeInterval);
    }

    @Test
    public void testFindClosestTimeValueInRandomListValidBeginningStrictTime() throws Exception {
        int randomListSize = random.nextInt(100) + 5;
        LinkedList<Weather> weatherList = new LinkedList<>();

        Weather weather = new Weather();
        weather.setUnixTime(currentTime);
        weatherList.add(weather);

        weatherList = appendRandomElements(weatherList, randomListSize, true);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);
        assertEquals(weatherList.getFirst(), currentWeather);
        assertEquals(currentTime, currentWeather.getUnixTime());
    }

    @Test
    public void testFindClosestTimeValueInRandomListValidEndStrictTime() throws Exception {
        int randomListSize = random.nextInt(100) + 5;
        LinkedList<Weather> weatherList = new LinkedList<>();

        weatherList = appendRandomElements(weatherList, randomListSize, true);

        Weather weather = new Weather();
        weather.setUnixTime(currentTime);
        weatherList.add(weather);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);
        assertEquals(weatherList.getLast(), currentWeather);
        assertEquals(currentTime, currentWeather.getUnixTime());
    }

    @Test
    public void testFindClosestTimeValueInRandomListValidMiddleStrictTime() throws Exception {
        int randomListSize = random.nextInt(100) + 5;
        int validItemId;
        LinkedList<Weather> weatherList = new LinkedList<>();

        weatherList = appendRandomElements(weatherList, randomListSize, true);

        validItemId = weatherList.size();
        Weather weather = new Weather();
        weather.setUnixTime(currentTime);
        weatherList.add(weather);

        weatherList = appendRandomElements(weatherList, randomListSize, true);


        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);
        assertEquals(weatherList.get(validItemId), currentWeather);
        assertEquals(currentTime, currentWeather.getUnixTime());
    }

    @Test
    public void testFindClosestTimeValueInRandomListMultipleValidsStrictTime() throws Exception {
        int randomListSize = random.nextInt(9000) + 1000;
        LinkedList<Weather> weatherList = new LinkedList<>();

        weatherList = appendRandomElements(weatherList, randomListSize, false);

        Weather weather = new Weather();
        weather.setUnixTime(currentTime);
        weatherList.add(weather);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);
        assertEquals(currentTime, currentWeather.getUnixTime());
    }

    @Test
    public void testFindClosestTimeValueInListStrictTime() throws Exception {

        Weather weather1 = new Weather();
        weather1.setUnixTime(currentTime - Computation.UnixTimeInterval.MINUTE_30);

        Weather weather2 = new Weather();
        weather2.setUnixTime(currentTime + Computation.UnixTimeInterval.MINUTE_30);

        Weather weather3 = new Weather();
        weather3.setUnixTime(currentTime - Computation.UnixTimeInterval.HOUR_1);

        Weather weather4 = new Weather();
        weather4.setUnixTime(currentTime + Computation.UnixTimeInterval.HOUR_1);

        Weather weather5 = new Weather();
        weather5.setUnixTime(currentTime + Computation.UnixTimeInterval.HOUR_3);

        Weather weather6 = new Weather();
        weather6.setUnixTime(currentTime);

        Weather weather7 = new Weather();
        weather7.setUnixTime(currentTime + Computation.UnixTimeInterval.MINUTE_5);


        LinkedList<Weather> weatherList = new LinkedList<>();
        weatherList.add(weather1);
        weatherList.add(weather2);
        weatherList.add(weather3);
        weatherList.add(weather4);
        weatherList.add(weather5);
        weatherList.add(weather6);
        weatherList.add(weather7);

        Weather currentWeather = realmUtils.findClosestTimeValue(weatherList, currentTime);
        assertEquals(currentTime, currentWeather.getUnixTime());
    }

    private LinkedList appendRandomElements(LinkedList weatherList, int amount, boolean avoidCurrentTime) {
        return appendRandomElements(weatherList, amount, avoidCurrentTime, 0);
    }

    private LinkedList appendRandomElements(LinkedList weatherList, int amount, boolean avoidCurrentTime, long timeFluctuationMargin) {
        Weather weather;
        for (int i= 0; i < amount; ++i) {
            weather = new Weather();
            weather.setUnixTime(random.nextInt((int)currentTime + (Computation.UnixTimeInterval.DAY_5 * 2)));
            if (weather.getUnixTime() < (currentTime + timeFluctuationMargin)
                    && weather.getUnixTime() > (currentTime - timeFluctuationMargin)
                    && avoidCurrentTime) {
                continue;
            }
            weatherList.add(weather);
        }
        return weatherList;
    }
}