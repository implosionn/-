package com.example.zlygz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        /*setContentView(R.layout.activity_blank_fragment);
        Intent intent = new Intent(this, BlankFragmentActivity.class);
        startActivity(intent);*/
    //登录界面
        //    setContentView(R.layout.mainactivity);
        setContentView(R.layout.activity_vpfragment);
        Intent intent = new Intent(this, VPFragmentActivity.class);
        startActivity(intent);
    }

    public void toMovie(View view){
        Intent intent = new Intent(this, VPFragmentActivity.class);
        startActivity(intent);
    }



}