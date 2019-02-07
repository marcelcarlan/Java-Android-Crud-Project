package com.carlanmarcel.books;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.carlanmarcel.books.viewModel.StatisticsViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StatisticsActivity extends AppCompatActivity {

    private float[] yData;
    private String[] xData;
    private PieChart pieChart;
    private StatisticsViewModel statisticsViewModel;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            pieChart.animateY(2000, Easing.EaseInOutCubic);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_statistics);
            this.statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
            pieChart = (PieChart) findViewById(R.id.pie_chart);

            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5,10,5,5);
            pieChart.setDragDecelerationFrictionCoef(0.95f);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);
            pieChart.setRotationEnabled(true);

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(this::addDataSet);
        }

    private void addDataSet() {
           Map<String, Float> map =  this.statisticsViewModel.getDataSet();
           List<PieEntry> yEntrys = new ArrayList<>();
           List<String> xEntrys = new ArrayList<>();
           Set<String> keys = map.keySet();
           for (String key:keys) {
                if(map.get(key)!=null) {
                    yEntrys.add(new PieEntry(map.get(key), key));
                    xEntrys.add(key);
                }
            }
           PieDataSet pieDataSet = new PieDataSet(yEntrys, "Categories");
           pieDataSet.setSliceSpace(3f);
           pieDataSet.setValueTextSize(12);
           pieDataSet.setSelectionShift(5f);
           pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

           Legend legend = pieChart.getLegend();
           legend.setForm(Legend.LegendForm.CIRCLE);
           legend.setTextSize(13);

           PieData pieData = new PieData((pieDataSet));
           pieData.setValueFormatter(new PercentFormatter());
           pieData.setValueTextColor(Color.BLACK);
           pieData.setValueTextSize(20f);
           Message message = mHandler.obtainMessage();
           message.sendToTarget();
           pieChart.setData(pieData);
           pieChart.invalidate();


    }


}
