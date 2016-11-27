package com.example.tangcan0823.chart_test;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.io.File;
import java.io.FileInputStream;
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
    private ListView listView;
    private LineChart mLineChart;
    private String CSV;
    private String sdPath = Environment.getExternalStorageDirectory().getPath();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hitori, null);

        mTextView = (TextView)view.findViewById(R.id.item_tv1);
        listView = (ListView) view.findViewById(R.id.listview);
        mLineChart = (LineChart)view.findViewById(R.id.line_chart);
        CSV = "csv1.csv";
        setLineChart(mLineChart);
        loadLineChartData(mLineChart);
        SetList();
        SetListitemlisten();
        return view;
    }


    private void loadLineChartData(LineChart chart){


        String[] next ;
        List<String[]> list = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(new File(sdPath + "/" + CSV))));
            while((next = reader.readNext()) != null){
                list.add(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> TIME = new ArrayList<String>();
        ArrayList<String>  NUM = new ArrayList<String>();
        ArrayList<String>  start = new ArrayList<String>();
        ArrayList<String>  end = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            TIME.add(list.get(i)[0]);
            NUM.add(list.get(i)[1]);
        }

        for (int i = 1; i < TIME.size()-1; i++) {
            if (Integer.parseInt(NUM.get(i + 1)) != 0 && Integer.parseInt(NUM.get(i - 1)) != 0 && Integer.parseInt(NUM.get(i)) == 0){

            }
            else if (Integer.parseInt(NUM.get(i)) == 0 && Integer.parseInt(NUM.get(i - 1)) != 0) {
                start.add(TIME.get(i));
            }
            else if (Integer.parseInt(NUM.get(i)) == 0 && Integer.parseInt(NUM.get(i + 1)) != 0) {
                end.add(TIME.get(i));
            }
        }

                mTextView.setText("");
        for (int i = 0; i < start.size(); i++){
                mTextView.append(start.get(i) +"から"+end.get(i)+"までは一人でしたよ\n");
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
        allLinesList.add(dataSet1);


        LineData mChartData = new LineData(TIME,allLinesList);

        // set data
        chart.setData((LineData) mChartData);
        Legend legend = chart.getLegend();
        legend.setTextColor(Color.parseColor("#6a84c3"));
        chart.animateX(1500);

    }

    private void setLineChart(LineChart chart) {

        chart.setDescription("");
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(true);
        chart.setScaleYEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDragEnabled(true);

        //set x Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#6a84c3"));



        //Get left Axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setLabelCount(3);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.parseColor("#6a84c3"));
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) Math.floor(value));
            }
        });


        //Set right Axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);
    }

    private void SetList(){
        String[] csv = {"2016/11/25","2016/11/26","2016/11/27","2016/11/28"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,csv);
        listView.setAdapter(adapter);

    }

    private void SetListitemlisten(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        CSV = "csv1.csv";
                        loadLineChartData(mLineChart);
                        break;
                    case 1:
                        CSV = "csv2.csv";
                        loadLineChartData(mLineChart);
                        break;
                    case 2:
                        CSV = "csv3.csv";
                        loadLineChartData(mLineChart);
                        break;
                    case 3:
                            CSV = "bb4_result_add.csv";
                       //   CSV = "csv4.csv";
                        loadLineChartData(mLineChart);
                        break;
                }
            }
        });

    }


}
