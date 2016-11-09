package com.example.tangcan0823.chart_test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linechart
        LineChart mLineChart = (LineChart) findViewById(R.id.line_chart);
        setLineChart(mLineChart);
        loadLineChartData(mLineChart);
    }





    /**
     * Set data for Linechart
     * @param chart
     */
    private void loadLineChartData(LineChart chart){


        String[] next ;
        List<String[]> list = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("data.csv")));
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

        removeDuplicateWithOrder(TIME);
        removeDuplicateWithOrder(NUM);


        ArrayList<LineDataSet> allLinesList = new ArrayList<LineDataSet>();
        ArrayList<Entry> entryList = new ArrayList<Entry>();
        for(int i=0;i<list.size();i++){
            entryList.add(new Entry(Integer.parseInt(NUM.get(i)),i));
        }

        LineDataSet dataSet1 = new LineDataSet(entryList,"周りの人数");
        dataSet1.setLineWidth(2.5f);
        dataSet1.setCircleSize(3.5f);
        dataSet1.setHighLightColor(Color.RED);
       // dataSet1.setDrawCubic(true);
        dataSet1.setDrawValues(true);


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
        allLinesList.add(dataSet2);


        LineData mChartData = new LineData(TIME,allLinesList);

        // set data
        chart.setData((LineData) mChartData);
        chart.animateX(1500);






    }


    /**
     * Set style for Linechart
     * @param chart
     */
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
        xAxis.setDrawAxisLine(true);

        //Get left Axis
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5);
//        leftAxis.setAxisLineWidth(1.5f);

        //Set right Axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
//        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5);
//        rightAxis.setDrawGridLines(false);
    }

    public static void removeDuplicateWithOrder(ArrayList arlList)
    {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = arlList.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        arlList.clear();
        arlList.addAll(newList);
    }


}