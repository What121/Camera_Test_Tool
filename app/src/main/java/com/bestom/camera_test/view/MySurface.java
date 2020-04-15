package com.bestom.camera_test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bestom.camera_test.R;
import com.bestom.camera_test.utils.CameraUtil;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "MySurface";

    SurfaceHolder mSurfaceHolder;
    int cameraID;

    public MySurface(Context context) {
        this(context,null);
    }

    public MySurface(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MySurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Cameras);
        cameraID = typedArray.getInt(R.styleable.Cameras_camera_type,0);
        if (cameraID==1){
//            mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);        //设置透明
            this.setZOrderOnTop(true);          //设置 置顶
        }
    }

    public void setCameraID(int cameraID){
        this.cameraID=cameraID;
    }

    public SurfaceHolder getSurfaceHolder(){
        return this.getHolder();
    }

    public void release(){
        mSurfaceHolder.removeCallback(this);
        mSurfaceHolder=null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CameraUtil.openCamera(cameraID);
        Log.d(TAG, "surfaceCreated: openCamera is "+cameraID);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        CameraUtil.startPreviewDisplay(surfaceHolder,cameraID);
        Log.d(TAG, "surfaceChanged: camera startPreviewDisplay");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        CameraUtil.releaseCamera(cameraID);
        Log.d(TAG, "surfaceDestroyed: releaseCamera "+cameraID);
    }


}
