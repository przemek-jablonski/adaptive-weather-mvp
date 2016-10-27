package com.android.szparag.newadaptiveweather.adapters;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ciemek on 26/09/2016.
 */
public class WeatherRecyclerViewAdapter extends BaseRecyclerViewAdapter<Weather> {

//    private RealmResults<Weather> items;


    //todo: implement butterknife injects here

    public WeatherRecyclerViewAdapter(@Nullable RecyclerOnPosClickListener clickListener) {
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

    private void onBindWeatherViewHolder(WeatherViewHolder viewHolder, int position) {
        Weather item = items.get(position);
        viewHolder.textDay.setText(Computation.getHumanDateFromUnixTime(item.getUnixTime()));
        viewHolder.textShortDesc.setText(item.getWeatherDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.textDetail.setText(Html.fromHtml(Utils.makeWeatherDetailString(item), Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.textDetail.setText(Html.fromHtml(Utils.makeWeatherDetailString(item)));
        }
        viewHolder.textTemperature.setText(Utils.makeTemperatureString(item.getTemperature()));

        Picasso
                .with(viewHolder.imageIcon.getContext())
                .load(Utils.getIconRes(item.getWeatherId(), false))
                .into(viewHolder.imageIcon);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycler_item_weather_row_basic_icon)    ImageView imageIcon;
        @BindView(R.id.recycler_item_weather_row_basic_day)     TextView textDay;
        @BindView(R.id.recycler_item_weather_row_basic_detail)  TextView textDetail;
        @BindView(R.id.recycler_item_weather_row_basic_shortdesc)   TextView textShortDesc;
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
