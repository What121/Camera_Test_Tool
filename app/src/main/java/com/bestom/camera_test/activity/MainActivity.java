package com.bestom.camera_test.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bestom.camera_test.R;
import com.bestom.camera_test.utils.CameraUtil;

import java.security.acl.Permission;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Context mContext;
    private Activity mActivity;

    Button switch_camera;
    Button open2_camera;
    TextView msg_text;

    int request_code=110;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        mActivity=this;

        try {
            String[] permisions = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            mActivity.requestPermissions( permisions,request_code);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        initview();

        switch_camera.setOnClickListener(this);
        open2_camera.setOnClickListener(this);

        if (CameraUtil.getCameraNumbers()>=2){
            switch_camera.setVisibility(View.VISIBLE);
            open2_camera.setVisibility(View.VISIBLE);
            msg_text.setVisibility(View.GONE);
        }else if (CameraUtil.getCameraNumbers()==1){
            switch_camera.setVisibility(View.VISIBLE);
            open2_camera.setVisibility(View.GONE);
            msg_text.setVisibility(View.GONE);
        }else {
            switch_camera.setVisibility(View.GONE);
            open2_camera.setVisibility(View.GONE);
            msg_text.setVisibility(View.VISIBLE);
            msg_text.append(" "+CameraUtil.getCameraNumbers());
        }
    }

    private void initview(){
        switch_camera=findViewById(R.id.switch_camera);
        open2_camera=findViewById(R.id.open2_camera);
        msg_text=findViewById(R.id.msg_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.switch_camera:
                startActivity(new Intent(mContext,SwitchcActivity.class));
                break;
            case R.id.open2_camera:
                startActivity(new Intent(mContext,open2cActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
