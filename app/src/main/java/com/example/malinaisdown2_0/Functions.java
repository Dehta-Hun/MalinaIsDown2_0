package com.example.malinaisdown2_0;

import android.view.View;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class Functions {

    private static Session session;
    private static JSch jsch;
    private static ChannelExec channelSsh;




    public static void  sshSentCmd(final String login, final String ip, final String pass, final String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    jsch = new JSch();
                    session = jsch.getSession(login, ip, 22);
                    session.setPassword(pass);

                    // Avoid asking for key confirmation
                    Properties prop = new Properties();
                    prop.put("StrictHostKeyChecking", "no");
                    session.setConfig(prop);

                    session.connect();

                    channelSsh = (ChannelExec) session.openChannel("exec");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    channelSsh.setOutputStream(baos);

                    channelSsh.setCommand(command);
                    channelSsh.connect();
                    channelSsh.disconnect();


                } catch (com.jcraft.jsch.JSchException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
}


