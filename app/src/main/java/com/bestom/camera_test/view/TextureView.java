package com.bestom.camera_test.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;

public class TextureView extends android.view.TextureView  implements android.view.TextureView.SurfaceTextureListener {

    public TextureView(Context context) {
        this(context,null);
    }

    public TextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }


}
