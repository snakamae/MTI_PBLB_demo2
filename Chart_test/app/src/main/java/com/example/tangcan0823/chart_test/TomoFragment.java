package com.example.tangcan0823.chart_test;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by tangcan0823 on 2016/11/16.
 */
public class TomoFragment extends Fragment {
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomo, null);
        button = (Button) view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProcess();
            }
        });
        return view;
    }

    private void DataProcess() {
        //导入数据
        List<String> list_TIME0 = new ArrayList<String>();
        List<String> list_ID0 = new ArrayList<String>();

        File sdCardDir = Environment.getExternalStorageDirectory();
        try {

            FileReader fr = new FileReader(new File(sdCardDir,"test2.csv"));
            BufferedReader br = new BufferedReader(fr);

            String line;
            StringTokenizer token;
            while ((line = br.readLine()) != null) {
                token = new StringTokenizer(line, ",");
                while (token.hasMoreTokens()) {
                    list_ID0.add(token.nextToken());
                    list_TIME0.add(token.nextToken());
                }
            }
            br.close();

        } catch (IOException ex) {

            ex.printStackTrace();
        }


        //transform  list_TIME0 to int
        List<Integer> int_TIME0 = new ArrayList<Integer>();
        for(String str : list_TIME0) {
            int_TIME0.add(Integer.parseInt(str));
        }

        //10分間化
        int a=0;
        List<Integer> int_TIME = new ArrayList<Integer>();
        for(int i=0;i<int_TIME0.size();i++){
            int_TIME.add(((int_TIME0.get(i)/600000)*600000)) ;
        }

        //list_ID0 unique
        List<String> String_ID = new ArrayList<String>();//copy for　unique

        for (int i=0; i<list_ID0.size(); i++) {
            if(!String_ID.contains(list_ID0.get(i))) {
                String_ID.add(list_ID0.get(i));
            }
        }


        List<Integer> NUM = new ArrayList<Integer>();//Calculate the total number of data for each 10 minutes of which has data
        int w=0;
        int n=0;
        for(int i = 0; i < int_TIME.size(); ){
            while((int_TIME.get(i)).equals(0+600000*n)){
                w++;
                i++;
                if(i==int_TIME.size()){break;}
            }
            NUM.add(w);
            w=0;
            n++;
        }

        List<Integer> NUM_SUM = new ArrayList<Integer>();//NUM 現在位置から先頭まで蓄積
        NUM_SUM.add(0);
        int sum=0;
        for(int i=0;i<NUM.size();i++){
            sum+=NUM.get(i);
            NUM_SUM.add(sum);
        }




        int RR=int_TIME0.get(int_TIME0.size()-1)/600000+1;
        int m=0;
        int[][]  ALL = new int[RR][String_ID.size()];
        //put data in ALL
        for (int y = 0; y < RR; y++) {
            {
                if(NUM_SUM.size()>=2){
                    for (int q = NUM_SUM.get(m); q < NUM_SUM.get(m+1); q++)
                    {
                        for (int x = 0; x < String_ID.size(); x++)
                        {
                            if(int_TIME.get(q).equals(0+600000*m)&&String_ID.get(x).equals(list_ID0.get(q))){
                                ALL[y][x] = 1;
                            }
                        }
                    }
                    m++;
                }else{
                    for(int p=0;p<NUM_SUM.get(0);p++){
                        for (int x = 0; x < String_ID.size(); x++)
                        {
                            if(int_TIME.get(p).equals(0+600000*m)&&String_ID.get(x).equals(list_ID0.get(p))){
                                ALL[y][x] = 1;
                            }
                        }}
                }
            }
        }





        int[][]  TIME_add = new int[RR][2];
        //第一列時間を書く
        int nn=0;
        for(int i=0;i<RR;i++){
            TIME_add[i][0]=0+600000*nn;
            nn++;
        }
        //第2列
        int ss=0;
        for(int i=0;i<RR;i++){
            for(int j=0;j<String_ID.size();j++){
                ss+=ALL[i][j];
            }
            TIME_add[i][1]=ss;
        }

        int[][]  TIME_change = new int[RR][2];
        //第一列時間を書く
        int pp=0;
        for(int i=0;i<RR;i++){
            TIME_change[i][0]=0+600000*pp;
            pp++;
        }
        //第2列
        TIME_change[0][1]=0;
        for(int i=1;i<RR;i++){
            for(int j=0;j<String_ID.size();j++){
                TIME_change[i][1]=Math.abs(ALL[i-1][j]-ALL[i][j])+TIME_change[i][1];
            }
        }

        //导出
        int i,j;
        try
        {
            FileWriter writer = new FileWriter(new File(sdCardDir,"TIME_add.csv"));
            for(i = 0; i < TIME_add.length; )
            {
                for (j=0; j<TIME_add[0].length; j++)
                {
                    writer.append(String.valueOf(TIME_add[i][j]));
                    writer.append(',');

                }
                writer.append('\n');
                i++;
                writer.flush();
            }
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //导出
        try
        {
            FileWriter writer = new FileWriter(new File(sdCardDir,"TIME_change.csv"));

            for(i = 0; i <TIME_change.length; )
            {
                for (j=0; j<TIME_change[0].length; j++)
                {
                    writer.append(String.valueOf(TIME_change[i][j]));
                    writer.append(',');

                }


                writer.append('\n');
                i++;
                writer.flush();
            }
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    }



