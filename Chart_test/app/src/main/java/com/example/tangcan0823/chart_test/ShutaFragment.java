package com.example.tangcan0823.chart_test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tangcan0823 on 2016/11/16.
 */
public class ShutaFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shuta, container, false);

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

        Button btn = (Button) view.findViewById(R.id.send_data);
        btn.setOnClickListener(this);
        return view;
    }

    public static Session session;

    public static String executeRemoteCommand(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();


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
        Channel channel = null;
        switch (v.getId()) {
            case R.id.send_data: {
                try {
                    channel = session.openChannel("sftp");
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                try {
                    channel.connect();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                ChannelSftp sftpChannel = (ChannelSftp) channel;

                try {
                    sftpChannel.put("/sdcard/11111111.csv", "/var/www/box");
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
            case R.id.get_data: {
                try {
                    channel = session.openChannel("sftp");
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                try {
                    channel.connect();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
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
            }
        }
    }
}