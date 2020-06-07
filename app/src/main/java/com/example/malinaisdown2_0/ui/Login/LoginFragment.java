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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginFragment extends Fragment {

    public TextView textLogin, textIp, textPassword, textCheck;
    public String login, ip, password;
    public Button btnCheck;

    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

    public static final String stringlogin = "saved";
    public static final String stringip = "ALOLO";
    public static final String stringpass = "sdsd";
    Functions.SshTestConnection ssh;

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

        executeCheckShow(300);
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
                executeCheckShow(5000);
            }
        };
        btnCheck.setOnClickListener(myCheckListener);

    }

    public  void executeCheckShow(int timeoutMS) {
        ssh = new Functions.SshTestConnection();
        ssh.execute(textLogin.getText().toString(), textIp.getText().toString(), textPassword.getText().toString());
        try {
            showMessage(ssh.get(timeoutMS, TimeUnit.MILLISECONDS));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
    public void showMessage(Boolean operator) {
        if (operator == true) {
            textCheck.setText("Подключено!");
            textCheck.setTextColor(getResources().getColor(R.color.greenColor));
        } else {
            textCheck.setText("Нет подключения");
            textCheck.setTextColor(getResources().getColor(R.color.grayColor));
        }

    }
}
