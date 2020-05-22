package com.example.malinaisdown2_0;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


