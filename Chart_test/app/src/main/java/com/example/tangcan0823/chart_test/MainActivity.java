package com.example.tangcan0823.chart_test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MTI");
        setSupportActionBar(mToolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.inflateHeaderView(R.layout.navigation_header);
        mTextView = (TextView) findViewById(R.id.item_tv1);


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
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#6a84c3"));


        //Get left Axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5);
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

    //ユニーク関数
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

    //リスト最大値を取る
    public double ArrayListMax(ArrayList sampleList)
    {
        try
        {
            double maxDevation = 0.0;
            int totalCount = sampleList.size();
            if (totalCount >= 1)
            {

                double max = Double.parseDouble(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++)
                {
                    double temp = Double.parseDouble(sampleList.get(i).toString());
                    if (temp > max)
                    {
                        max = temp;
                    }
                } maxDevation = max;
            }
            return maxDevation;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    //リスト最小値を取る
    public double ArrayListMin(ArrayList sampleList)
    {
        try
        {
            double mixDevation = 0.0;
            int totalCount = sampleList.size();
            if (totalCount >= 1)
            {
                double min = Double.parseDouble(sampleList.get(0).toString());
                for (int i = 0; i < totalCount; i++)
                {
                    double temp = Double.parseDouble(sampleList.get(i).toString());
                    if (min > temp)
                    {
                        min = temp;
                    }
                } mixDevation = min;
            }
            return mixDevation;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }





}