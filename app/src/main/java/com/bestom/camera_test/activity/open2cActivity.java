package com.bestom.camera_test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.bestom.camera_test.R;

public class open2cActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open2c);

        mContext=this;
        mActivity=this;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
