package com.example.tangcan0823.chart_test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by tangcan0823 on 2016/11/16.
 */
public class HitoriFragment extends Fragment {

    private TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hitori, null);
        mTextView = (TextView)view.findViewById(R.id.item_tv1);
        LineChart mLineChart = (LineChart)view.findViewById(R.id.line_chart);
        setLineChart(mLineChart);
        loadLineChartData(mLineChart);
        return view;
    }


    private void loadLineChartData(LineChart chart){


        String[] next ;
        List<String[]> list = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getActivity().getAssets().open("data.csv")));
            while((next = reader.readNext()) != null){
                list.add(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> TIME = new ArrayList<String>();
        ArrayList<String>  NUM = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            TIME.add(list.get(i)[0]);
            NUM.add(list.get(i)[1]);
        }

        for (int i = 0; i < list.size(); i++){
            if (Integer.parseInt(NUM.get(i)) == 1){
                mTextView.append(TIME.get(i)+"  ");
            }

        }



        ArrayList<LineDataSet> allLinesList = new ArrayList<LineDataSet>();
        ArrayList<Entry> entryList = new ArrayList<Entry>();
        for(int i=0;i<list.size();i++){
            entryList.add(new Entry(Integer.parseInt(NUM.get(i)),i));
        }

        LineDataSet dataSet1 = new LineDataSet(entryList,"周りの人数");
        dataSet1.setLineWidth(2.5f);
        dataSet1.setCircleSize(3.5f);
        dataSet1.setDrawFilled(true);
        dataSet1.setFillColor(Color.parseColor("#455A64"));
        dataSet1.setColor(Color.parseColor("#b3b5bb"));
        dataSet1.setCircleColor(Color.parseColor("#ffc755"));
        dataSet1.setDrawCircleHole(false);
        dataSet1.setDrawValues(false);


        ArrayList<Entry> entryList2 = new ArrayList<Entry>();
        for (int i = 0; i <TIME.size() ; i++) {
            entryList2.add(new Entry(1,i));
        }

        LineDataSet dataSet2 = new LineDataSet(entryList2,"１人の時");
        dataSet2.setColor(Color.RED);
        dataSet2.setLineWidth(2.5f);
        dataSet2.setDrawValues(false);
        dataSet2.setDrawCircles(false);




        allLinesList.add(dataSet1);
        //  allLinesList.add(dataSet2);


        LineData mChartData = new LineData(TIME,allLinesList);

        // set data
        chart.setData((LineData) mChartData);
        chart.animateX(1500);


    }

    private void setLineChart(LineChart chart) {

        chart.setDescription("");
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(true);
        chart.setDoubleTapToZoomEnabled(false);

        //set x Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#6a84c3"));


        //Get left Axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(4);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.parseColor("#6a84c3"));
//        leftAxis.setAxisLineWidth(1.5f);

        //Set right Axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(mTf);
//          rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);
    }


}
