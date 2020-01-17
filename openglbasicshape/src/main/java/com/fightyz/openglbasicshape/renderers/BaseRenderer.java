package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:57
 */
public class BaseRenderer implements GLSurfaceView.Renderer {
    /**
     * 一个 float 数据占 4 个字节
     */
    protected int BYTES_PER_FLOAT = 4;

    protected Context mContext;

    public BaseRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
