package com.mainsm.apmc.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mainsm.apmc.R;
import com.mainsm.apmc.adapter.StateDataAdapter;
import com.mainsm.apmc.constants.Constants;
import com.mainsm.apmc.interfaces.HomeListener;
import com.mainsm.apmc.model.APMCResponse;
import com.mainsm.apmc.model.CitiesItem;
import com.mainsm.apmc.model.PricesItem;
import com.mainsm.apmc.model.citiesModel.CitiesResponse;
import com.mainsm.apmc.viewmodel.HomeViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class HomeActivity extends AppCompatActivity implements HomeListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private HomeViewModel viewModel;
    private RecyclerView homeRv;
    private Button compareBtn;
    private int count = 0;
    TextView hindi, english;
    private List<CitiesResponse> responses;
    private Dialog dialog;
    private ImageView localSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolbar();
        initUi();
        initData();

    }

    private void initData() {
        viewModel.getStates(HomeActivity.this);
        viewModel.getCities(HomeActivity.this);

        if(viewModel.getStateList().size()>0 || null != viewModel.getStateList())
            setData(viewModel.getStateList());

        if(viewModel.getCitiesList().size()>0 || null != viewModel.getCitiesList())
            setCities(viewModel.getCitiesList());

        localSetter.setOnClickListener(v -> openLocalSelectorDialog());
    }

    private void openLocalSelectorDialog() {
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.select_local_dialog);
        hindi = dialog.findViewById(R.id.hindi);
        english = dialog.findViewById(R.id.eng);

        hindi.setOnClickListener(v -> {
            setAppLocale("hi");
            dialog.dismiss();
        });

        english.setOnClickListener(v -> {
            setAppLocale("en");
            dialog.dismiss();
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);

    }

    private void initUi() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeRv = findViewById(R.id.rv1);
        compareBtn = findViewById(R.id.compare);
        localSetter = findViewById(R.id.local);
    }

    @Override
    public void setData(List<APMCResponse> data) {
        homeRv.setLayoutManager(new LinearLayoutManager(this));
        StateDataAdapter adapter = new StateDataAdapter(this,this, data);
        homeRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setCities(List<CitiesResponse> citiesResponses) {

        compareBtn.setOnClickListener(v -> {
            setDialog(citiesResponses);
        });

    }

    private void setDialog(List<CitiesResponse> citiesResponses) {
        dialog = new Dialog(HomeActivity.this);
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.choose_two_dialog, null);
        dialog.setContentView(view);
        LinearLayout cBox = dialog.findViewById(R.id.checkbox);
        TextView okBtn = dialog.findViewById(R.id.ok);
        TextView clearAllBtn = dialog.findViewById(R.id.clear);

        responses = new ArrayList<>();

        for(int i=0; i<citiesResponses.size(); i++){
            AtomicReference<CheckBox> checkBox = new AtomicReference<>(new CheckBox(getApplicationContext()));
            checkBox.get().setText(citiesResponses.get(i).getName());
            checkBox.get().setId(i+101);
            if(checkBox.get().getParent() != null)
                ((ViewGroup) checkBox.get().getParent()).removeView(checkBox.get());
            cBox.addView(checkBox.get());

            clearAllBtn.setOnClickListener(v -> {
                responses.clear();
                cBox.removeAllViewsInLayout();
                count = 0;
                Log.e(TAG, "setDialog:CLOSE " + responses );

                reInitiateView(citiesResponses, cBox, responses, okBtn, clearAllBtn);

            });

            int finalI = i;
            checkBox.get().setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(count == 2 && isChecked){
                   Toast.makeText(getApplicationContext(), R.string.limit_reached, Toast.LENGTH_LONG).show();
                   buttonView.setChecked(false);
                }else if(isChecked){
                    count++;
                    responses.add(citiesResponses.get(finalI));
                }else if(count > 0){
                    buttonView.setChecked(true);
                }
            });

        }


        dialog.findViewById(R.id.cancel).setOnClickListener(v -> {
            dismissDialog(dialog, responses);
            count = 0;
        });

        okBtn.setOnClickListener(v -> {
            Log.e(TAG, "setDialog: RESPONSE"  + responses.toString() );
            if(count == 2)
                gotoCompareActivity(responses, dialog);
            else
                Toast.makeText(getApplicationContext(), R.string.select_atleast_2, Toast.LENGTH_LONG).show();
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
    }

    private void dismissDialog(Dialog dialog, List<CitiesResponse> responses) {
        if(null != dialog)
            dialog.dismiss();
        if(null != responses)
            responses.clear();
        count = 0;
    }

    private void gotoCompareActivity(List<CitiesResponse> responses, Dialog dialog) {
        Intent intent = new Intent(HomeActivity.this, CompareActivity.class);
        Log.e(TAG, "gotoCompareActivity: " + responses );
        intent.putExtra("data", (Serializable) responses);
        startActivity(intent);
        dismissDialog(dialog, responses);




    }

    private void reInitiateView(List<CitiesResponse> citiesResponses, LinearLayout cBox, List<CitiesResponse> responses, TextView btn, TextView okBtn) {

        for(int a=0; a<citiesResponses.size(); a++) {
            AtomicReference<CheckBox> newCheckBox = new AtomicReference<>(new CheckBox(getApplicationContext()));
            newCheckBox.get().setText(citiesResponses.get(a).getName());
            if(newCheckBox.get().getParent() != null)
                ((ViewGroup) newCheckBox.get().getParent()).removeView(newCheckBox.get());
            cBox.addView(newCheckBox.get());



            int finalI = a;
            newCheckBox.get().setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(count == 2 && isChecked){
                    Toast.makeText(getApplicationContext(), "Limit Reached", Toast.LENGTH_LONG).show();
                    buttonView.setChecked(false);
                }else if(isChecked){
                    count++;
                    responses.add(citiesResponses.get(finalI));
                }else if(count > 0){
                    buttonView.setChecked(true);
                }
            });
        }
    }



    public void nextActivity(Context context, List<CitiesItem> citiesItems){
        Intent intent = new Intent(context, CityByCityActivity.class);
        intent.putExtra("cities", (Serializable) citiesItems);
        context.startActivity(intent);
    }

    public LineData setChartData(List<CitiesItem> citiesItems, LineChart mLineGraph, String name){

        LineDataSet d1 = new LineDataSet(getDs1(citiesItems), citiesItems.get(0).getName());
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(8.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setCircleHoleColor(Color.CYAN);
        d1.setDrawValues(false);
        d1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        d1.setGradientColor(Color.CYAN, Color.MAGENTA);
        LineDataSet d2 = new LineDataSet(getDs2(citiesItems), citiesItems.get(1).getName());
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(8.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleHoleColor(Color.GREEN);
        d2.setDrawValues(false);
        d2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

//        LineDataSet lineDataSet3 = new LineDataSet(getDs3(citiesItems), citiesItems.get(2).getName());
//        lineDataSet3.setColor(Color.rgb(50,0,0));
//        lineDataSet3.setValueTextColor(Color.rgb(20, 30, 40));
//        lineDataSet3.setValueTextSize(10f);

        Legend l = mLineGraph.getLegend();
        l.setTextColor(Color.rgb(84, 150, 255));
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(12.5f);
        l.setTextSize(12.0f);


        mLineGraph.setTouchEnabled(true);
        mLineGraph.setDragEnabled(true);
        mLineGraph.setScaleEnabled(true);
        mLineGraph.setPinchZoom(true);
        mLineGraph.setDrawGridBackground(false);
        mLineGraph.setBackgroundColor(Color.rgb(1, 24, 61));
        mLineGraph.animateXY(1000, 1000, Easing.EaseInBack);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineGraph.setPinchZoom(false);
        // set custom chart offsets (automatic offset calculation is hereby disabled)
//        mLineGraph.setViewPortOffsets(10, 0, 10, 0);


//        mLineGraph.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mLineGraph.getRenderer().getPaintRender().setShadowLayer(1.5f, 3, 3, Color.rgb(247,255,0));



        Description description = new Description();
        description.setText( name  + " ( per 10 Kg ) ");
        description.setTextSize(10f);
        mLineGraph.setDescription(description);
        mLineGraph.getDescription().setEnabled(false);



        XAxis xAxis = mLineGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(true);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getMonths(citiesItems)));
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(Color.RED);
        xAxis.setAxisLineWidth(1.4f);
        xAxis.setTextColor(Color.WHITE);

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


        return new LineData(dataSets);




    }


    private List<String> getMonths(List<CitiesItem> citiesItems) {
        ArrayList<String> xVal = new ArrayList<>();
        ArrayList<PricesItem> pricesItems = new ArrayList<>(citiesItems.get(1).getPrices());
        for(int i=0; i<pricesItems.size(); i++)
            xVal.add(pricesItems.get(i).getMonth());
        return xVal;

    }

    private List<Entry> getDs3(List<CitiesItem> citiesItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<citiesItems.size(); i++){
            yVal.add(new Entry(Float.parseFloat(citiesItems.get(2).getPrices().get(i).getPrice()),i));
        }

        return yVal;
    }

    private List<Entry> getDs2(List<CitiesItem> citiesItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<citiesItems.get(1).getPrices().size(); i++){
            yVal.add(new Entry(i,Float.parseFloat(citiesItems.get(1).getPrices().get(i).getPrice())));
        }

        return yVal;
    }

    private List<Entry> getDs1(List<CitiesItem> citiesItems) {
        ArrayList<Entry> yVal = new ArrayList<>();
        for(int i=0; i<citiesItems.get(0).getPrices().size(); i++){
            yVal.add(new Entry(i, Float.parseFloat(citiesItems.get(0).getPrices().get(i).getPrice())));
        }
        return yVal;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissDialog(dialog, responses);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissDialog(dialog, responses);
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

    }

    @SuppressLint("ObsoleteSdkInt")
    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getApplication().getSharedPreferences(Constants.DEFAULT, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Constants.LOCAL, localeCode);
        editor.apply();


        recreate();

    }

}