package com.mainsm.apmc.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mainsm.apmc.R;
import com.mainsm.apmc.adapter.CityByCityAdapter;
import com.mainsm.apmc.model.CitiesItem;
import com.mainsm.apmc.model.PricesItem;
import com.mainsm.apmc.model.SixMonthsPriceItem;
import com.mainsm.apmc.model.ThisMonthPricesItem;

import java.util.ArrayList;
import java.util.List;

public class CityByCityActivity extends AppCompatActivity {
    private List<CitiesItem> citiesItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_by_city);
        citiesItemList = (List<CitiesItem>) getIntent().getSerializableExtra("cities");
        setData(citiesItemList);
        initToolbar();
    }

    private void setData(List<CitiesItem> citiesItemList) {
        RecyclerView cityRv = findViewById(R.id.rv2);
        cityRv.setLayoutManager(new LinearLayoutManager(this));
        CityByCityAdapter adapter = new CityByCityAdapter(this, citiesItemList, this);
        cityRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public LineData setChartData(List<PricesItem> pricesItems, LineChart mLineGraph, String cityName){
        LineDataSet lineDataSet = new LineDataSet(getData(pricesItems), cityName);
        lineDataSet.setValueTextColor(Color.rgb(55, 60, 73));
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setCircleRadius(8.5f);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleHoleColor(Color.CYAN);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(false);

        //to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setDrawGridLines(false);

        YAxis leftYAxis = mLineGraph.getAxisLeft();
        leftYAxis.setEnabled(true);
        leftYAxis.setDrawGridLines(false);

        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        List<String> xAxisValues = getMonths(pricesItems);
        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xAxisValues.get((int) value);
            }
        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths(pricesItems)));

        return new LineData(lineDataSet);

    }


    public LineData setSixMonthChartData(List<SixMonthsPriceItem> sixMonthsPriceItems, LineChart mLineGraph, String cityName){

        Legend l = mLineGraph.getLegend();
        l.setTextColor(Color.rgb(84, 150, 255));
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextSize(12.0f);
        l.setFormSize(12.5f);



        LineDataSet lineDataSet = new LineDataSet(getSixMonthData(sixMonthsPriceItems), cityName);
        lineDataSet.setValueTextColor(Color.rgb(55, 60, 73));
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setCircleRadius(8.5f);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setColor(Color.YELLOW);
        lineDataSet.setCircleColor(Color.YELLOW);
        lineDataSet.setCircleHoleColor(Color.YELLOW);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        float[] f = {1.0f, 2.0f, 2.5f, 3.0f};
        lineDataSet.setFormLineDashEffect(new DashPathEffect(f, 0));


//        mLineGraph.setBackgroundColor(Color.rgb(122, 4, 6));

        mLineGraph.getRenderer().getPaintRender().
                setShader(new LinearGradient(0, 0, mLineGraph.getMeasuredWidth(),
                                             0, Color.RED, Color.MAGENTA, Shader.TileMode.CLAMP));

        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(false);
        mLineGraph.setBackgroundColor(Color.rgb(1, 24, 61));
        mLineGraph.animateXY(1000, 1000, Easing.EaseInBack);


        //to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setDrawGridLines(false);

        YAxis leftYAxis = mLineGraph.getAxisLeft();
        leftYAxis.setEnabled(true);
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisLineColor(Color.RED);
        leftYAxis.setTextColor(Color.WHITE);
        leftYAxis.setAxisLineWidth(1.4f);
//        YAxis leftYAxis = mLineGraph.getAxisLeft();
//        leftYAxis.setEnabled(false);
//        XAxis topXAxis = mLineGraph.getXAxis();
//        topXAxis.setEnabled(false);

        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.RED);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineWidth(1.4f);

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getSixMonths(sixMonthsPriceItems)));
        return new LineData(lineDataSet);

    }

    public LineData setChart2Data(List<PricesItem> pricesItems, LineChart mLineGraph, String cityName){
//        Typeface mTf = Typeface.createFromAsset(CityByCityActivity.this.getAssets(), "OpenSans-Regular.ttf");
        LineDataSet d2 = new LineDataSet(getData(pricesItems), cityName);
        d2.setLineWidth(2.5f);
        d2.setHighlightLineWidth(2.5f);
        d2.setCircleRadius(6.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.COLORFUL_COLORS[1]);
        d2.setCircleColor(ColorTemplate.COLORFUL_COLORS[1]);
        d2.setCircleHoleColor(ColorTemplate.COLORFUL_COLORS[1]);
        d2.setDrawValues(false);
        d2.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        Description description = new Description();
        description.setText(cityName  + " ( per 10 Kg ) ");
        mLineGraph.setDescription(description);
        mLineGraph.setDrawGridBackground(false);

        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths(pricesItems)));


        YAxis leftAxis = mLineGraph.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mLineGraph.getAxisRight();
