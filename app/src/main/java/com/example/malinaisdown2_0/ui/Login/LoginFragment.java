package com.example.malinaisdown2_0.ui.Login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.malinaisdown2_0.MainActivity;
import com.example.malinaisdown2_0.R;

public class LoginFragment extends Fragment {

    public TextView text_login, text_ip, text_password;
    public String login, ip, password;
    public SharedPreferences pref_login, pref_ip, pref_pass;
    final String stringlogin = "saved";
    final String stringip = "ALOLO";
    final String stringpass = "sdsd";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        text_login = root.findViewById(R.id.textLogin);
        text_ip = root.findViewById(R.id.textServer);
        text_password = root.findViewById(R.id.textPassword);


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        pref_login = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String savedlogin = pref_login.getString(stringlogin, "");
        text_login.setText(savedlogin);

        pref_ip = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String savedip = pref_ip.getString(stringip, "");
        text_ip.setText(savedip);

        pref_pass = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String savedpass = pref_pass.getString(stringpass, "");
        text_password.setText(savedpass);

    }

    @Override
    public void onPause() {
        super.onPause();

        login = text_login.getText().toString();
        pref_login = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = pref_login.edit();
        ed.putString(stringlogin, login);
        ed.apply();


        ip = text_ip.getText().toString();
        pref_ip = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed_ip = pref_ip.edit();
        ed_ip.putString(stringip, ip);
        ed_ip.apply();

        password = text_password.getText().toString();
        pref_pass = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed_pass = pref_pass.edit();
        ed_pass.putString(stringpass, password);
        ed_pass.apply();


    }


}
