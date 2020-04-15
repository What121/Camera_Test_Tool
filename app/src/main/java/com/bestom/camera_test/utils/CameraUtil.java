package com.bestom.camera_test.utils;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraUtil {
    private static final String TAG = "CameraUtil";

    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 480;
    private static int mOrientation = 0;

    private static Camera mCamera0,mCamera1;

    /**
     * 根据ID打开相机
     * @param cameraID
     */
    public static void openCamera(int cameraID) {
        Log.d(TAG, "NumberOfCameras: "+Camera.getNumberOfCameras());

        if  (cameraID==0){
            if ( mCamera0 != null) {
                throw new RuntimeException("camera already initialized!");
            }
            mCamera0 = Camera.open( Camera.CameraInfo.CAMERA_FACING_BACK);
            // 没有摄像头时，抛出异常
            if (mCamera0 == null) {
                throw new RuntimeException("Unable to open cameraID"+cameraID);
            }

            Camera.Parameters parameters = mCamera0.getParameters();
//            mCameraPreviewFps = CameraUtils.chooseFixedPreviewFps(parameters, expectFps * 1000);
            parameters.setRecordingHint(true);
            int[] fps= new int[2];
            parameters.getPreviewFpsRange(fps);
            Log.e(TAG, "openCamera"+cameraID+ " FPS: "+Arrays.toString(fps) );
            mCamera0.setParameters(parameters);
            setPreviewSize(mCamera0, CameraUtil.DEFAULT_WIDTH, CameraUtil.DEFAULT_HEIGHT);
            mCamera0.setDisplayOrientation(mOrientation);
        }
        else if (cameraID==1){
            if ( mCamera1 != null) {
                throw new RuntimeException("camera already initialized!");
            }

            mCamera1=Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            if (mCamera1 == null) {
                throw new RuntimeException("Unable to open cameraID"+cameraID);
            }

            Camera.Parameters parameters = mCamera1.getParameters();
//            mCameraPreviewFps = CameraUtils.chooseFixedPreviewFps(parameters, expectFps * 1000);
            parameters.setRecordingHint(true);
            int[] fps= new int[2];
            parameters.getPreviewFpsRange(fps);
            Log.e(TAG, "openCamera"+cameraID+ "  FPS: "+Arrays.toString(fps) );

            mCamera1.setParameters(parameters);
            setPreviewSize(mCamera1, CameraUtil.DEFAULT_WIDTH, CameraUtil.DEFAULT_HEIGHT);
            mCamera1.setDisplayOrientation(mOrientation);
        }else {
            throw new IllegalArgumentException("not support cameraID"+cameraID);
        }
    }

    /**
     * 开始预览
     *
     * @param holder
     */
    public static void startPreviewDisplay(SurfaceHolder holder, int cameraID) {
        if (cameraID==0){
            if (mCamera0 == null) {
                throw new IllegalStateException("Camera must be set when start preview");
            }
            try {
                mCamera0.setPreviewDisplay(holder);
                mCamera0.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (cameraID==1){
            if (mCamera1 == null) {
                throw new IllegalStateException("Camera must be set when start preview");
            }
            try {
                mCamera1.setPreviewDisplay(holder);
                mCamera1.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("cameraID not support"+cameraID);
        }


    }


    /**
     * 释放相机
     */
    public static void releaseCamera(int cameraID) {
        if (cameraID==0){
            if (mCamera0 != null) {
                mCamera0.release();
                mCamera0 = null;
            }
        }else if (cameraID==1){
            if (mCamera1 != null) {
                mCamera1.release();
                mCamera1 = null;
            }
        }else {
            throw new IllegalArgumentException("cameraID not support"+cameraID);
        }


    }


    /**
     * 设置预览大小
     *
     * @param camera
     * @param expectWidth
     * @param expectHeight
     */
    public static void setPreviewSize(Camera camera, int expectWidth, int expectHeight) {
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = calculatePerfectSize(parameters.getSupportedPreviewSizes(),
                expectWidth, expectHeight);
        Log.d(TAG, "setPreviewSize:  width+"+size.width+" height+"+size.height);
        parameters.setPreviewSize(size.width, size.height);
        camera.setParameters(parameters);
    }

    /**
     * 计算最完美的Size
     *
     * @param sizes
     * @param expectWidth
     * @param expectHeight
     * @return
     */
    public static Camera.Size calculatePerfectSize(List<Camera.Size> sizes, int expectWidth,
                                                   int expectHeight) {
        // 根据宽度进行排序
        sortList(sizes);
        Camera.Size result = sizes.get(0);
        // 判断存在宽或高相等的Size
        boolean widthOrHeight = false;
        // 辗转计算宽高最接近的值
        for (Camera.Size size : sizes) {
            Log.d(TAG, "sizes list: "+ size.width+" "+size.height);
            // 如果宽高相等，则直接返回
            if (size.width == expectWidth && size.height == expectHeight) {
                result = size;
                break;
            }
            // 仅仅是宽度相等，计算高度最接近的size
            if (size.width == expectWidth) {
                widthOrHeight = true;
                if (Math.abs(result.height - expectHeight)
                        > Math.abs(size.height - expectHeight)) {
                    result = size;
                }
            }
            // 高度相等，则计算宽度最接近的Size
            else if (size.height == expectHeight) {
                widthOrHeight = true;
                if (Math.abs(result.width - expectWidth)
                        > Math.abs(size.width - expectWidth)) {
                    result = size;
                }
            }
            // 如果之前的查找不存在宽或高相等的情况，则计算宽度和高度都最接近的期望值的Size
            else if (!widthOrHeight) {
                if (Math.abs(result.width - expectWidth)
                        > Math.abs(size.width - expectWidth)
                        && Math.abs(result.height - expectHeight)
                        > Math.abs(size.height - expectHeight)) {
                    result = size;
                }
            }
        }
        return result;
    }

    /**
     * 排序
     *
     * @param list
     */
    private static void sortList(List<Camera.Size> list) {
        Collections.sort(list, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size pre, Camera.Size after) {
                if (pre.width > after.width) {
                    return 1;
                } else if (pre.width < after.width) {
                    return -1;
                }
                return 0;
            }
        });
    }

    /**
     * 获取支持的camera数量
     * @return
     */
    public static int getCameraNumbers(){
        return Camera.getNumberOfCameras();
    }




}