//        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        return new LineData(d2);

    }

    public LineData setCurrentMonthChartData(List<ThisMonthPricesItem> thisMonthPricesItems, LineChart mLineGraph, String cityName){
        Legend legend = mLineGraph.getLegend();
        LineDataSet lineDataSet = new LineDataSet(getCurrentMonthData(thisMonthPricesItems), cityName);
        lineDataSet.setColor(Color.rgb(55, 70, 80));
        lineDataSet.setValueTextColor(Color.rgb(55, 60, 73));
        lineDataSet.setValueTextSize(15f);
        lineDataSet.enableDashedLine(10f,10f, 1);

        legend.setFormSize(10f);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(15f);


        mLineGraph.fitScreen();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mLineGraph.setOutlineAmbientShadowColor(Color.RED);
            mLineGraph.setOutlineSpotShadowColor(Color.MAGENTA);

        }
        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(false);
        mLineGraph.setBorderColor(Color.RED);

        //to hide right Y and top X border
        YAxis rightYAxis = mLineGraph.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = mLineGraph.getAxisLeft();
        leftYAxis.setEnabled(true);
        leftYAxis.setDrawGridLines(false);
        XAxis topXAxis = mLineGraph.getXAxis();
        topXAxis.setEnabled(false);
        XAxis xAxis = mLineGraph.getXAxis();


        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        List<String> xAxisValues = getDates(thisMonthPricesItems);
//        ValueFormatter valueFormatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return xAxisValues.get((int) value);
//            }
//        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDates(thisMonthPricesItems)));
        return new LineData(lineDataSet);

    }

    private List<String> getDates(List<ThisMonthPricesItem> thisMonthPricesItems) {
        ArrayList<String> xVal = new ArrayList<>();
        for(int i=0; i<thisMonthPricesItems.size(); i++){
            xVal.add(thisMonthPricesItems.get(i).getDay());
        }
        return xVal;

    }

    private ArrayList<Entry> getCurrentMonthData(List<ThisMonthPricesItem> thisMonthPricesItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<thisMonthPricesItems.size(); i++){
            yVal.add(new Entry(i,Float.parseFloat(thisMonthPricesItems.get(i).getPrice())));
        }
        return yVal;
    }


    private List<String> getSixMonths(List<SixMonthsPriceItem> sixMonthsPriceItems) {
        ArrayList<String> xVal = new ArrayList<>();
        for(int i=0; i<sixMonthsPriceItems.size(); i++){
            xVal.add(sixMonthsPriceItems.get(i).getMonth());
        }
        return xVal;
    }

    private List<Entry> getSixMonthData(List<SixMonthsPriceItem> sixMonthsPriceItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<sixMonthsPriceItems.size(); i++){
            yVal.add(new Entry(i, Float.parseFloat(sixMonthsPriceItems.get(i).getPrices())));
        }
        return yVal;
    }


    private List<String> getMonths(List<PricesItem> pricesItems) {
        ArrayList<String> xVal = new ArrayList<>();
        for(int i=0; i<pricesItems.size(); i++){
            xVal.add(pricesItems.get(i).getMonth());
        }
        return xVal;
    }

    private List<Entry> getData(List<PricesItem> pricesItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<pricesItems.size(); i++){
            yVal.add(new Entry(i, Float.parseFloat(pricesItems.get(i).getPrice())));
        }
        return yVal;
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