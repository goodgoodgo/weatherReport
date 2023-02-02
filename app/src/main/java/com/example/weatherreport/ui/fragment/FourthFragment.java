package com.example.weatherreport.ui.fragment;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.weatherreport.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment implements AdapterView.OnItemClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FourthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourthFragment newInstance(String param1, String param2) {
        FourthFragment fragment = new FourthFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fourth, container, false);
        String data[]={"用户信息设置","切换默认城市","","历史城市显示数量"};
        ListView listview=view.findViewById(R.id.listSet);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setOnCreateContextMenuListener((menu, view1, contextMenuInfo) -> {
            menu.setHeaderTitle("选择城市数量");
            menu.add(0,0,0,"1");
            menu.add(0,1,0,"2");
            menu.add(0,1,0,"3");
        });

        Button button=view.findViewById(R.id.likeCity);
        button.setOnClickListener(view1 -> {
            PopupMenu popupMenu=new PopupMenu(getActivity(),button);
            popupMenu.getMenuInflater().inflate(R.menu.pop,popupMenu.getMenu());
            Context ctx = getActivity();
            SharedPreferences spCheck = ctx.getSharedPreferences("like",  Context.MODE_PRIVATE);
            SharedPreferences.Editor editorCheck = spCheck.edit();
            editorCheck.putString("like", "金");
            editorCheck.commit();
            popupMenu.show();
        });
        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String id = String.valueOf(info.id);
        Context ctx = getActivity();
        SharedPreferences spCheck = ctx.getSharedPreferences("historyNum",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCheck = spCheck.edit();
        editorCheck.putString("historyNum", String.valueOf(item.getItemId()+1));
        editorCheck.commit();
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i==2){

        }
    }
}