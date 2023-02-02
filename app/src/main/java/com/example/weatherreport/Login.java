package com.example.weatherreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weatherreport.util.Md5;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context ctx = Login.this;
        SharedPreferences spCheck = ctx.getSharedPreferences("check",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCheck = spCheck.edit();
        SharedPreferences sp = Login.this.getSharedPreferences("user", MODE_PRIVATE);

        Button register = findViewById(R.id.user_register);
        Button login = findViewById(R.id.login_btn);
        CheckBox checkBox = findViewById(R.id.remember_pwd);

        Intent intent = new Intent(Login.this,MainActivity.class);
        if(spCheck.getString("remember",null)!=null&&spCheck.getString("remember",null).equals("true")
                &&spCheck.getString("RemPassword",null)!=null
                &&spCheck.getString("RemUserName",null)!=null){
            checkBox.setChecked(true);
            EditText userNameE=findViewById(R.id.username);
            EditText passwordE=findViewById(R.id.password);
            userNameE.setText(spCheck.getString("RemUserName",null));
            passwordE.setText(spCheck.getString("RemPassword",null));
            //判断记住的密码是否正确
            if(Md5.md5(spCheck.getString("RemPassword",null)).equals(sp.getString(spCheck.getString("RemUserName",null),null))){
                intent.putExtra("isLogin", "true");
                startActivity(intent);
            }
        }


        /**
         * 登录
         */
        login.setOnClickListener(view -> {
            EditText userNameE=findViewById(R.id.username);
            EditText passwordE=findViewById(R.id.password);
            String userName=userNameE.getText().toString();
            String password=passwordE.getText().toString();
            if(userName.length()==0&&password.length()==0){
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            }else {
                System.out.println(spCheck.getString("remember",null));
                if(Md5.md5(password).equals(sp.getString(userName,"null"))){
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    intent.putExtra("isLogin", "true");
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 注册
         */
        register.setOnClickListener(view -> {
            EditText userNameE=findViewById(R.id.username);
            EditText passwordE=findViewById(R.id.password);
            String userName=userNameE.getText().toString();
            String password=passwordE.getText().toString();
            if(userName.length()==0||password.length()==0){
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(userName, Md5.md5(password));
                editor.commit();
                Toast.makeText(ctx, "注册成功", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnClickListener(view -> {
            EditText userNameE=findViewById(R.id.username);
            EditText passwordE=findViewById(R.id.password);
            String userName=userNameE.getText().toString();
            String password=passwordE.getText().toString();
            if(checkBox.isChecked()){
                editorCheck.putString("remember", "true");
            }else {
                editorCheck.putString("remember","false");
            }
            editorCheck.putString("RemUserName",userName);
            editorCheck.putString("RemPassword",password);
            editorCheck.commit();
        });
    }

}