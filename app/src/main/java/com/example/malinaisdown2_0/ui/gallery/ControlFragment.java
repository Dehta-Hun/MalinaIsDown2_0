package com.example.malinaisdown2_0.ui.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.malinaisdown2_0.R;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class ControlFragment extends Fragment{


    private View controlView;
    private Button btn_shutdown;
    private Button btn_reboot;
    private Session session;
    private JSch jsch;
    private ChannelExec channelssh;
    private String command;
    private String question;

    private ControlViewModel controlViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        controlView = inflater.inflate(R.layout.fragment_control, container, false);


        btn_shutdown = controlView.findViewById(R.id.btn_shutdown);
        btn_shutdown.setOnClickListener(myShutdownListener);

        btn_reboot = controlView.findViewById(R.id.btn_reboot);
        btn_reboot.setOnClickListener(myRebootListener);

        return controlView;
    }

    View.OnClickListener myShutdownListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            question = "ВЫРУБАЕМ НАХ?";
            command = "sudo shutdown -h now";
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(question)
                    .setPositiveButton("DA EBA KANESHNO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            sshSentCmd(); //Запуск метода отправки команды по ssh

                            Toast.makeText(
                                    getActivity(), "Ну все, хана малине",
                                    Toast.LENGTH_LONG
                            ).show();

                        }
                    })
                    .setNegativeButton("NENE ASTANAVIS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getActivity(), "Ну как хош братан",
                                    Toast.LENGTH_LONG
                            ).show();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("SHO");
            alert.show();

        }
    };

    public View.OnClickListener myRebootListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            command = "sudo shutdown -r now";
            question = "РЕБУТАЕМ НАХ?";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(question)
                    .setPositiveButton("DA EBA KANESHNO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            sshSentCmd(); //Запуск метода отправки команды по ssh

                            Toast.makeText(
                                    getActivity(), "Ну все, хана малине",
                                    Toast.LENGTH_LONG
                            ).show();

                        }
                    })
                    .setNegativeButton("NENE ASTANAVIS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(
                                    getActivity(), "Ну как хош братан",
                                    Toast.LENGTH_LONG
                            ).show();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("SHO");
            alert.show();

        }
    };


    public void sshSentCmd() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    jsch = new JSch();
                    session = jsch.getSession("pi", "192.168.1.3", 22);
                    session.setPassword("ZvTuJkJk1515");

                    // Avoid asking for key confirmation
                    Properties prop = new Properties();
                    prop.put("StrictHostKeyChecking", "no");
                    session.setConfig(prop);

                    session.connect();

                    channelssh = (ChannelExec) session.openChannel("exec");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    channelssh.setOutputStream(baos);

                    channelssh.setCommand(command);
                    channelssh.connect();
                    channelssh.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
