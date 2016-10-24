package com.android.szparag.newadaptiveweather.adapters;

import android.view.View;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ciemek on 22/10/2016.
 */

public class CurrentWeatherAdapter {

    private CurrentWeatherViewHolder viewHolder;
    private Weather weather;

    public CurrentWeatherAdapter(View currentWeatherParentLayout) {
        viewHolder = new CurrentWeatherViewHolder(currentWeatherParentLayout);
    }


    public void setCurrentWeather(Weather weather) {
        this.weather = weather;
    }

    public void onBind() {

        viewHolder.forecastCurrentTemperature.setText(Utils.makeTemperatureString(weather.getTemperature()));
        viewHolder.forecastCurrentTemperatures.setText(Utils.makeTemperatureMinMaxString(weather.getTemperatureMax(), weather.getTemperatureMin()));

        viewHolder.forecastCurrentDate.setText(Computation.getHumanDateFromUnixTime(weather.getUnixTime()));

        viewHolder.forecastCurrentShortDesc.setText(weather.getWeatherMain());
        viewHolder.forecastCurrentDesc.setText(weather.getWeatherDescription());

        viewHolder.forecastCurrentPressureVal.setText(Utils.makeStringRoundedFloat(weather.getPressureAtmospheric()));
        viewHolder.forecastCurrentHumidityVal.setText(Utils.makeStringRoundedFloat(weather.getHumidityPercent()));

        viewHolder.forecastCurrentWindspeedVal.setText(Utils.makeStringRoundedFloat(weather.getWindSpeed()));
        viewHolder.forecastCurrentWinddirectionVal.setText(Utils.makeStringRoundedFloat(weather.getWindDirection()));

        viewHolder.forecastCurrentCloudsVal.setText(Utils.makeStringRoundedFloat(weather.getCloudsPercent()));

        viewHolder.forecastCurrentRainVal.setText(Utils.makeStringRoundedFloat(weather.getRainPast3h()));
        viewHolder.forecastCurrentSnowVal.setText(Utils.makeStringRoundedFloat(weather.getSnowPast3h()));
    }


    public class CurrentWeatherViewHolder {

        @BindView(R.id.item_weather_current_shortdesc)
        TextView forecastCurrentShortDesc;

        @BindView(R.id.item_weather_current_tempmaxmin)
        TextView forecastCurrentTemperatures;

        @BindView(R.id.item_weather_current_temperature)
        TextView forecastCurrentTemperature;

        @BindView(R.id.item_weather_current_desc)
        TextView forecastCurrentDesc;

        @BindView(R.id.item_weather_current_pressure_val)
        TextView forecastCurrentPressureVal;

        @BindView(R.id.item_weather_current_humidity_val)
        TextView forecastCurrentHumidityVal;

        @BindView(R.id.item_weather_current_windspeed_val)
        TextView forecastCurrentWindspeedVal;

        @BindView(R.id.item_weather_current_winddirection_val)
        TextView forecastCurrentWinddirectionVal;

        @BindView(R.id.item_weather_current_clouds_val)
        TextView forecastCurrentCloudsVal;

        @BindView(R.id.item_weather_current_rain_val)
        TextView forecastCurrentRainVal;

        @BindView(R.id.item_weather_current_snow_val)
        TextView forecastCurrentSnowVal;

        @BindView(R.id.item_weather_current_date)
        TextView forecastCurrentDate;

        public CurrentWeatherViewHolder(View currentWeatherParentLayout) {
            ButterKnife.bind(this, currentWeatherParentLayout);
        }

    }

}
