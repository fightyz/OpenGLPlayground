package com.fightyz.openglbasicshape.renderers;

import android.content.Context;

import com.fightyz.openglbasicshape.objects.Point;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;

public class PointRenderer extends BaseRenderer {
    private Point mPoint;

    public PointRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        // 在 onSurfaceCreated 里面初始化，否则会报线程错误
        mPoint = new Point(mContext);
        mPoint.bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // 确定视口大小
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);
        mPoint.draw();
    }
}
