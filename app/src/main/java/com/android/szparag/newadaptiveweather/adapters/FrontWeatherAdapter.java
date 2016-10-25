package com.android.szparag.newadaptiveweather.adapters;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.PicassoColorTransformation;
import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;

/**
 * Created by ciemek on 24/10/2016.
 */

public class FrontWeatherAdapter {

    private FrontWeatherViewHolder viewHolder;
    private Weather weather;
    private Bitmap  backgroundImageBitmap;


    public FrontWeatherAdapter(View frontWeatherParentLayout) {
        viewHolder = new FrontWeatherViewHolder(frontWeatherParentLayout);
    }


    public void setWeather(Weather weather){
        this.weather = weather;
    }

    public void onBind() {
        viewHolder.textDateTime.setText(Computation.getHumanDateFromUnixTime(weather.getUnixTime()));

        viewHolder.textCity.setText(weather.getCity());
        viewHolder.textCoords.setText(weather.getCity()); //todo: make this load coords, as intended

        viewHolder.textTemps.setText(Utils.makeTemperatureMinMaxString(
                weather.getTemperatureMax(), weather.getTemperatureMin())
        );

        viewHolder.textTemp.setText(Utils.makeTemperatureString(weather.getTemperature()));
        viewHolder.textDesc.setText(weather.getWeatherDescription());

        Picasso
                .with(viewHolder.imageWeather.getContext())
                .load(Utils.getIconRes(weather.getWeatherId(), true))
                .into(viewHolder.imageWeather);
    }

    public void setBackgroundImage(Bitmap bitmap) {
        backgroundImageBitmap = bitmap;
    }

    public void onBindBackgroundImage() {
        viewHolder.imageBackground.setImageBitmap(backgroundImageBitmap);
    }

    public ImageView getBackgroundImage() {
        return viewHolder.imageBackground;
    }


    public class FrontWeatherViewHolder {

        @BindView(R.id.item_weather_front_datetime)
        TextView textDateTime;

        @BindView(R.id.item_weather_front_city)
        TextView textCity;

        @BindView(R.id.item_weather_front_coords)
        TextView textCoords;

        @BindView(R.id.item_weather_front_temperatures)
        TextView textTemps;

        @BindView(R.id.item_weather_front_temp)
        TextView textTemp;

        @BindView(R.id.item_weather_front_desc)
        TextView textDesc;

        @BindView(R.id.item_weather_front_image_background)
        ImageView imageBackground;

        @BindView(R.id.item_weather_front_image_weather)
        ImageView imageWeather;

        public FrontWeatherViewHolder(View frontWeatherParentLayout) {
            ButterKnife.bind(this, frontWeatherParentLayout);
        }
    }

}
