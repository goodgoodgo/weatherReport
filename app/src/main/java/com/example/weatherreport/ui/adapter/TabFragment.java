package com.example.weatherreport.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TabFragment extends androidx.fragment.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        TextView textView = new TextView(getContext());
        textView.setText("TabFragment");
        return textView;
    }
}
