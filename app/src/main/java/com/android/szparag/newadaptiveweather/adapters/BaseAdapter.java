package com.android.szparag.newadaptiveweather.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciemek on 26/09/2016.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected RecyclerOnPosClickListener    recyclerOnPosClickListener;
    protected List<T>                       items;


    public BaseAdapter() {
        super();
        items = new ArrayList<T>();
    }

    public BaseAdapter(@Nullable RecyclerOnPosClickListener clickListener) {
        this();
        if (clickListener != null) {
            setRecyclerOnPosClickListener(clickListener);
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setRecyclerOnPosClickListener(RecyclerOnPosClickListener clickListener) {
        recyclerOnPosClickListener = clickListener;
    }

    public void addItem(T item) {
        items.add(item);
        notifyItemInserted(items.size()-1);
    }

    public void addItemNoNotify(T item) {
        items.add(item);
    }

    public void addItems(List<T> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    public void updateItems(List<T> item) {
        items.clear();
        items.addAll(item);
        notifyDataSetChanged();
    }


    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }
}
