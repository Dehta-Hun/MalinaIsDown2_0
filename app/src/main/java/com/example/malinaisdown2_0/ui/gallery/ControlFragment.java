package com.example.malinaisdown2_0.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.malinaisdown2_0.R;

public class ControlFragment extends Fragment {
xdsd
    private ControlViewModel controlViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        controlViewModel =
                ViewModelProviders.of(this).get(ControlViewModel.class);
        View root = inflater.inflate(R.layout.fragment_control, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
        controlViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}
