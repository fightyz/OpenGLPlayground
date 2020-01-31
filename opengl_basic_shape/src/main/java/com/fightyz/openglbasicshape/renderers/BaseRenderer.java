package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:57
 */
public abstract class BaseRenderer implements GLSurfaceView.Renderer {
    protected Context mContext;

    protected float[] modelMatrix = new float[16];
    protected float[] viewMatrix = new float[16];
    protected float[] projectionMatrix = new float[16];

    public BaseRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {}

    /**
     * Surface 刚创建的时候，它的 size 是 0，也就是说在画第一次图之前它会被调用一次
     * @param gl10
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) { }

    @Override
    public void onDrawFrame(GL10 gl10) { }
}
