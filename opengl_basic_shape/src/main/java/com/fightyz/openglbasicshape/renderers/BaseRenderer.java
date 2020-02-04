package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:57
 */
public abstract class BaseRenderer implements GLSurfaceView.Renderer {
    protected Context mContext;

    private float angleX;
    private float angleY;
    private float angleZ;

    protected float[] modelMatrix = new float[16];
    protected float[] viewMatrix = new float[16];
    protected float[] projectionMatrix = new float[16];
    protected float[] mvpMatrix = new float[16];

    public BaseRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * Surface 刚创建的时候，它的 size 是 0，也就是说在画第一次图之前它会被调用一次
     * @param gl10
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) { }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public float getAngleX() {
        return angleX;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ = angleZ;
    }
}
