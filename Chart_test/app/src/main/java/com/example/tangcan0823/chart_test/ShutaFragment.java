package com.example.tangcan0823.chart_test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by tangcan0823 on 2016/11/16.
 */
public class ShutaFragment extends Fragment implements View.OnClickListener {
    public View view;
    public WebView myWebView;
    public Button btnsend, btnget, btncommand;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_shuta, container, false);


       /* Button btnconneect=(Button) view.findViewById(R.id.connect_data);
        btnconneect.setOnClickListener(this);
       */ btnsend = (Button) view.findViewById(R.id.send_data);
        btnsend.setOnClickListener(this);
        btnget = (Button) view.findViewById(R.id.get_data);
        btnget.setOnClickListener(this);
        btncommand = (Button) view.findViewById(R.id.command_button);
        btncommand.setOnClickListener(this);

      /*  myWebView = (WebView) view.findViewById(R.id.web_view);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://www.google.com");*/

        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand("sit-user-16", "2016mti", "130.158.80.37", 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
        toast("CONNETED");

        return view;
    }

    public static Session session;

    public String executeRemoteCommand(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();
        //toast("CANT CONNECT");

//----------------------コマンド
        // SSH Channel
      /*  ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("python makeTable.py test.csv resulttest.csv");
                //"mkdir /home/sit-user-16/testdata.csv");
        channelssh.connect();
        channelssh.disconnect();*/


//-----------------------アップロード
      /*  Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        sftpChannel.put("/sdcard/11111111.csv", "/var/www/box");
*/
//---------------------ダウンロード
//
/*
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        try (FileOutputStream out = new FileOutputStream("/sdcard/resulttest.csv")) {
            try (InputStream in = sftpChannel.get("resulttest.csv")) {
                // read from in, write to out
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
        }
        sftpChannel.exit();*/
        // session.disconnect();


//--------------------------------------------------------------------
        return "0";
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_data: {

                try {
                    sendData();
                } catch (JSchException e) {
                    e.printStackTrace();
                } catch (SftpException e) {
                    e.printStackTrace();
                }
                btnsend.setBackgroundColor(Color.rgb(0,101,135));
                break;
            }
            case R.id.get_data: {
                try {
                    getData();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                btnget.setBackgroundColor(Color.rgb(0,101,135));
                break;
            }
            /*case R.id.connect_data:{
                connect();
                toast("CONNETED");
                break;
            }*/
            case R.id.command_button:{

                /*  doGet();
                toast("AMAZON");*/
                /*try {
                    exec_post();
                    toast("AMAZON");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    runSsh();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                btncommand.setBackgroundColor(Color.rgb(0,101,135));
                break;
            }
            default:{
                break;
            }
        }
    }
    public void connect(){
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand("sit-user-16", "2016mti", "130.158.80.37", 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);
    }
    public void sendData() throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        sftpChannel.put("/sdcard/11111111.csv", "/var/www/box");
        sftpChannel.disconnect();
  //      session.disconnect();
    }
    public void getData() throws JSchException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        try (FileOutputStream out = new FileOutputStream("/sdcard/resulttest.csv")) {
            try (InputStream in = sftpChannel.get("resulttest.csv")) {
                // read from in, write to out
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } catch (SftpException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sftpChannel.exit();
//        session.disconnect();
    }

    public void runSsh() throws JSchException {
        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        channelssh.setCommand("sh /home/sit-user-16/test.sh");
        channelssh.connect();
        channelssh.disconnect();
//        MySyncTask task = new MySyncTask(getActivity(), mReturnTextView);
//        task.execute();
    }

    public void toast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    public void exec_post() throws IOException {
        HttpURLConnection con = null;
        URL url = new URL("http://130.158.80.37/phpinfo.php");
        con = (HttpURLConnection) url.openConnection();
        con.connect();
    }
    public void doGet()
    {
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://130.158.80.37/phpinfo.php?data=ok");
    }
}