package com.example.malinaisdown2_0.ui.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.malinaisdown2_0.Functions;
import com.example.malinaisdown2_0.R;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class LoginFragment extends Fragment {

    public TextView textLogin, textIp, textPassword, textCheck;
    public String login, ip, password;
    public Button btnCheck;

    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

    public static final String stringlogin = "saved";
    public static final String stringip = "ALOLO";
    public static final String stringpass = "sdsd";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View loginView = inflater.inflate(R.layout.fragment_login, container, false);

        textLogin = loginView.findViewById(R.id.textLogin);
        textIp = loginView.findViewById(R.id.textServer);
        textPassword = loginView.findViewById(R.id.textPassword);
        textCheck = loginView.findViewById(R.id.connectCheck);
        btnCheck = loginView.findViewById(R.id.btnConnectionTest);


        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        assignListenerOnClick();

        return loginView;

    }

    @Override
    public void onResume() {
        super.onResume();
        String savedlogin = mSettings.getString(stringlogin, "");
        textLogin.setText(savedlogin);

        String savedip = mSettings.getString(stringip, "");
        textIp.setText(savedip);

        String savedpass = mSettings.getString(stringpass, "");
        textPassword.setText(savedpass);

        sshConnectionCheck(textLogin.getText().toString(), textIp.getText().toString(), textPassword.getText().toString());
    }

    @Override
    public void onPause() {
        super.onPause();

        Functions.hideKeyboard(getActivity());

        login = textLogin.getText().toString();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(stringlogin, login);
        editor.apply();


        ip = textIp.getText().toString();
        SharedPreferences.Editor ed_ip = mSettings.edit();
        ed_ip.putString(stringip, ip);
        ed_ip.apply();

        password = textPassword.getText().toString();
        SharedPreferences.Editor ed_pass = mSettings.edit();
        ed_pass.putString(stringpass, password);
        ed_pass.apply();
    }

    public void assignListenerOnClick() {
        View.OnClickListener myCheckListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideKeyboard(getActivity());
                sshConnectionCheck(textLogin.getText().toString(), textIp.getText().toString(), textPassword.getText().toString());

            }
        };
        btnCheck.setOnClickListener(myCheckListener);
    }

    public void sshConnectionCheck(final String login, final String ip, final String password) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    JSch jsch = new JSch();
                    Session session = jsch.getSession(login, ip, 22);
                    session.setPassword(password);

                    // Avoid asking for key confirmation
                    Properties prop = new Properties();
                    prop.put("StrictHostKeyChecking", "no");
                    session.setConfig(prop);

                    session.connect();

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
//                            Toast.makeText(getActivity(), "ок", Toast.LENGTH_SHORT).show();
                            textCheck.setText("Подключено!");
                            textCheck.setTextColor(getResources().getColor(R.color.greenColor));
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
//                            Toast.makeText(getActivity(), "не ок", Toast.LENGTH_SHORT).show();
                            textCheck.setText("Нет подключения");
                            textCheck.setTextColor(getResources().getColor(R.color.grayColor));
                        }
                    });

                }
            }
        }).start();
    }

}
