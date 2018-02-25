package com.hulahoop.mentalhealth.undepress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MonitorFragment extends Fragment {

    private LineChart lineChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monitor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lineChart = (LineChart) view.findViewById(R.id.progressChart);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawBorders(true);

        //        LimitLine upperLimit = new LimitLine(5f, "Depressed");
//        upperLimit.setLineWidth(2f);
//        upperLimit.enableDashedLine(10f,10f, 10f);
//        upperLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upperLimit.setTextSize(12f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
//        leftAxis.addLimitLine(upperLimit);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
//        leftAxis.enableAxisLineDashedLine(5f, 1f, 0);
        leftAxis.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0,6));
        yValues.add(new Entry(1,5));
        yValues.add(new Entry(2,1));
        yValues.add(new Entry(3,9));
        yValues.add(new Entry(4,2));
        yValues.add(new Entry(5,4));
        yValues.add(new Entry(6,8));

        LineDataSet dataSet1 = new LineDataSet(yValues, "Depression Symptom Tracker");
        dataSet1.setFillAlpha(110);
        dataSet1.setColor(getResources().getColor(R.color.orange));
        dataSet1.setLineWidth(3f);
        dataSet1.setValueTextSize(10f);
        dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet1.setDrawFilled(true);
        dataSet1.setFillColor(getResources().getColor(R.color.darkTosca));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);

        String[] values = new String[] {"16-Jan", "23-Jan", "26-Jan", "29-Jan", "31-Jan",
                                        "2-Feb", "5-Feb"};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(false);
    }

    public class MyXAxisValueFormatter extends IndexAxisValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
}
