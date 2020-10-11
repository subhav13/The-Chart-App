package com.mainsm.apmc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.mainsm.apmc.R;
import com.mainsm.apmc.activities.CityByCityActivity;
import com.mainsm.apmc.model.CitiesItem;

import java.util.List;

public class CityByCityAdapter extends RecyclerView.Adapter<CityByCityAdapter.MyHolder> {

    private Context context;
    private List<CitiesItem> citiesItems;
    private CityByCityActivity activity;

    public CityByCityAdapter(Context context, List<CitiesItem> citiesItems, CityByCityActivity activity) {
        this.context = context;
        this.citiesItems = citiesItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_by_city_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        boolean isExpanded = citiesItems.get(position).isExpanded();
        holder.cityName.setText(citiesItems.get(position).getName());
        if(isExpanded)
            holder.cityName.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_baseline_arrow_drop_down_24,0 );
        else
            holder.cityName.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_baseline_arrow_drop_up_24,0 );

        holder.chart.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.chart2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.buttonLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.yearly.setActivated(true);

//        holder.chart.setDescription(context.getString(R.string.onion_prices)+ " "+ citiesItems.get(position).getName() + "\n" + context.getString(R.string.per_100_kg));

        holder.chart2.setData(activity.setChart2Data(citiesItems.get(position).getPrices(), holder.chart2, citiesItems.get(position).getName()));

        if(holder.yearly.isActivated()) {
            holder.chart.setData(activity.setChartData(citiesItems.get(position).getPrices(), holder.chart, citiesItems.get(position).getName()));
            holder.chart.invalidate();
        }

        holder.yearly.setOnClickListener(v -> {
            holder.chart.setBackgroundColor(Color.WHITE);
            holder.chart.setBackgroundColor(Color.WHITE);
            holder.chart.getAxisLeft().setAxisLineColor(Color.BLACK);
            holder.chart.getAxisLeft().setTextColor(Color.BLACK);
            holder.chart.getXAxis().setAxisLineColor(Color.BLACK);
            holder.chart.getXAxis().setTextColor(Color.BLACK);
            holder.chart.setData(activity.setChartData(citiesItems.get(position).getPrices(), holder.chart, citiesItems.get(position).getName()));
            holder.chart.invalidate();

        });

        holder.sixMonths.setOnClickListener(v -> {
            holder.yearly.setActivated(false);
            holder.chart.setData(activity.setSixMonthChartData(citiesItems.get(position).getSixMonthsPrice(), holder.chart, citiesItems.get(position).getName()));
            holder.chart.invalidate();

        });

            holder.thisMonths.setOnClickListener(v -> {
                holder.yearly.setActivated(false);
                holder.chart.setBackgroundColor(Color.WHITE);
                holder.chart.getAxisLeft().setAxisLineColor(Color.BLACK);
                holder.chart.getAxisLeft().setTextColor(Color.BLACK);
                holder.chart.getXAxis().setAxisLineColor(Color.BLACK);
                holder.chart.getXAxis().setTextColor(Color.BLACK);
                holder.chart.setData(activity.setCurrentMonthChartData(citiesItems.get(position).getThisMonthPrices(), holder.chart, citiesItems.get(position).getName()));
                holder.chart.invalidate();

            });

    }

    @Override
    public int getItemCount() {
        return citiesItems.size();
    }

     class MyHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        LineChart chart, chart2;
        TextView yearly, sixMonths, thisMonths;
        LinearLayout buttonLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
            chart = itemView.findViewById(R.id.chart);
            chart2 = itemView.findViewById(R.id.chart2);
            yearly = itemView.findViewById(R.id.yearly);
            sixMonths = itemView.findViewById(R.id.six_months);
            thisMonths = itemView.findViewById(R.id.this_month);
            buttonLayout = itemView.findViewById(R.id.button_parent);

            cityName.setOnClickListener(v -> {
                CitiesItem item = citiesItems.get(getAdapterPosition());
                item.setExpanded(!item.isExpanded());
                notifyItemChanged(getAdapterPosition());

            });

        }
    }
}
