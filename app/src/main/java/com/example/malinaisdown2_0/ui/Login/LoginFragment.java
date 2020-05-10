package com.example.malinaisdown2_0.ui.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.malinaisdown2_0.R;

public class LoginFragment extends Fragment {

    public TextView text_login, text_ip, text_password;
    public String login, ip, password;

    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mSettings;

    public static final String stringlogin = "saved";
    public static final String stringip = "ALOLO";
    public static final String stringpass = "sdsd";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        text_login = root.findViewById(R.id.textLogin);
        text_ip = root.findViewById(R.id.textServer);
        text_password = root.findViewById(R.id.textPassword);

        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        String savedlogin = mSettings.getString(stringlogin, "");
        text_login.setText(savedlogin);

        String savedip = mSettings.getString(stringip, "");
        text_ip.setText(savedip);

        String savedpass = mSettings.getString(stringpass, "");
        text_password.setText(savedpass);

    }

    @Override
    public void onPause() {
        super.onPause();

        login = text_login.getText().toString();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(stringlogin, login);
        editor.apply();


        ip = text_ip.getText().toString();
        SharedPreferences.Editor ed_ip = mSettings.edit();
        ed_ip.putString(stringip, ip);
        ed_ip.apply();

        password = text_password.getText().toString();
        SharedPreferences.Editor ed_pass = mSettings.edit();
        ed_pass.putString(stringpass, password);
        ed_pass.apply();


    }


}
