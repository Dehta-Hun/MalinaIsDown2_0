package com.example.malinaisdown2_0;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class Functions {

    private static Session session;
    private static JSch jsch;
    private static ChannelExec channelSsh;


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


    public static class SshTestConnection extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... userData) { //Принимает 3 параметра: Логин, адрес сервера, пароль

            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(userData[0], userData[1], 22);
                session.setPassword(userData[2]);

                // Avoid asking for key confirmation
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);

                session.connect();

                return true;

            } catch (Exception e){
                e.printStackTrace();

                return false;
            }
        }
    }

    public static class sshSentCmd extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... userData) {
            try {

                jsch = new JSch();
                session = jsch.getSession(userData[0], userData[1], 22);
                session.setPassword(userData[2]);

                // Avoid asking for key confirmation
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);

                session.connect();

                channelSsh = (ChannelExec) session.openChannel("exec");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                channelSsh.setOutputStream(baos);

                channelSsh.setCommand(userData[3]);
                channelSsh.connect();
                channelSsh.disconnect();

                return true;
            } catch (com.jcraft.jsch.JSchException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static class sftpSentPhoto extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... userData) {
            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(userData[0], userData[1], 22);
                session.setPassword(userData[2]);
                session.connect();

                ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
                sftpChannel.connect();

                sftpChannel.put("/storage/emulated/0/DCIM/Camera/IMG_20200525_193944.jpg", "/home/pi/usb/IMG_20200525_193944.jpg");

                return true;
            } catch (JSchException e) {
                e.printStackTrace();
                return false;
            } catch (SftpException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}


