package com.mainsm.apmc.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mainsm.apmc.R;
import com.mainsm.apmc.constants.Constants;
import com.mainsm.apmc.model.citiesModel.CitiesResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompareActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String TAG = CompareActivity.class.getSimpleName() ;
    private TextView compare, city1, city2;
    private LineChart mLineGraph;
    private ImageView fullScreen;
    private String cityName1 = "", cityName2 = "", localCode;
    private int avg1, avg2;
    private LinearLayout linearLayout;
    private int orientation;
    private List<CitiesResponse> citiesResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_compare);

        citiesResponses = (List<CitiesResponse>) getIntent().getSerializableExtra("data");
        Log.e(TAG, "onCreate: "+ citiesResponses );
        initUi();
        setData();

    }

    private void setData() {
        if(citiesResponses.size()>0) {
            cityName1 = citiesResponses.get(0).getName();
            cityName2 = citiesResponses.get(1).getName();
        }
        int total1 = 0;
        int total2 = 0;

        for(int i=0; i<citiesResponses.get(0).getPrices().size(); i++){
            total1 = total1 + Integer.parseInt(citiesResponses.get(0).getPrices().get(i).getPrice());
        }
        for(int i=0; i<citiesResponses.get(1).getPrices().size(); i++){
            total2 = total2 + Integer.parseInt(citiesResponses.get(1).getPrices().get(i).getPrice());
        }
        if (total1>0)
            avg1 = total1/citiesResponses.get(0).getPrices().size();
        if (total2>0)
            avg2 = total2/citiesResponses.get(1).getPrices().size();


        if(!localCode.equals("hi")){
            city1.setText(MessageFormat.format("{0} {1} :\n\n{2}", getString(R.string.avg_ann_cost), cityName1, avg1));
            city2.setText(MessageFormat.format("{0} {1} :\n\n{2}", getString(R.string.avg_ann_cost), cityName2, avg2));
            compare.setText(MessageFormat.format("{0} {1} & {2}", getString(R.string.comparision_btn), cityName1, cityName2));

        }else{
            city1.setText(MessageFormat.format("{0}, {1} :\n\n{2}", getString(R.string.avg_ann_cost), cityName1, avg1));
            city2.setText(MessageFormat.format("{0}, {1} :\n\n{2}", getString(R.string.avg_ann_cost), cityName2, avg2));
            compare.setText(MessageFormat.format("{1} & {2} {0}", getString(R.string.comparision_btn), cityName1, cityName2));

        }

        setChart();

    }

    private void setChart() {
        LineDataSet d1 = new LineDataSet(getDs1(citiesResponses), cityName1);
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(8.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setCircleHoleColor(Color.CYAN);
        d1.setDrawValues(false);
        d1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d1.setGradientColor(Color.CYAN, Color.MAGENTA);

        LineDataSet d2 = new LineDataSet(getDs2(citiesResponses), cityName2);
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(8.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleHoleColor(Color.GREEN);
        d2.setDrawValues(false);
        d2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d1.setGradientColor(Color.MAGENTA, Color.CYAN);


        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(false);


        Legend l = mLineGraph.getLegend();
        l.setTextColor(Color.rgb(84, 150, 255));
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(12.5f);
        l.setTextSize(12.0f);

        mLineGraph.setBackgroundColor(Color.rgb(1, 24, 61));
        mLineGraph.animateXY(1000, 1000, Easing.EaseInBack);
        mLineGraph.getDescription().setEnabled(false);

        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.RED);
        xAxis.setAxisLineWidth(1.4f);
        xAxis.setTextColor(Color.WHITE);

        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "Decemeber"));
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        YAxis leftAxis = mLineGraph.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.RED);
        leftAxis.setAxisLineWidth(1.4f);
        leftAxis.setTextColor(Color.WHITE);


        YAxis rightAxis = mLineGraph.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        //to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(d1);
        dataSets.add(d2);
        mLineGraph.setData(new LineData(dataSets ));
        mLineGraph.invalidate();
    }

    private List<Entry> getDs2(List<CitiesResponse> citiesResponses) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<citiesResponses.get(1).getPrices().size(); i++){
            yVal.add(new Entry(i,Float.parseFloat(citiesResponses.get(1).getPrices().get(i).getPrice())));
        }
        return yVal;
    }

    private List<Entry> getDs1(List<CitiesResponse> citiesResponses) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<citiesResponses.get(0).getPrices().size(); i++){
            yVal.add(new Entry(i,Float.parseFloat(citiesResponses.get(0).getPrices().get(i).getPrice())));
        }
        return yVal;
    }

    private void initUi() {
        sharedPreferences = getApplication().getSharedPreferences(Constants.DEFAULT, MODE_PRIVATE);
        localCode = sharedPreferences.getString(Constants.LOCAL,"null");
        orientation = this.getResources().getConfiguration().orientation;
        compare = findViewById(R.id.compareBetween);
        linearLayout = findViewById(R.id.parent);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        mLineGraph = findViewById(R.id.chart);
        fullScreen = findViewById(R.id.fullScreen);
        orientationConfiguration();

    }

    private void orientationConfiguration() {
        fullScreen.setOnClickListener(v -> {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLineGraph.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            mLineGraph.setLayoutParams(layoutParams);
            fullScreen.setImageResource(R.drawable.ic_baseline_fullscreen_exit_24);
            compare.setVisibility(View.GONE);
            city1.setVisibility(View.GONE);
            city2.setVisibility(View.GONE);
        }else
            initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

}