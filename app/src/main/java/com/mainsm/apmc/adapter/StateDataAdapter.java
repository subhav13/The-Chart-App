package com.mainsm.apmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChartView;
import com.github.mikephil.charting.charts.LineChart;
import com.mainsm.apmc.R;
import com.mainsm.apmc.activities.HomeActivity;
import com.mainsm.apmc.model.APMCResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateDataAdapter extends RecyclerView.Adapter<StateDataAdapter.MyHolder> {
    private HomeActivity activity;
    private Context context;
    private List<APMCResponse> responses;

    public StateDataAdapter(HomeActivity activity, Context context, List<APMCResponse> responses) {
        this.activity = activity;
        this.context = context;
        this.responses = responses;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_data_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.stateName.setText(responses.get(position).getName());


//        holder.chartView.setDescription(context.getString(R.string.onion_prices)+ responses.get(position).getName() + "\n" + context.getString(R.string.per_100_kg));
        holder.chartView.setData(activity.setChartData(responses.get(position).getCities(), holder.chartView, responses.get(position).getName()));
        holder.chartView.invalidate();

        holder.cityByCity.setOnClickListener(v -> activity.nextActivity(context, responses.get(position).getCities()));
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        LineChart chartView;
        TextView stateName, cityByCity;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            chartView = itemView.findViewById(R.id.any_chart);
            stateName = itemView.findViewById(R.id.state_name);
            cityByCity = itemView.findViewById(R.id.cityByCity);
        }
    }
}
