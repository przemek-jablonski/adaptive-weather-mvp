package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.views.contracts.BulkWeatherInfoView;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by ciemek on 25/09/2016.
 */
public class BulkWeatherInfoPresenterTest {

    BulkWeatherInfoBasePresenter bulkWeatherInfoBasePresenter;
    WeatherService  mockService;
    BulkWeatherInfoView mockView;


    @Before
    public void setup() {
        mockService = mock(WeatherService.class);

        mockView = mock(BulkWeatherInfoView.class);

//        bulkWeatherInfoBasePresenter = new BulkWeatherInfoPresenter(mockService);
//        bulkWeatherInfoBasePresenter.setView(mockView);
    }

    @Test
    public void testFetchForecast5Day() {

    }

//    @Test
//    public void ifUserIsNullThenNoInteractionWithView() {
//        bulkWeatherInfoBasePresenter.fetchWeather5Day();
//    }





}
