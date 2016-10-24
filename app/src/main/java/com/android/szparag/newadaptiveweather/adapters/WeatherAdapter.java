package com.android.szparag.newadaptiveweather.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by ciemek on 26/09/2016.
 */
public class WeatherAdapter extends BaseAdapter<Weather> {

//    private RealmResults<Weather> items;


    //todo: implement butterknife injects here

    public WeatherAdapter(@Nullable RecyclerOnPosClickListener clickListener) {
        super(clickListener);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.item_recycler_weather_row_basic,
                                parent,
                                false
                        )
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindWeatherViewHolder(((WeatherViewHolder) holder), position);
    }

    private void onBindWeatherViewHolder(WeatherViewHolder holder, int position) {
        Weather item = items.get(position);
        holder.textDay.setText(Computation.getHumanDateFromUnixTime(item.getUnixTime()));
        holder.textShortDesc.setText(item.getWeatherDescription());
        holder.textDetail.setText(Utils.makeWeatherDetailString(item));
        holder.textTemperature.setText(Utils.makeTemperatureString(item.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycler_item_weather_row_basic_day)     TextView textDay;
        @BindView(R.id.recycler_item_weather_row_basic_shortdesc) TextView textShortDesc;
        @BindView(R.id.recycler_item_weather_row_basic_detail)  TextView textDetail;
        @BindView(R.id.recycler_item_weather_row_basic_temperature) TextView textTemperature;

        public WeatherViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            if (recyclerOnPosClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerOnPosClickListener.OnPosClick(view, getLayoutPosition());
                    }
                });
            }

        }

    }
}
