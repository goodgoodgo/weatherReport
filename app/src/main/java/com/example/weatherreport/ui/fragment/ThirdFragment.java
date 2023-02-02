package com.example.weatherreport.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.weatherreport.R;
import com.example.weatherreport.model.Weather;
import com.example.weatherreport.model.WeatherDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String data[];
    private static List<Weather> weatherList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        WeatherDatabase weatherDatabase=WeatherDatabase.getInstance(getContext());
        weatherList = weatherDatabase.get_weather_dao().get_all_weather();
        List<String> his=new ArrayList<>();
        Boolean flag;
        for(int i=0;i<weatherList.size();i++) {
            flag=true;
            for (int j = 0; j < his.size(); j++) {
                if(weatherList.get(i).getAddress().equals(his.get(j).split(" ")[0])){
                    flag=false;
                    break;
                }
            }
            if(flag==true && !weatherList.get(i).toString().split(" ")[0].equals("无")){
                his.add(weatherList.get(i).toString());
            }
        }

        SharedPreferences spCheck = getActivity().getSharedPreferences("historyNum",  Context.MODE_PRIVATE);
        if(spCheck.getString("historyNum",null)!=null){
            Log.i("tagg",spCheck.getString("historyNum",null));
            int historyNum=Integer.parseInt(spCheck.getString("historyNum",null));
            data=new String[historyNum];
            for (int i = 0; i < historyNum; i++) {
                data[i]=his.get(i);
            }
        }else {
            data=new String[his.size()];
            for (int i = 0; i < his.size(); i++) {
                data[i]=his.get(i);
            }
        }

        ListView listview=view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
        return view;
    }
}