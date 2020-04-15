package com.bestom.camera_test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bestom.camera_test.R;
import com.bestom.camera_test.utils.CameraUtil;
import com.bestom.camera_test.view.MySurface;

public class SwitchcActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SwitchcActivity";
    Context mContext;
    Activity mActivity;

    int camera_nums=0;
    LinearLayout dolayout;
    MySurface mMySurface;

    int cameraid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchc);

        mContext=this;
        mActivity=this;
        camera_nums= CameraUtil.getCameraNumbers();

        initview();




    }

    private void initview(){
        dolayout=findViewById(R.id.do_layout);
        mMySurface=findViewById(R.id.my_surface);
        for (int i=0;i<camera_nums;i++){
            Button button=new Button(mContext);
            switch (i){
                case 0:
                    button.setId(R.id.camera_0);
                    button.setText("open camera_0");
                    break;
                case 1:
                    button.setId(R.id.camera_1);
                    button.setText("open camera_1");
                    break;
                case 2:
                    button.setId(R.id.camera_2);
                    button.setText("open camera_2");
                    break;
                default:
                    break;
            }

            button.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            button.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setPadding(0,15,0,0);

            dolayout.addView(button);
            button.setOnClickListener(this::onClick);

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.camera_0){
            cameraid=0;
        }else if (view.getId()==R.id.camera_1){
            cameraid=1;
        }else if (view.getId()==R.id.camera_2){
            cameraid=2;
        }

        mMySurface.setVisibility(View.GONE);
        mMySurface.setCameraID(cameraid);
        mMySurface.setVisibility(View.VISIBLE);
        Log.e(TAG, "onClick: " );
    }


}
