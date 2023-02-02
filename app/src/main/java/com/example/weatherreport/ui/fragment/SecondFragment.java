package com.example.weatherreport.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherreport.MainActivity;
import com.example.weatherreport.R;
import com.example.weatherreport.model.City;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


public class SecondFragment extends Fragment implements View.OnClickListener{



    private ImageView imageView;

    private Button button_gerate_QRCode;

    private static String msg="";
    private static Boolean flag=true;
    private static String id="";
    private static String cityMain="";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View viewStatic;
    private static List<String> city=new ArrayList<>();
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String tmpCard="";
    private static String cityCard="";
    private static String msgCard="";
    private String mParam1;
    private String mParam2;
    private static int f=0;

    TextView firstCity;
    TextView firstMsg;
    TextView firstTmp;
    TextView secondCity;
    TextView secondMsg;
    TextView secondTmp;
    public SecondFragment() {
    }


    @NonNull
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        View view=inflater.inflate(R.layout.fragment_second, container,false);
        EditText findCity=view.findViewById(R.id.findCity);
        Context context = getActivity();
        Intent intent=new Intent(context, City.class);
        findCity.setOnClickListener(view1 -> {
            getActivity().finish();
            startActivity(intent);
        });
        cityMain=((MainActivity)getActivity()).getCity();
        firstCity=view.findViewById(R.id.firstCard);
        firstMsg=view.findViewById(R.id.firstMsg);
        firstTmp=view.findViewById(R.id.firstTmp);
        secondCity=view.findViewById(R.id.secondCard);
        secondMsg=view.findViewById(R.id.secondMsg);
        secondTmp=view.findViewById(R.id.secondTmp);
        SharedPreferences spCheck = getActivity().getSharedPreferences("like",  Context.MODE_PRIVATE);

        if (!spCheck.getString("like",null).equals("金华")){
            secondMsg.setText("晴 13°/2°");
            secondTmp.setText("6°");
            secondCity.setText("湖州");
        }
        msgCard=City.getMsgCard();
        tmpCard=City.getTmpCard();
        cityCard=City.getCityCard();
        if (!msgCard.equals("")){
            firstMsg.setText(msgCard);
        }
        if (!tmpCard.equals("")){
            firstTmp.setText(tmpCard);
        }
        if (!cityCard.equals("")){
            firstCity.setText(cityCard);
            cityCard="";
        }
        button_gerate_QRCode = (Button) view.findViewById(R.id.button_generate_QRcode);
        imageView = (ImageView) view.findViewById(R.id.imageview_QRcode);

        button_gerate_QRCode.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_generate_QRcode:
                String input_string = msgCard+cityCard;
                if(!TextUtils.isEmpty(input_string)){
                    imageView.setImageBitmap(generate_QRCode_Bitmap(input_string,600,600));
                }
            default:
        }
    }


    private Bitmap generate_QRCode_Bitmap(String content,int width,int height){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x000000;
                    } else {
                        pixels[i * width + j] = 0xffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
            //根据官方文档显示stride：Number of colors in the array between rows (must be >= width or <= -width).
            // 即Bitmap.createBitmap方法中第三个参数需要大于width。
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
