package com.example.weatherreport.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.weatherreport.MainActivity;
import com.example.weatherreport.R;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class City extends AppCompatActivity {

    private static String name="";
    private TextView show;
    private Spinner provinceSpinner;//省级
    private Spinner citySpinner;//市级
    private Spinner countySpinner;//区级
    private static String cityMain;
    private static String tmpCard="";
    private static String msgCard="";
    private static String cityCard="";

    private static DBEngine dbEngine;

    public static String getTmpCard() {
        return tmpCard;
    }

    public static void setTmpCard(String tmpCard) {
        City.tmpCard = tmpCard;
    }

    public static String getMsgCard() {
        return msgCard;
    }

    public static void setMsgCard(String msgCard) {
        City.msgCard = msgCard;
    }

    public static String getCityCard() {
        return cityCard;
    }

    public static void setCityCard(String cityCard) {
        City.cityCard = cityCard;
    }

    private String[] province = new String[]{"北京","浙江","河北","山西","辽宁","吉林","黑龙江",
            "江苏","安徽","福建","江西","山东","河南","湖北","湖南","海南","四川","贵州","云南","陕西","甘肃","青海"};
    private String[][] city = new String[][]{
            {"东城区","西城区","崇文区","宣武区","朝阳区","海淀区","丰台区","石景山区","门头沟区",
                    "房山区","通州区","顺义区","大兴区","昌平区","平谷区","怀柔区","密云县","延庆县"},
            {"杭州","嘉兴","湖州","绍兴","温州","宁波","金华","衢州","舟山","台州","丽水"},
            {"和平区","河东区","河西区","南开区","河北区","红桥区","塘沽区","汉沽区","大港区","东丽区"},
            {"广州","深圳","韶关"}};
    private String[][][] county = new String[][][]{{
            //北京
            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无" },
            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},},{
            //嘉兴
            {"萧山","钱塘","余杭","桐庐","临安","富阳","建德","上城","滨江"},
            {"桐乡","平湖","嘉善","海宁","秀洲","海盐","南湖"},{"无"},{"无"},{"无"},},{
            //杭州
            {"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},{"无"},},{
            //广东
            {"海珠区","荔湾区","越秀区","白云区","萝岗区","天河区","黄浦区","花都区","从化市","增城市"
                    ,"番禺区","南沙区"},
            {"宝安区","福田区","龙岗区","罗湖区","南山区","盐田区"},
            {"武江区","浈江区","曲江区","乐昌市","南雄市","始兴县","仁化县","翁源县","新丰县","乳源县"}
    }
    };


    //Spinner想要填充肯定需要适配器
    private ArrayAdapter<String> provinceAdapter;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> countyAdapter;
    private int provicePosition;//省级选中索引
    private int cityPosition;//市级选中索引
    private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Button button =findViewById(R.id.selectCity);
        initViews();//初始化
        Intent intent=new Intent(City.this,MainActivity.class);
        button.setOnClickListener(view -> {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("flag", 3);
            intent.putExtra("city",county[provicePosition][cityPosition][num]);
            startActivity(intent);
            getSync(view);
        });
    }

    //控件初始化
    private void initViews() {
        provinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        countySpinner = (Spinner) findViewById(R.id.countySpinner);
        show = (TextView) findViewById(R.id.show);

        //初始化适配器及显示的内容
        provinceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,province);
        provinceSpinner.setAdapter(provinceAdapter);


        //对省级下拉实现监听，市级下拉需要根据不同的省级显示不同内容。
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //需要在item选中省级的时候，动态的改变市级对应的显示
                cityAdapter = new ArrayAdapter<String>(City.this,android.R.layout.simple_spinner_item,city[position]);
                //设置二级下拉列表当中选项内容适配器
                citySpinner.setAdapter(cityAdapter);
                //记录当前的省级索引位置，留给下面修改县级进行适配使用
                provicePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //市级下拉监听
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //需要在item选中市级的时候，动态的改变县级对应的显示
                countyAdapter = new ArrayAdapter<String>(City.this,android.R.layout.simple_spinner_item,county[provicePosition][position]);//首先确定是哪个省的。
                //设置二级下拉列表当中选项内容适配器
                countySpinner.setAdapter(countyAdapter);
                show.setText("选中的城市为:"+ province[provicePosition] + city[provicePosition][position]);
                cityPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //区级的下拉
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show.setText("选中的城市为:"+ province[provicePosition] + city[provicePosition][cityPosition]
                        + county[provicePosition][cityPosition][position]);
                num=position;
                cityMain=county[provicePosition][cityPosition][position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public static void getSync(View view) {
        //在Android 中完成网络请求必须在子线程中完成
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("https://geoapi.qweather.com/v2/city/lookup?location=" +
                        cityMain+"&key=1025c3f120e64152b5d0f13e9d4ad3cf").build();
                //准备好请求的Code对象
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String msg=response.body().string();
                    JSONObject data = JSONObject.parseObject(msg);
                    JSONArray jsonArray = data.getJSONArray("location");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String id=jsonObject.getString("id");
                    Request requestW = new Request.Builder().url("https://devapi.qweather.com/v7/weather/3d?location=" +
                            id+"&key=1025c3f120e64152b5d0f13e9d4ad3cf").build();
                    Call callW = okHttpClient.newCall(requestW);
                    Response responseW = callW.execute();
                    JSONObject dataW = JSONObject.parseObject(responseW.body().string());
                    JSONArray jsonArrayW = dataW.getJSONArray("daily");
                    JSONObject jsonObjectW=jsonArrayW.getJSONObject(0);
                    String tempMin=jsonObjectW.getString("tempMin");
                    String tempMax=jsonObjectW.getString("tempMax");
                    String textDay=jsonObjectW.getString("textDay");
                    dbEngine = new DBEngine(view.getContext());
                    tmpCard=(Integer.parseInt(tempMax)+Integer.parseInt(tempMin))/2+"°";
                    msgCard=textDay+"  "+tempMax+"° / "+tempMin+"°";
                    cityCard=cityMain;
                    City.setCityCard(cityCard);
                    City.setMsgCard(msgCard);
                    City.setTmpCard(tmpCard);

                    Weather weather = new Weather(cityMain,Integer.parseInt(tempMin),Integer.parseInt(tempMax),textDay);
                    dbEngine.insert_weather(weather);
                    //dbEngine.delete_all_weather();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}