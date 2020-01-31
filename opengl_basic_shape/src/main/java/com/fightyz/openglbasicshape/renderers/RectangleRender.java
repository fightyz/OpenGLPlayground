package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.openglbasicshape.objects.Rectangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setIdentityM;

public class RectangleRender extends BaseRenderer {
    private Rectangle mRectangle;

    public RectangleRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        mRectangle = new Rectangle(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        setIdentityM(modelMatrix, 0);
        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, viewMatrix, 0, modelMatrix, 0);
        final float[] result = new float[16];
        Matrix.multiplyMM(result, 0, projectionMatrix, 0, temp, 0);
        System.arraycopy(result, 0, projectionMatrix, 0, result.length);
        mRectangle.bindData();
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL_COLOR_BUFFER_BIT);

//        mRectangle.draw(modelMatrix);

        mRectangle.draw();
    }
}
