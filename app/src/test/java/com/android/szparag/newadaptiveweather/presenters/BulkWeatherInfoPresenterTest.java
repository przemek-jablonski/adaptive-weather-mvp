package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.views.BaseView;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by ciemek on 25/09/2016.
 */
public class BulkWeatherInfoPresenterTest {

    BasePresenter   basePresenter;
    WeatherService  mockService;
    BaseView        mockView;


    @Before
    public void setup() {
        mockService = mock(WeatherService.class);

        mockView = mock(BaseView.class);

//        basePresenter = new BulkWeatherInfoPresenter(mockService);
//        basePresenter.setView(mockView);
    }

    @Test
    public void testFetchForecast5Day() {

    }

//    @Test
//    public void ifUserIsNullThenNoInteractionWithView() {
//        basePresenter.fetchWeather5Day();
//    }





}
