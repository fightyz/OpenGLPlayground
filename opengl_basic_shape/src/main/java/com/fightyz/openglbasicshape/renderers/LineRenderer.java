package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.openglbasicshape.objects.Line;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setIdentityM;

public class LineRenderer extends BaseRenderer {
    private Line mLine;

    public LineRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        mLine = new Line(mContext);
        mLine.bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0.5f, 0f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        mLine.draw();
    }
}
