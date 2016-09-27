package com.android.szparag.newadaptiveweather.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.WeatherForecastItem;
import com.android.szparag.newadaptiveweather.utils.Utils;

/**
 * Created by ciemek on 26/09/2016.
 */
public class MainAdapter extends BaseAdapter<WeatherForecastItem> {
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public MainAdapter(@Nullable RecyclerOnPosClickListener clickListener) {
        super(clickListener);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.recycler_item_weather_row_basic,
                                parent,
                                false
                        )
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherForecastItem item = items.get(position);
        ((MainViewHolder) holder).textDay.setText(item.calculationUTCTime);
        ((MainViewHolder) holder).textShortDesc.setText(item.weather.get(0).description);
        ((MainViewHolder) holder).textDetail.setText(Utils.makeWeatherDetailString(item));
        ((MainViewHolder) holder).textTemperature.setText(Utils.kelvinToCelsiusRoundDebug(item.main.temp));
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView textDay;
        TextView textShortDesc;
        TextView textDetail;
        TextView textTemperature;

        public MainViewHolder(View itemView) {
            super(itemView);

            textDay = (TextView) itemView.findViewById(R.id.recycler_item_weather_row_basic_day);
            textShortDesc = (TextView) itemView.findViewById(R.id.recycler_item_weather_row_basic_shortdesc);
            textDetail = (TextView) itemView.findViewById(R.id.recycler_item_weather_row_basic_detail);
            textTemperature = (TextView) itemView.findViewById(R.id.recycler_item_weather_row_basic_temperature);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerOnPosClickListener.OnPosClick(view, getLayoutPosition());
                }
            });
        }

    }
}
