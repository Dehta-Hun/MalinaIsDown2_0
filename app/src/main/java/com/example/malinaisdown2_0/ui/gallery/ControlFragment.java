package com.example.malinaisdown2_0.ui.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.example.malinaisdown2_0.Functions;

public class ControlFragment extends Fragment{


    private View controlView;
    private Button btn_shutdown;
    private Button btn_reboot;
    private String command, question, login, ip, pass;
    private Functions.sshSentCmd sshConnection;


    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

    public static final String stringlogin = "saved";
    public static final String stringip = "ALOLO";
    public static final String stringpass = "sdsd";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        controlView = inflater.inflate(R.layout.fragment_control, container, false);


        btn_shutdown = controlView.findViewById(R.id.btn_shutdown);
        btn_reboot = controlView.findViewById(R.id.btn_reboot);


        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ip = mSettings.getString(stringip, "");
        login = mSettings.getString(stringlogin, "");
        pass = mSettings.getString(stringpass, "");

        assignListeners();

        return controlView;
    }

    public void assignListeners() {       //Метод назначающий клик листнер кнопкам
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

                                sshConnection = new Functions.sshSentCmd();
                                sshConnection.execute(login, ip, pass, command); //Запуск метода отправки команды по ssh

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
        btn_shutdown.setOnClickListener(myShutdownListener);

        View.OnClickListener myRebootListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                command = "sudo shutdown -r now";
                question = "РЕБУТАЕМ НАХ?";

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(question)
                        .setPositiveButton("DA EBA KANESHNO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sshConnection = new Functions.sshSentCmd();
                                sshConnection.execute(login, ip, pass, command); //Запуск метода отправки команды по ssh

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
        btn_reboot.setOnClickListener(myRebootListener);

    }
}

