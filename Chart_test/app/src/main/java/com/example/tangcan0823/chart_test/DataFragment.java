package com.example.tangcan0823.chart_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Created by tangcan0823 on 2016/11/16.
 */
public class DataFragment extends Fragment implements View.OnClickListener {
    public View view;
    public Button btnsend, btnget;
    public int counter=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_data, container, false);

        btnsend = (Button) view.findViewById(R.id.send_data);
        btnsend.setOnClickListener(this);
        btnget = (Button) view.findViewById(R.id.get_data);
        btnget.setOnClickListener(this);

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
        return "0";
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_data: {
                if (counter == 0) {
                    try {
                        sendData();
                    } catch (JSchException e) {
                        e.printStackTrace();
                    } catch (SftpException e) {
                        e.printStackTrace();
                    }

                    try {
                        runSsh();
                    } catch (JSchException e) {
                        e.printStackTrace();
                    }
                    try {
                        //1000ミリ秒Sleepする
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }

                    btnsend.setBackgroundResource(R.drawable.frame_style_pressed);
                    counter++;
                }
                break;
            }
            case R.id.get_data: {
                if (counter == 1) {
                    try {
                        getData();
                    } catch (JSchException e) {
                        e.printStackTrace();
                    }
                    btnget.setBackgroundResource(R.drawable.frame_style_pressed);
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    public void sendData() throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;

        sftpChannel.put("/sdcard/bb4.csv", "/var/www/box");
        sftpChannel.disconnect();
  //      session.disconnect();
    }
    public void getData() throws JSchException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        try (FileOutputStream out = new FileOutputStream("/sdcard/bb4_result_add.csv")) {
            try (InputStream in = sftpChannel.get("./demo/result/bb4_result_add.csv")) {
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
        channelssh.setCommand("sh /home/sit-user-16/demo/code/test.sh");
        channelssh.connect();
        channelssh.disconnect();
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
}